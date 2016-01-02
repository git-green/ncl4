package ua.sumdu.greenberg.servlets;

import org.apache.log4j.Logger;

//import ua.sumdu.greenberg.model.ServerTimerTask;
import ua.sumdu.greenberg.model.objects.Category;
import ua.sumdu.greenberg.model.objects.Picture;
import ua.sumdu.greenberg.model.objects.Product;
import ua.sumdu.greenberg.model.objects.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This servlet working with index.jsp.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class IndexServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(IndexServlet.class);
    EntityManager em = null;
    private List<Category> categoryList;
    private List<Product> products;
    private List<Picture> pictures;
    private List<User> users;
    private int countProduct;
    private int countFind;
    private String textFind;
    private int categoryID;
    private int minPrice;
    private int maxPrice;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("Init UserServlet");
        em = Persistence.createEntityManagerFactory("JavaAuction").createEntityManager();
//        ServerTimerTask.run();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        log.info("Request - " + request.getParameter("action"));

        if ("registerForm".equals(request.getParameter("action"))) {
            log.info("Click register");
            sendResponse(response, "<result>OK</result>");
        } else if ("login".equals(request.getParameter("action"))) {
            log.info("Click login");
            sendResponse(response, "<result>OK</result>");
        } else if ("logOut".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null || request.getSession().getAttribute("user") != "") {
                request.getSession().setAttribute("user", null);
                sendResponse(response, "<result>OK</result>");
            }
        } else if ("admin".equals(request.getParameter("action"))) {
            log.info("Click admin");
            sendResponse(response, "<result>OK</result>");
        } else if ("cabinet".equals(request.getParameter("action"))) {
            log.info("Click cabinet");
            sendResponse(response, "<result>OK</result>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;
        request.getSession().setAttribute("em", em);
        products = null;
        countProduct = 0;
        countFind = 0;
        textFind = null;
        minPrice = 0;
        maxPrice = 1000000000;
        if (request.getParameter("category") != null && request.getParameter("category") != "" &&
                request.getParameter("category").length() > 0) {
                if ("find".equals(request.getParameter("category"))) {
                    textFind = request.getParameter("text");
                    if (request.getParameter("text") != null && request.getParameter("text") != "" &&
                            request.getParameter("text").length() >= 2) {
                        if (request.getParameter("page") != null && request.getParameter("page") != "" &&
                                request.getParameter("page").length() > 0) {
                            if (request.getParameter("minPrice") != null && request.getParameter("minPrice") != "" &&
                                    request.getParameter("minPrice").length() > 0
                                    && request.getParameter("maxPrice") != null && request.getParameter("maxPrice") != "" &&
                                    request.getParameter("maxPrice").length() > 0) {
                                minPrice = Integer.parseInt(request.getParameter("minPrice"));
                                maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
                                products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_FIND_PRODUCT_ON_PAGE").setParameter(1, "%" + request.getParameter("text")
                                        + "%").setParameter(2, "%" + request.getParameter("text") + "%").setParameter(3, "%" + request.getParameter("text")
                                        + "%").setParameter(4, "%" + request.getParameter("text") + "%").setParameter(5, minPrice).setParameter(6, minPrice).setParameter(7, maxPrice).setParameter(8, maxPrice).setParameter(9, Integer.parseInt(request.getParameter("page"))).setParameter(10, Integer.parseInt(request.getParameter("page"))).getResultList();
                            } else {
                                products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_FIND_PRODUCT_ON_PAGE").setParameter(1, "%" + request.getParameter("text")
                                        + "%").setParameter(2, "%" + request.getParameter("text") + "%").setParameter(3, "%" + request.getParameter("text")
                                        + "%").setParameter(4, "%" + request.getParameter("text") + "%").setParameter(5, minPrice).setParameter(6, minPrice).setParameter(7, maxPrice).setParameter(8, maxPrice).setParameter(9, Integer.parseInt(request.getParameter("page"))).setParameter(10, Integer.parseInt(request.getParameter("page"))).getResultList();
                            }
                        } else {
                            products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_FIND_PRODUCT_ON_PAGE").setParameter(1, "%" + request.getParameter("text")
                                    + "%").setParameter(2, "%" + request.getParameter("text") + "%").setParameter(3, "%" + request.getParameter("text")
                                    + "%").setParameter(4, "%" + request.getParameter("text") + "%").setParameter(5, minPrice).setParameter(6, minPrice).setParameter(7, maxPrice).setParameter(8, maxPrice).setParameter(9, 1).setParameter(10, 1).getResultList();
                        }
                     countFind = ((Number) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("FIND_COUNT_ALL_PRODUCT").setParameter(1, "%" + request.getParameter("text")
                             + "%").setParameter(2, "%" + request.getParameter("text") + "%").setParameter(3, "%" + request.getParameter("text")
                             + "%").setParameter(4, "%" + request.getParameter("text") + "%").setParameter(5, minPrice).setParameter(6, minPrice).setParameter(7, maxPrice).setParameter(8, maxPrice).getSingleResult()).intValue();
                    }
                } else {
                    categoryID = Integer.parseInt(request.getParameter("category"));
                    if (categoryID != 0) {
                        if (request.getParameter("page") != null && request.getParameter("page") != ""
                                && request.getParameter("page").length() > 0) {
                            if (request.getParameter("minPrice") != null && request.getParameter("minPrice") != ""
                                    && request.getParameter("minPrice").length() > 0
                                    && request.getParameter("maxPrice") != null && request.getParameter("maxPrice") != ""
                                    && request.getParameter("maxPrice").length() > 0) {
                                minPrice = Integer.parseInt(request.getParameter("minPrice"));
                                maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
                                products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCTS_FOR_CATEGORY").setParameter(1, Integer.parseInt(request.getParameter("category"))).setParameter(2, minPrice).setParameter(3, maxPrice).setParameter(4, maxPrice).setParameter(5, maxPrice).setParameter(6, Integer.parseInt(request.getParameter("page"))).setParameter(7, Integer.parseInt(request.getParameter("page"))).getResultList();
                            } else
                                products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCTS_FOR_CATEGORY").setParameter(1, Integer.parseInt(request.getParameter("category"))).setParameter(2, minPrice).setParameter(3, minPrice).setParameter(4, maxPrice).setParameter(5, maxPrice).setParameter(6, Integer.parseInt(request.getParameter("page"))).setParameter(7, Integer.parseInt(request.getParameter("page"))).getResultList();
                        } else {
                            products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCTS_FOR_CATEGORY").setParameter(1, Integer.parseInt(request.getParameter("category"))).setParameter(2, minPrice).setParameter(3, minPrice).setParameter(4, maxPrice).setParameter(5, maxPrice).setParameter(6, 1).setParameter(7, 1).getResultList();
                        }
                    countProduct = ((Number) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_COUNT_PRODUCTS_FOR_CATEGORY").setParameter(1, Integer.parseInt(request.getParameter("category"))).setParameter(2, minPrice).setParameter(3, minPrice).setParameter(4, maxPrice).setParameter(5, maxPrice).getSingleResult()).intValue();
                    } else {
                        if (request.getParameter("page") != null && request.getParameter("page") != ""
                                && request.getParameter("page").length() > 0) {
                            if (request.getParameter("minPrice") != null && request.getParameter("minPrice") != ""
                                    && request.getParameter("minPrice").length() > 0
                                    && request.getParameter("maxPrice") != null && request.getParameter("maxPrice") != ""
                                    && request.getParameter("maxPrice").length() > 0) {
                                minPrice = Integer.parseInt(request.getParameter("minPrice"));
                                maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
                                products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, minPrice).setParameter(2, minPrice).setParameter(3, maxPrice).setParameter(4, maxPrice).setParameter(5, Integer.parseInt(request.getParameter("page"))).setParameter(6, Integer.parseInt(request.getParameter("page"))).getResultList();
                            } else {
                                products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, minPrice).setParameter(2, minPrice).setParameter(3, maxPrice).setParameter(4, maxPrice).setParameter(5, Integer.parseInt(request.getParameter("page"))).setParameter(6, Integer.parseInt(request.getParameter("page"))).getResultList();
                            }
                            products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, minPrice).setParameter(2, minPrice).setParameter(3, maxPrice).setParameter(4, maxPrice).setParameter(5, Integer.parseInt(request.getParameter("page"))).setParameter(6, Integer.parseInt(request.getParameter("page"))).getResultList();
                        } else {
                            products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, minPrice).setParameter(2, minPrice).setParameter(3, maxPrice).setParameter(4, maxPrice).setParameter(5, 1).setParameter(6, 1).getResultList();
                        }
                        countProduct = ((Number) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_COUNT_PRODUCTS_FOR_ALL").setParameter(1, minPrice).setParameter(2, minPrice).setParameter(3, maxPrice).setParameter(4, maxPrice).getSingleResult()).intValue();
                    }
                }
        } else {
            categoryID = 0;
            countProduct = ((Number) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_COUNT_PRODUCTS_FOR_ALL").setParameter(1, minPrice).setParameter(2, minPrice).setParameter(3, maxPrice).setParameter(4, maxPrice).getSingleResult()).intValue();
            products = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, minPrice).setParameter(2, minPrice).setParameter(3, maxPrice).setParameter(4, maxPrice).setParameter(5, 1).setParameter(6, 1).getResultList();
        }

        categoryList = null;
        users = null;
        pictures = null;
        categoryList = (List<Category>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_CATEGORIES").getResultList();
        users = (List<User>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_USERS").getResultList();
        pictures = (List<Picture>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_PICTURES").getResultList();
        rd = request.getRequestDispatcher("index.jsp");
        request.setAttribute("list", categoryList);
        request.setAttribute("countProduct", countProduct);
        request.setAttribute("countFind", countFind);
        request.setAttribute("textFind", textFind);
        request.setAttribute("categoryID", categoryID);
        request.setAttribute("products", products);
        request.setAttribute("pictures", pictures);
        request.setAttribute("users", users);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        rd.forward(request, response);
    }
    
    public void destroy() {
//    	ServerTimerTask.stop();
    }

    private void sendResponse(HttpServletResponse response, String text) {
        try (PrintWriter pw = response.getWriter()) {
            pw.println(text);
        } catch (IOException e) {
            log.error(e);
        }
    }


}
