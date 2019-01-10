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
import model.DatabaseTableColumnLabel;
import model.HashHelper;
import model.User;

/**
 * URI: /login
 * GET: return a login page, fill the form and click the button to login, click button do POST /login
 *      *login.vm
 * POST: check if the info could login, reture success or fail
 *      login success: set Session, then redirect to the UserHomeServlet /userhome, click button do GET /userhome
 *      login fail:    *loginfail.vm - give a form to login again, click button do POST /login
 *      
 * @author domingo
 *
 */
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(LoginServlet.class.getName());

    private static final String loginPageURL = "src/main/velocityTemplate/login.vm";
    private static final String loginFailPageURL = "src/main/velocityTemplate/loginfail.vm";

    /**
     * Do GET Method
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Login doGet");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        out.println(staticPage(loginPageURL));
    }

    /**
     * When click login button
     * Do POST Method
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatabaseManager database = new DatabaseManager();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(canLogin(email, password)) {
            logger.log(Level.INFO, "login success" +
                    "\nemail is: " + email + 
                    "\npassword is: " + password);
            
            User user = database.get_User(email);
            HttpSession currentSession = request.getSession();
            currentSession.setAttribute("user", user);
            
//            currentSession.setAttribute("email", email);
//            currentSession.setAttribute("password", password);
//            currentSession.setAttribute("username", database.get_User_Info(email, DatabaseTableColumnLabel.USER_USERNAME));
            response.sendRedirect(request.getContextPath()+"/userhome");
        } else {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            
            PrintWriter out = response.getWriter();
            out.println(staticPage(loginFailPageURL));
        }
    }

    /**
     * check if this password could login
     * @param email
     * @param password
     * @return
     */
    public boolean canLogin(String email, String password) {
        DatabaseManager database = new DatabaseManager();
        try {
            if(!email.equals("") && database.emailExist(email)) { //if email is not null, email exist
                String salt = database.get_User_Info(email, DatabaseTableColumnLabel.USER_SALT);
                String hash = HashHelper.hash(salt, password);
                String hashInDatabase = database.get_User_Info(email, DatabaseTableColumnLabel.USER_HASHRESULT);
                if(hash.equals(hashInDatabase)) { //if password after hashing could match
                    return true;
                } else {
                    logger.log(Level.INFO, "password incorrect");
                    return false;
                }
            } else {
                logger.log(Level.INFO, "email is null, or email donot exist");
                return false;
            }
        } catch (SQLException sqle){
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
        return false;
    }

    /**
     * Generate Static Page
     * @return
     */
    String staticPage(String URL) {

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(URL);
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }


}
