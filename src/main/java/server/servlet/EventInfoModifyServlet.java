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
import model.Event;
import model.User;

public class EventInfoModifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UserHomeServlet.class.getName());

    private static final String eventInfoModifyURL = "src/main/velocityTemplate/eventinfomodify.vm";
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "DoGet - path is：" + request.getPathInfo());
        DatabaseManager database = new DatabaseManager();
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        } else {
            PrintWriter out = response.getWriter();
            User user = (User) currentSession.getAttribute("user");
            Event event = database.get_Event(Integer.parseInt(request.getParameter("eventId")));
            out.println(eventInfoModifyPage(eventInfoModifyURL, user, event));
        }
    }

    private String eventInfoModifyPage(String URL, User user, Event event) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(URL);
        VelocityContext context = new VelocityContext();
        context.put("user", user);
        context.put("event", event);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatabaseManager database = new DatabaseManager();
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        } else {
            int eventId = Integer.parseInt(request.getParameter("eventId")); 
            String newName = (String) request.getParameter("name");
            String newDescription = (String) request.getParameter("description");
            int newPrice = Integer.parseInt(request.getParameter("price"));
            logger.log(Level.INFO, "new Name: "+newName);
            database.set_Event_All(eventId, newName, newDescription, newPrice);
            response.sendRedirect(request.getContextPath()+"/userhome");
        }
    }
}
