package ua.sumdu.greenberg.servlets;

import org.apache.log4j.Logger;

//import ua.sumdu.greenberg.model.OracleDataBase;
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
        products = null;
        countProduct = 0;
        countFind = 0;
        textFind = null;
        minPrice = 0;
        maxPrice = 0;
        if (request.getParameter("category") != null && request.getParameter("category") != "" &&
                request.getParameter("category").length() > 0) {
                if ("find".equals(request.getParameter("category"))) {
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
//                            products = OracleDataBase.getInstance().getProductsFind(Integer.parseInt(request.getParameter("page")),
//                                    minPrice, maxPrice, request.getParameter("text"));
                            } else {
//                            products = OracleDataBase.getInstance().getProductsFind(Integer.parseInt(request.getParameter("page")), 0, 0, request.getParameter("text"));
                            }
                        } else {
//                        products = OracleDataBase.getInstance().getProductsFind(1, 0, 0, request.getParameter("text"));
                        }
//                    countFind = OracleDataBase.getInstance().getCountFind(minPrice, maxPrice, request.getParameter("text"));
                        textFind = request.getParameter("text");
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
//                        products = OracleDataBase.getInstance().getProducts(Integer.parseInt(request.getParameter("page")),
//                                Integer.parseInt(request.getParameter("category")), minPrice, maxPrice);
                                products = (List<Product>) em.createNamedQuery("GET_PRODUCTS_FOR_CATEGORY").setParameter(1, Integer.parseInt(request.getParameter("category"))).setParameter(2, minPrice).setParameter(3, minPrice).setParameter(4, maxPrice).setParameter(5, maxPrice).setParameter(6, Integer.parseInt(request.getParameter("page"))).setParameter(7, Integer.parseInt(request.getParameter("page"))).getResultList();

                            } else
//                       products = OracleDataBase.getInstance().getProducts(Integer.parseInt(request.getParameter("page")),
//                                Integer.parseInt(request.getParameter("category")), 0, 0);
                                products = (List<Product>) em.createNamedQuery("GET_PRODUCTS_FOR_CATEGORY").setParameter(1, Integer.parseInt(request.getParameter("category"))).setParameter(2, 0).setParameter(3, 0).setParameter(4, 1000000000).setParameter(5, 1000000000).setParameter(6, Integer.parseInt(request.getParameter("page"))).setParameter(7, Integer.parseInt(request.getParameter("page"))).getResultList();

                        } else {
//                    products = OracleDataBase.getInstance().getProducts(1, Integer.parseInt(request.getParameter("category")), 0, 0);
                            products = (List<Product>) em.createNamedQuery("GET_PRODUCTS_FOR_CATEGORY").setParameter(1, Integer.parseInt(request.getParameter("category"))).setParameter(2, 0).setParameter(3, 0).setParameter(4, 1000000000).setParameter(5, 1000000000).setParameter(6, 1).setParameter(7, 1).getResultList();
                            System.out.println("Products - " + products);
                        }
                    } else {
//                categoryID = Integer.parseInt(request.getParameter("category"));
//                countProduct = OracleDataBase.getInstance().getCount(Integer.parseInt(request.getParameter("category")), minPrice, maxPrice);
//                        countProduct = ((Number) em.createNamedQuery("GET_COUNT_PRODUCTS_FOR_CATEGORY").setParameter(1, Integer.parseInt(request.getParameter("category"))).setParameter(2, minPrice).setParameter(3, minPrice).setParameter(4, maxPrice).setParameter(5, maxPrice).getSingleResult()).intValue();
                        countProduct = ((Number) em.createNamedQuery("GET_COUNT_PRODUCTS_FOR_ALL").setParameter(1, 0).setParameter(2, 0).setParameter(3, 1000000000).setParameter(4, 1000000000).getSingleResult()).intValue();
                       //todo  products =
                        if (request.getParameter("page") != null && request.getParameter("page") != ""
                                && request.getParameter("page").length() > 0) {
                            if (request.getParameter("minPrice") != null && request.getParameter("minPrice") != ""
                                    && request.getParameter("minPrice").length() > 0
                                    && request.getParameter("maxPrice") != null && request.getParameter("maxPrice") != ""
                                    && request.getParameter("maxPrice").length() > 0) {
                                minPrice = Integer.parseInt(request.getParameter("minPrice"));
                                maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
                                if (minPrice == 0 & maxPrice == 0) {
                                    products = (List<Product>) em.createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, 0).setParameter(2, 0).setParameter(3, 1000000000).setParameter(4, 1000000000).setParameter(5, Integer.parseInt(request.getParameter("page"))).setParameter(6, Integer.parseInt(request.getParameter("page"))).getResultList();
                                } else {
                                    products = (List<Product>) em.createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, minPrice).setParameter(2, minPrice).setParameter(3, maxPrice).setParameter(4, maxPrice).setParameter(5, Integer.parseInt(request.getParameter("page"))).setParameter(6, Integer.parseInt(request.getParameter("page"))).getResultList();
                                }
                            } else {
                                products = (List<Product>) em.createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, 1).setParameter(2, 1).setParameter(3, 1000000000).setParameter(4, 1000000000).setParameter(5, Integer.parseInt(request.getParameter("page"))).setParameter(6, Integer.parseInt(request.getParameter("page"))).getResultList();
                            }
                        } else {
                            products = (List<Product>) em.createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, 0).setParameter(2, 0).setParameter(3, 1000000000).setParameter(4, 1000000000).setParameter(5, 1).setParameter(6, 1).getResultList();
                        }
                    }
                }
        } else {
            categoryID = 0;
//            countProduct = OracleDataBase.getInstance().getCount(0, 0, 0);
            countProduct = ((Number) em.createNamedQuery("GET_COUNT_PRODUCTS_FOR_ALL").setParameter(1, 0).setParameter(2, 0).setParameter(3, 1000000000).setParameter(4, 1000000000).getSingleResult()).intValue();
//            products = OracleDataBase.getInstance().getProducts(1, 0, 0, 0);
//            products = (List<Product>) em.createNamedQuery("GET_ALL_PRODUCTS").getResultList();
            products = (List<Product>) em.createNamedQuery("GET_PRODUCTS_FOR_ALL_CATEGORIES").setParameter(1, 0).setParameter(2, 0).setParameter(3, 1000000000).setParameter(4, 1000000000).setParameter(5, 1).setParameter(6, 1).getResultList();
        }
        System.out.println("Category - " + categoryID);
        System.out.println("Count Product - " + countProduct);

        categoryList = null;
        users = null;
        pictures = null;
//        categoryList = OracleDataBase.getInstance().getAllCategories();
        categoryList = (List<Category>) em.createNamedQuery("GET_ALL_CATEGORIES").getResultList();
//        users = OracleDataBase.getInstance().getAllUsers();
        users = (List<User>) em.createNamedQuery("GET_ALL_USERS").getResultList();
//        pictures = OracleDataBase.getInstance().getAllPictures();
        pictures = (List<Picture>) em.createNamedQuery("GET_ALL_PICTURES").getResultList();
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
