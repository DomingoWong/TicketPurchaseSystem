package server.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DatabaseManager;

public class TicketPurchaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UserHomeServlet.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, request.getParameter("email")+" purchase "+request.getParameter("eventId"));
        HttpSession currentSession = request.getSession(false);
        if(currentSession == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        } else {
            DatabaseManager database = new DatabaseManager();
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            String email = request.getParameter("email");
            database.purchaseTicket(eventId, email);
            response.sendRedirect(request.getContextPath()+"/userhome");
        } 

    }
}
