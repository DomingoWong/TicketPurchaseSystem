package server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import model.DatabaseManager;
import model.User;

public class UserInfoModifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UserInfoModifyServlet.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "DoGet - path is：" + request.getPathInfo());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        } else {
            PrintWriter out = response.getWriter();
            User user = (User) currentSession.getAttribute("user");
            out.println(userInfoModifyPage(user));
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatabaseManager database = new DatabaseManager();
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        } else {
            User user = (User) currentSession.getAttribute("user");
            String newUsername = (String) request.getParameter("username");
            String newPassword = (String) request.getParameter("password");
            database.set_User_Username(user.getEmail(), newUsername);
            database.set_User_HashResult(user.getEmail(), newPassword);
            response.sendRedirect(request.getContextPath()+"/login");
        }
    }


    private String userInfoModifyPage(User user) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate("src/main/velocityTemplate/userinfomodify.vm");
        VelocityContext context = new VelocityContext();
        context.put("user", user);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }
}
