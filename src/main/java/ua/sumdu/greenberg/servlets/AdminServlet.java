package ua.sumdu.greenberg.servlets;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.sumdu.greenberg.model.objects.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class AdminServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(AdminServlet.class);
    private List<Category> categoryList = null;
    private List<Product> products = null;
    private List<Picture> pictures = null;
    private List<User> users = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("saveUsers") != null) {
			List<User> someUsers = (List<User>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_USERS").getResultList();
			for (User user : someUsers) {
				((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
				user.setBaned("unbanned");
				((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
			}
			String[] s = request.getParameterValues("ban");
			if (s != null) {
				List<Integer> banList = new ArrayList<Integer>();
				for (int i = 0; i < s.length; i++)
					banList.add(Integer.valueOf(s[i]));
				for (Integer id : banList) {
					User user = (User) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_USER_BY_ID").setParameter(1, id).getResultList().get(0);
					((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
					user.setBaned("baned");
					((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
				}
			}
			doGet(request, response);
		}
		else if (request.getParameter("deleteProduct") != null) {
			String[] s = request.getParameterValues("deleteCheckBox");
			if (s != null) {
				List<Integer> deleteList = new ArrayList<Integer>();
				for (int i = 0; i < s.length; i++)
					deleteList.add(Integer.valueOf(s[i]));
				for (Integer id : deleteList) {
					((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
					if (!((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_FOLLOWING_BY_ID").setParameter(1, id).getResultList().isEmpty()) {
						List<Following> followingS = (List<Following>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_FOLLOWING_BY_ID").setParameter(1, id).getResultList();
						for (Following fl : followingS) {
							((EntityManager) request.getSession().getAttribute("em")).remove(fl);
						}
					}
					if (!((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PICTURE_BY_ID").setParameter(1, id).getResultList().isEmpty()) {
						List<Picture> pictureS = (List<Picture>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PICTURE_BY_ID").setParameter(1, id).getResultList();
						for (Picture p : pictureS) {
							((EntityManager) request.getSession().getAttribute("em")).remove(p);
						}
					}
					if (!((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCT_CATEGORY_BY_ID").setParameter(1, id).getResultList().isEmpty()) {
						List<ProductCategory> productCategoryS = (List<ProductCategory>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCT_CATEGORY_BY_ID").setParameter(1, id).getResultList();
						for (ProductCategory pc : productCategoryS) {
							((EntityManager) request.getSession().getAttribute("em")).remove(pc);
						}
					}
					if (!((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCT_BY_ID").setParameter(1, id).getResultList().isEmpty()) {
						Product product = (Product) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCT_BY_ID").setParameter(1, id).getResultList().get(0);
						((EntityManager) request.getSession().getAttribute("em")).remove(product);
					}
					((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
				}
			}
			doGet(request, response);
		}
		else if (request.getParameter("categories") != null) {
			boolean result = false;
			String error = "UNKNOWN ERROR";
			String categoryName = request.getParameter("catName");
			int categoryID = Integer.parseInt(request.getParameter("catID"));
			boolean isEmpty = categoryName.equals("");
			boolean isChoosed = categoryID != -1;
			if ("create".equals(request.getParameter("categories"))) {
				if (!isEmpty) {
					if (!isChoosed) {
						((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
						Category category = new Category();
						category.setName(categoryName);
						((EntityManager) request.getSession().getAttribute("em")).persist(category);
						((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
						result = true;
					} else {
						((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
						Category category = new Category();
						category.setName(categoryName);
						category.setParentID(categoryID);
						((EntityManager) request.getSession().getAttribute("em")).persist(category);
						((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
						result = true;
					}
				} else error = isEmpty ? "Empty text field" : "Don't chosed category";
			} else if ("change".equals(request.getParameter("categories"))) {
				if (isChoosed && !isEmpty) {
					((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
					Category category = (Category) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_CATEGORY_BY_ID").setParameter(1,categoryID).getResultList().get(0);
					category.setName(categoryName);
					((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
					result = true;
				} else error = isEmpty ? "Empty text field" : "Don't chosed category";
			} else if ("delete".equals(request.getParameter("categories"))) {
				if (isChoosed) {
					((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
					Category category = (Category) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_CATEGORY_BY_ID").setParameter(1,categoryID).getResultList().get(0);
					((EntityManager) request.getSession().getAttribute("em")).remove(category);
					((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
					result = true;
				} else error = "Don't chosed category";
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
        categoryList = (List<Category>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_CATEGORIES").getResultList();
        users = (List<User>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_USERS").getResultList();
        pictures = (List<Picture>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_PICTURES").getResultList();
        products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_PRODUCTS").getResultList();

        request.setAttribute("categories", categoryList);
       	request.setAttribute("products", products);
       	request.setAttribute("pictures", pictures);
       	request.setAttribute("users", users);

        RequestDispatcher rd = request.getRequestDispatcher("jsp/admin.jsp");
        rd.forward(request, response);
    }

}
