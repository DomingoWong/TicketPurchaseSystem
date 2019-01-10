package server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Vector;
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

public class EventDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UserHomeServlet.class.getName());

    private static final String eventPage = "src/main/velocityTemplate/event.vm";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int eventId = Integer.valueOf(request.getParameter("eventId"));
        logger.log(Level.INFO, "EventId is: " + eventId);

        DatabaseManager database = new DatabaseManager();
        Event event = null;
        HttpSession currentSession = request.getSession(false);
        if(currentSession == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        } else {
            User user = (User) currentSession.getAttribute("user");
            logger.log(Level.INFO, ""+user.getEmail());

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            event = database.get_Event(eventId);

            PrintWriter out = response.getWriter();
            out.println(getMethodPage(eventPage, event, user));
        }
    }

    private String getMethodPage(String URL, Event event, User user) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(URL);
        VelocityContext context = new VelocityContext();
        context.put("event", event);
        context.put("user", user);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
