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

public class EventCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UserHomeServlet.class.getName());
    private static final String createEventPageURL = "src/main/velocityTemplate/eventcreate.vm";
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "EventCreate doGet");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
        HttpSession currentSession = request.getSession(false);
        if(currentSession == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        } else {
            PrintWriter out = response.getWriter();
            out.println(createEventPage(createEventPageURL));
        }
    }
    
    private String createEventPage(String URL) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(URL);
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, ""+request.getParameter("price"));
        DatabaseManager database = new DatabaseManager();
        HttpSession currentSession = request.getSession();
        User user = (User)currentSession.getAttribute("user");
        String owner = user.getEmail();
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        logger.log(Level.INFO, "Owner: "+ owner +"; Name:"+name+"; Description:"+description+"; Price:"+price);
        
        database.createEvent(owner, name, description, price);
        
        response.sendRedirect(request.getContextPath()+"/userhome");
        
    }
}
