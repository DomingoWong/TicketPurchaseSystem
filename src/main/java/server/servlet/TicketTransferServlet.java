package server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
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
import model.Ticket;

public class TicketTransferServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UserHomeServlet.class.getName());
    private static final String ticketTransferPage = "src/main/velocityTemplate/tickettransfer.vm";  
    private static final String transferFailPageURL = "src/main/velocityTemplate/tickettransferfail.vm";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "DoGet - path is：" + request.getPathInfo());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        HttpSession currentSession = request.getSession(false);
        if (currentSession == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        } else {
            PrintWriter out = response.getWriter();
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            DatabaseManager database = new DatabaseManager();
            Ticket ticket = database.get_Ticket(ticketId);
            out.println(ticketTransferPage(ticketTransferPage, ticket));
        }
    }

    private String ticketTransferPage(String URL, Ticket ticket) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(URL);
        VelocityContext context = new VelocityContext();
        context.put("ticket", ticket);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatabaseManager database = new DatabaseManager();
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
        try {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            String targetEmail = request.getParameter("email"); 
            logger.log(Level.INFO, "ticketId: "+ticketId+"; target email: "+ targetEmail);
            if(database.emailExist(targetEmail)) {
                database.set_Ticket_Owner(ticketId, targetEmail);
                response.sendRedirect(request.getContextPath()+"/userhome");
            } else {
                PrintWriter out = response.getWriter();
                out.println(transferFailPage(transferFailPageURL));
            }
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "", sqle);
        }

    }

    private String transferFailPage(String URL) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(URL);
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }
    
}
