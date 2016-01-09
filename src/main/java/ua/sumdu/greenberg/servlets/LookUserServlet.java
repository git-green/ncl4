package ua.sumdu.greenberg.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.sumdu.greenberg.model.objects.*;

public class LookUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (request.getParameter("id") != null) {
    		int userID = Integer.valueOf(request.getParameter("id"));
            request.setAttribute("lookingUser", (User) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_USER_BY_ID").setParameter(1, userID).getResultList().get(0));
			request.setAttribute("buying", (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_USER_BUYING").setParameter(1, userID).getResultList());
			request.setAttribute("solding", (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_USERS_PRODUCTS").setParameter(1, userID).getResultList());
            request.setAttribute("users", (List<User>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_USERS").getResultList());

    		request.setAttribute("id", userID);
       		RequestDispatcher rd = request.getRequestDispatcher("jsp/lookUser.jsp");
            rd.forward(request, response);
    	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("deleteProductBUY") != null) {
			String[] s = request.getParameterValues("deleteCheckBoxBUY");
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
			response.sendRedirect("lookUser?id=" + request.getParameter("id"));
		} else if (request.getParameter("deleteProductSELL") != null) {
			String[] s = request.getParameterValues("deleteCheckBoxSELL");
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
			response.sendRedirect("lookUser?id=" + request.getParameter("id"));
		}
	}

}
