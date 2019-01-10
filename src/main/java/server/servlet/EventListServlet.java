package server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Vector;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import model.DatabaseManager;
import model.Event;

public class EventListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
//    private static Logger logger = Logger.getLogger(UserHomeServlet.class.getName());
           
    private static final String eventsPage = "src/main/velocityTemplate/events.vm";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatabaseManager database = new DatabaseManager();
        Vector<Event> allEvents = new Vector<Event>();
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        allEvents = database.get_Event_All();
        
        PrintWriter out = response.getWriter();
        out.println(getMethodPage(eventsPage, allEvents));
    }
    
    /**
     * Generate Static Page
     * @param allEvents 
     * @return
     */
    String getMethodPage(String URL, Vector<Event> allEvents ) {

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(URL);
        VelocityContext context = new VelocityContext();
        context.put("eventList", allEvents);
        
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
    }
}
