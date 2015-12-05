package ua.sumdu.greenberg.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.sumdu.greenberg.model.OracleDataBase;
import ua.sumdu.greenberg.model.objects.Category;
import ua.sumdu.greenberg.model.objects.Picture;
import ua.sumdu.greenberg.model.objects.Product;
import ua.sumdu.greenberg.model.objects.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@SuppressWarnings("serial")
public class AdminServlet extends HttpServlet {
    
	private static final Logger log = Logger.getLogger(AdminServlet.class);
	
    private List<Category> categoryList = null;
    private List<Product> products = null;
    private List<Picture> pictures = null;
    private List<User> users = null;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
       
		if (request.getParameter("saveUsers") != null) {
			OracleDataBase.getInstance().unBanAllUsers();
			String[] s = request.getParameterValues("ban");
			if (s != null) {
				List<Integer> banList = new ArrayList<Integer>();
				for (int i = 0; i < s.length; i++)
					banList.add(Integer.valueOf(s[i]));
				OracleDataBase.getInstance().setUserBan(banList);
			}
			doGet(request, response);
		}
		
		else if (request.getParameter("deleteProduct") != null) {
			
			String[] s = request.getParameterValues("deleteCheckBox");
			if (s != null) {
				List<Integer> deleteList = new ArrayList<Integer>();
				for (int i = 0; i < s.length; i++)
					deleteList.add(Integer.valueOf(s[i]));
				OracleDataBase.getInstance().deleteProducts(deleteList);
			}
			doGet(request, response);
		}		
		else if (request.getParameter("categories") != null) {
			
			boolean result = false;
			String error = "UNKNOWN ERROR";
			
			String categoryName = request.getParameter("catName");
			int categoryID = Integer.valueOf(request.getParameter("catID"));
			
			boolean isEmpty = categoryName.equals("");
			boolean isChoosed = categoryID != -1;
			
			if ("create".equals(request.getParameter("categories"))) {
				if (!isEmpty) {
					if (!isChoosed)
						result = OracleDataBase.getInstance().addCategory(categoryName);
					else
						result = OracleDataBase.getInstance().addCategory(categoryID, categoryName);
				} else error = isEmpty ? "Empty text field" : "Don't chosed category";
			} else if ("change".equals(request.getParameter("categories"))) {
				if (isChoosed && !isEmpty) 
					result = OracleDataBase.getInstance().changeCategory(categoryID, categoryName);
				else error = isEmpty ? "Empty text field" : "Don't chosed category";
			} else if ("delete".equals(request.getParameter("categories"))) {
				if (isChoosed) 
					result = OracleDataBase.getInstance().deleteCategory(categoryID, categoryList);
				else error = "Don't chosed category";
			}
			if (result)
				sendResponse(response, "<result>OK</result>");
			else
				sendResponse(response, "<result>" + error + "</result>");
		}
		
	}
	
    private void sendResponse(HttpServletResponse response, String text) {
        try (PrintWriter pw = response.getWriter()) {
            pw.println(text);
        } catch (IOException e) {
            log.error(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
        
       	categoryList = OracleDataBase.getInstance().getAllCategories();
       	users = OracleDataBase.getInstance().getAllUsers();
       	products = OracleDataBase.getInstance().getAllProducts();
       	pictures = OracleDataBase.getInstance().getAllPictures();
        
       	request.setAttribute("categories", categoryList);
       	request.setAttribute("products", products);
       	request.setAttribute("pictures", pictures);;
       	request.setAttribute("users", users);
        
        RequestDispatcher rd = request.getRequestDispatcher("jsp/admin.jsp");
        rd.forward(request, response);
    }

}
