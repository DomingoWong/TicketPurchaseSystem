package server.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DatabaseManager;

public class EventDeleteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatabaseManager database = new DatabaseManager();
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        } else {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            database.deleteEvent(eventId);
            response.sendRedirect(request.getContextPath()+"/userhome");
        }
    }
}
