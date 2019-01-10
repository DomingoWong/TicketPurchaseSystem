package server;

//import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import server.servlet.*;

public class TicketPurchaseServer {
    
//  private static Logger log = Logger.getLogger(TicketPurchaseServer.class.getName());
    
    public static void main(String[] args) throws Exception {
        Server ticketPurchaseServer = new Server(7070);
        
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ticketPurchaseServer.setHandler(handler);

        handler.addServlet(DefaultServlet.class, "/");
        //--------- User -----------
        handler.addServlet(RegisterServlet.class, "/register");
        handler.addServlet(LoginServlet.class, "/login");
        handler.addServlet(LogoutServlet.class, "/logout");
        handler.addServlet(UserHomeServlet.class, "/userhome");
        handler.addServlet(UserInfoModifyServlet.class, "/resetuserinfo");
        
        //--------- Event ----------
        handler.addServlet(EventCreateServlet.class, "/createevent");
        handler.addServlet(EventInfoModifyServlet.class, "/reseteventinfo");
        handler.addServlet(EventListServlet.class, "/events");
        handler.addServlet(EventDeleteServlet.class, "/deleteevent");
        handler.addServlet(EventDetailServlet.class, "/event");
        
        //--------- Ticket ---------
        handler.addServlet(TicketTransferServlet.class, "/tickettransfer");
        handler.addServlet(TicketPurchaseServlet.class, "/ticketpurchase");
        
        ticketPurchaseServer.start();
        ticketPurchaseServer.join();
        
    }

}
