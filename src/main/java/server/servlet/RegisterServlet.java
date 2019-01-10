package server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import model.DatabaseManager;

/**
 * URI: /register
 * GET: return a register page, when click do POST /register with information
 *      *register.vm
 * POST: get the information, check if it could register, return success or fail
 *      register success: *registersuccess.vm - register success page, click login button do GET /login
 *      register fail:    *registerfail.vm - give a form to register again, click button do POST /register
 * @author domingo
 *
 */
public class RegisterServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(RegisterServlet.class.getName());

    private static final String registerPageURL = "src/main/velocityTemplate/register.vm";
    private static final String registerSuccessPageURL = "src/main/velocityTemplate/registersuccess.vm";
    private static final String registerFailPageURL = "src/main/velocityTemplate/registerfail.vm";

    /**
     * Do Get Method
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Require register page");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        //if current status is login, then redirect to /login
        //        HttpSession currentSession = request.getSession(false);
        //        if (currentSession == null) {
        PrintWriter out = response.getWriter();
        out.println(staticPage(registerPageURL));
        //        } else {
        //            if (currentSession.getAttribute("email") != null) {
        //            response.sendRedirect(request.getContextPath() + "/login");
        //            }
        //        }

    }

    /**
     * When click register button
     * Do Post Method
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Click register button");
        DatabaseManager database = new DatabaseManager();

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //if this email has been registered, return register fail; else register success
        try {
            if(emailIsValid(email)) {
                if (database.register(email, username, password)) {
                    logger.log(Level.INFO, "Register success. The email is: " + request.getParameter("email"));
                    out.println(staticPage(registerSuccessPageURL));
                } else {
                    logger.log(Level.INFO, "Register fail.");// may failed access the database; 
                    out.println(staticPage(registerFailPageURL));
                }
            } else {
                logger.log(Level.INFO, "Email is not valid.");// may failed access the database; 
                out.println(staticPage(registerFailPageURL));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }
    }

    private boolean emailIsValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$"; 

        Pattern pat = Pattern.compile(emailRegex); 
        if (email.equals("")) 
            return false; 
        return pat.matcher(email).matches(); 
    }

    /**
     * Return the static page with the URL's velocity template
     * @param URL
     * @return the static page
     */
    private String staticPage(String URL) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(URL);
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }
}
