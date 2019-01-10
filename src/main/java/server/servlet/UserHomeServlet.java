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
import model.DatabaseTableColumnLabel;
import model.Event;
import model.Ticket;
import model.User;

/**
 * URI: /userhome
 * GET: User home, contain user's information, when click modify button do GET /resetuserinfo
 *      when click logout button do GET /logout
 *      TODO: show events that this user publish, and ticket that this user have
 * POST:
 * 
 * @author domingo
 *
 */
public class UserHomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UserHomeServlet.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "UserHome DoGet - path is：" + request.getPathInfo());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        DatabaseManager database = new DatabaseManager();

        HttpSession currentSession = request.getSession(false);

        if (currentSession == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            User user = (User) currentSession.getAttribute("user");
            
            Vector<Event> usersEvents = new Vector<Event>();
            usersEvents = database.get_Event_All(user.getEmail());
            
            Vector<Ticket> usersTickets = new Vector<Ticket>();
            usersTickets = database.get_Ticket_All(user.getEmail());
            
            PrintWriter out = response.getWriter();
            out.println(userHomePage(user, usersEvents, usersTickets));
        }
    }

    private String userHomePage(User user, Vector<Event> usersEvents, Vector<Ticket> usersTickets) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate("src/main/velocityTemplate/userhome.vm");
        VelocityContext context = new VelocityContext();
        context.put("user", user);
        context.put("event", usersEvents);
        context.put("ticket", usersTickets);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }


}
