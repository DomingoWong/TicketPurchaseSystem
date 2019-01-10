package test;

import server.servlet.LoginServlet;

public class LoginServletTest {

    public static void main(String[] args) {
        LoginServlet loginServlet = new LoginServlet();
        System.out.println(loginServlet.canLogin("", "admin"));
        System.out.println(loginServlet.canLogin("wongym.domingo", "admin"));
        System.out.println(loginServlet.canLogin("wongym.domingo@gmail.com", "admin1"));
        System.out.println(loginServlet.canLogin("wongym.domingo@gmail.com", "admin"));

    }

}
