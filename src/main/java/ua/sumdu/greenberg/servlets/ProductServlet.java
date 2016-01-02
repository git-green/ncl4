package ua.sumdu.greenberg.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.greenberg.model.objects.Following;
import ua.sumdu.greenberg.model.objects.Picture;
import ua.sumdu.greenberg.model.objects.Product;
import ua.sumdu.greenberg.model.objects.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
* This servlet working with product.jsp.
*
* Created by Greenberg Dima <gdvdima2008@yandex.ru>
*/
public class ProductServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(IndexServlet.class);
//    EntityManager em = Persistence.createEntityManagerFactory("JavaAuction").createEntityManager();
    private Product product;
    private List<Picture> pictures;
    private List<User> users;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        if ("clickBet".equals(request.getParameter("action"))) {
            product = (Product) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCT_BY_ID").setParameter(1, Integer.parseInt(request.getParameter("productID"))).getResultList().get(0);
            if (product.getCurrentPrice() > Integer.parseInt(request.getParameter("bet")) || (!product.isActive())) {
                sendResponse(response, "<result>Error: Your bet is small. </result>");
            } else {
                if (Integer.parseInt(request.getParameter("bet")) >= product.getBuyoutPrice()) {
                    sendResponse(response, "<result>OK</result>");
                } else {
                    ((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
                    product.setCurrentPrice(Integer.parseInt(request.getParameter("bet")));
                    product.setCurrentBuyerID(Integer.parseInt(request.getParameter("buyerID")));
                    Following f = new Following();
                    f.setProduct_id(product.getId());
                    f.setFollower_id(Integer.parseInt(request.getParameter("buyerID")));
                    ((EntityManager) request.getSession().getAttribute("em")).persist(f);
                    ((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();

                    // message
                    sendResponse(response, "<result>OK</result>");
                }
            }

        } else if ("realBuy".equals(request.getParameter("action"))) {
            ((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
            product.setCurrentPrice(product.getBuyoutPrice());
            product.setCurrentBuyerID(Integer.parseInt(request.getParameter("userID")));
            product.setEndDate(new Date());
            product.setActive("disactive");
            ((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
            // message
            sendResponse(response, "<result>OK</result>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;
            if (request.getParameter("id") != null && !request.getParameter("id").equals("") &&
                    ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCT_BY_ID").setParameter(1, Integer.parseInt(request.getParameter("id"))).getResultList() != null) {
                product = (Product) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCT_BY_ID").setParameter(1, Integer.parseInt(request.getParameter("id"))).getResultList().get(0);
                pictures = (List<Picture>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PICTURE_BY_ID").setParameter(1, Integer.parseInt(request.getParameter("id"))).getResultList();
                users = (List<User>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_USERS").getResultList();
                request.setAttribute("pictures", pictures);
                request.setAttribute("product", product);
                request.setAttribute("users", users);
                if (request.getParameter("page") != null && request.getParameter("page") != "") {
                    if ("buy".equals(request.getParameter("page"))) {
                        rd = request.getRequestDispatcher("jsp/buy.jsp");
                    } else {
                        rd = request.getRequestDispatcher("jsp/product.jsp");
                    }
                }else {
                    rd = request.getRequestDispatcher("jsp/product.jsp");
                }
                rd.forward(request, response);
            } else {
                rd = request.getRequestDispatcher("index");
                rd.forward(request, response);
            }

    }

    /**
     * This method send response.
     *
     * @param response - HttpServletResponse.
     * @param text     - message.
     */
    private void sendResponse(HttpServletResponse response, String text) {
        try (PrintWriter pw = response.getWriter()) {
            pw.println(text);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
