package ua.sumdu.greenberg.servlets;

import org.apache.log4j.Logger;

import com.octo.captcha.service.CaptchaServiceException;
import ua.sumdu.greenberg.model.CaptchaServiceSingleton;
import ua.sumdu.greenberg.model.Messager;
import ua.sumdu.greenberg.model.objects.User;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
* This servlet working with register.jsp.
*
* Created by Greenberg Dima <gdvdima2008@yandex.ru>
*/
public class RegisterServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RegisterServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

	    boolean isResponseCorrect = false;
        String captchaId = request.getSession().getId();
        String captcha = request.getParameter("captcha");

        System.out.println(captcha);

        try {
        	isResponseCorrect =
        		CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, captcha);
        } catch (CaptchaServiceException e) {
        	e.printStackTrace();
        }

        if (!isResponseCorrect) {
        	sendResponse(response, "<result>Wrong captcha!</result>");
        } else if ("registerData".equals(request.getParameter("action"))) {
            if (!(((Number) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("LOGIN_IS_FREE").setParameter(1, request.getParameter("login")).getSingleResult()).intValue() == 0)) {
                               sendResponse(response, "<result>This login is busy</result>");
            } else if (!(((Number) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("EMAIL_IS_FREE").setParameter(1, request.getParameter("email")).getSingleResult()).intValue() == 0)) {
                                sendResponse(response, "<result>This email is busy</result>");
            } else {
                ((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
                User user = new User();
                user.setLogin(request.getParameter("login"));
                user.setPassword(request.getParameter("password"));
                user.setName(request.getParameter("firstName"));
                user.setSecondName(request.getParameter("secondName"));
                user.setBirth(new Date(Long.parseLong(request.getParameter("age"))));
                user.seteMail(request.getParameter("email"));
                user.setPhone(request.getParameter("phone"));
                user.setRegistrationDate(new Timestamp(new Date().getTime()));
                user.setStatus("user");
                user.setActive("unactivated");
                user.setBaned("unbanned");
                ((EntityManager) request.getSession().getAttribute("em")).persist(user);
                ((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();

                Messager.registrationMail(request.getParameter("login"), request.getParameter("firstName"), request.getParameter("email"));
                sendResponse(response, "<result>OK</result>");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("jsp/register.jsp");
        rd.forward(request, response);
    }

    private void sendResponse(HttpServletResponse response, String text) {
        try (PrintWriter pw = response.getWriter()) {
            pw.println(text);
        } catch (IOException e) {
            log.error(e);
        }
    }

}