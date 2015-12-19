package ua.sumdu.greenberg.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.greenberg.model.objects.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This servlet working with login.jsp.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class LoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LoginServlet.class);
    EntityManager em = Persistence.createEntityManagerFactory("JavaAuction").createEntityManager();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        if ("login".equals(request.getParameter("action"))) {
            log.info("Click login");
            User user = (User) em.createNamedQuery("AUTHORIZATION").setParameter(1, request.getParameter("login")).setParameter(2, request.getParameter("password")).getResultList().get(0);
            log.info("USER - " + user);
            if (user != null) {
                if (!user.isBanned()) {
                    if (user.isActivated()) {
                        request.getSession().setAttribute("user", user);
                        sendResponse(response, "<result>OK</result>");
                    } else {
                        sendResponse(response, "<result>Account don't active</result>");
                    }
                } else {
                    sendResponse(response, "<result>You are banned</result>");
                }
            } else {
                sendResponse(response, "<result>Login incorrect.</result>");
            }
        } else if ("loginEmail".equals(request.getParameter("action"))) {
            User res = (User) em.createNamedQuery("AUTHORIZATION_BY_EMAIL").setParameter(1, request.getParameter("login")).setParameter(2, request.getParameter("password")).getResultList().get(0);
            if (res != null) {
                if (!res.isBanned()) {
                    request.getSession().setAttribute("user", res);
                    sendResponse(response, "<result>OK</result>");
                } else {
                    sendResponse(response, "<result>You are banned</result>");
                }
            } else {
                sendResponse(response, "<result>Email incorrect.</result>");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("jsp/login.jsp");
        rd.forward(request, response);
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
