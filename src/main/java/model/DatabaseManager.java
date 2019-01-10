package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private final static String SQLUSERNAME = "user48";
    private final static String SQLPASSWORD = "user48";
    private final static String SQLDATABASE = "user48";
    private final static String SQLDRIVER = "com.mysql.cj.jdbc.Driver";
//    private final static String SQLSERVER =  "jdbc:mysql://localhost:3306/";
    private final static String SQLSERVER =  "jdbc:mysql://sql.cs.usfca.edu:3306/";
    private final static String SQLTIMEZONE = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static Logger logger = Logger.getLogger(DatabaseManager.class.getName());

    /**
     * Constructor
     * Set Driver
     */
    public DatabaseManager () {
        try {
            Class.forName(SQLDRIVER).newInstance(); // load driver
        }
        catch (Exception e) {
            System.err.println("Can't find driver");
            System.exit(1);
        }
    }
    public void DatabaseManagerTest() {
        try {
            String sqlQuery = "SELECT * FROM user;";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                System.out.println(email);
            }

            resultSet.close();
            statement.close();
            connection.close(); logger.log(Level.INFO, "SQL connection close");//log
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
    }

    /**
     * check if the email has existed in the user table
     * @param email
     * @return exist - ture; not exist - false;
     * @throws SQLException
     */
    public boolean emailExist(String email) throws SQLException {
        String sqlQuery = "SELECT email FROM user WHERE email='" + email + "';";
        Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
        if(resultSet.next()) {
            resultSet.close();
            statement.close();
            connection.close(); logger.log(Level.INFO, "SQL connection close");
            return true;
        } else {
            resultSet.close();
            statement.close();
            connection.close(); logger.log(Level.INFO, "SQL connection close");
            return false;
        }
    }

    /**
     * Check if there exist the email. Then register.
     * Register, if success return true, if failed return false.
     * @param email
     * @param password
     * @return register success or not
     */
    public boolean register(String email, String username, String password) throws SQLException{
        if (emailExist(email) || username.equals("") || password.equals("")) {
            return false;
        } else {
            String salt = SaltGenerator.generate();
            String sqlQuery = "INSERT INTO user (email, username, salt, hashResult) VALUES ('"
                    + email + "', '"
                    + username + "', '"
                    + salt + "', '"
                    + HashHelper.hash(salt, password)
                    + "');";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            return true;
        }
    }

    /**
     * Get one user's info from table 'user'.
     * Can only return one element, because email is the primary key;
     * @param email
     * @param columnLabel
     * @return
     */
    public String get_User_Info(String email, String columnLabel) {
        String queryResult = null;
        try {
            String sqlQuery = "SELECT * FROM user WHERE email='"+ email +"';";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            resultSet.next(); 
            queryResult = resultSet.getString(columnLabel);
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
        return queryResult;
    }

    /**
     * Set table user's username which has this email 
     * @param email
     * @param value
     * @return
     */
    public boolean set_User_Username(String email, String value) {
        try {
            String sqlQuery = "UPDATE user SET username='" + value +"' WHERE email='"+ email +"';";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            return true;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
        return false;
    }

    /**
     * Set table user's hashresult which has this email
     * @param email
     * @param password
     * @return
     */
    public boolean set_User_HashResult(String email, String password) {
        try {
            String salt = get_User_Info(email, DatabaseTableColumnLabel.USER_SALT);
            String hashResult = HashHelper.hash(salt, password);
            String sqlQuery = "UPDATE user SET hashResult='" + hashResult +"' WHERE email='"+ email +"';";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            return true;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
        return false;
    }

    /**
     * Get all event in the database
     * @return
     */
    public Vector<Event> get_Event_All() {
        String sqlQuery = "SELECT * FROM event;";
        Connection connection;
        Vector<Event> allEvents = new Vector<Event>();
        try {
            connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            while(resultSet.next()) {
                allEvents.add(
                        new Event(
                                resultSet.getInt(DatabaseTableColumnLabel.EVENT_EVENTID), 
                                resultSet.getString(DatabaseTableColumnLabel.EVENT_OWNER),
                                resultSet.getString(DatabaseTableColumnLabel.EVENT_DESCRIPTION),
                                resultSet.getString(DatabaseTableColumnLabel.EVENT_NAME),
                                resultSet.getInt(DatabaseTableColumnLabel.EVENT_PRICE)
                                )
                        );
            }
            return allEvents;
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return allEvents;
    }

    /**
     * Get all events whose owner is email
     * @param email
     * @return
     */
    public Vector<Event> get_Event_All(String email) {
        String sqlQuery = "SELECT * FROM event WHERE owner='" + email +"';";
        Connection connection;
        Vector<Event> allEvents = new Vector<Event>();
        try {
            connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            while(resultSet.next()) {
                allEvents.add(
                        new Event(
                                resultSet.getInt(DatabaseTableColumnLabel.EVENT_EVENTID), 
                                resultSet.getString(DatabaseTableColumnLabel.EVENT_OWNER),
                                resultSet.getString(DatabaseTableColumnLabel.EVENT_DESCRIPTION),
                                resultSet.getString(DatabaseTableColumnLabel.EVENT_NAME),
                                resultSet.getInt(DatabaseTableColumnLabel.EVENT_PRICE)
                                )
                        );
            }
            return allEvents;
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return allEvents;
    }

    /**
     * Get event name whose id is eventId 
     * @param eventId
     * @return
     */
    public String get_Event_Name(int eventId) {
        String queryResult = null;
        try {
            String sqlQuery = "SELECT name FROM event WHERE eventId="+ eventId +";";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            resultSet.next(); 
            queryResult = resultSet.getString(DatabaseTableColumnLabel.EVENT_NAME);
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
        return queryResult;
    }
    
    /**
     * Modify event information
     * @param eventId
     * @param name
     * @param description
     * @param price
     * @return
     */
    public boolean set_Event_All(int eventId, String name, String description, int price) {
        try {
            String sqlQuery = "UPDATE event SET name='" + name + "',"
                    + "description='" + description + "',"
                    + "price=" + price
                    + " WHERE eventId="+ eventId +";";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            return true;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
            return false;
        }
        
    }
    
    /**
     * Get the event whose id is eventId
     * @param eventId
     * @return
     */
    public Event get_Event(int eventId) {
        try {
            String sqlQuery = "SELECT * FROM event WHERE eventId="+ eventId +";";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            resultSet.next(); 
            Event event = new Event(
                    Integer.parseInt(resultSet.getString(DatabaseTableColumnLabel.EVENT_EVENTID)),
                    resultSet.getString(DatabaseTableColumnLabel.EVENT_OWNER),
                    resultSet.getString(DatabaseTableColumnLabel.EVENT_DESCRIPTION),
                    resultSet.getString(DatabaseTableColumnLabel.EVENT_NAME),
                    Integer.parseInt(resultSet.getString(DatabaseTableColumnLabel.EVENT_PRICE))
                    );
            return event;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
        return null;

    }

    /**
     * Get the user whose email is email
     * @param email
     * @return
     */
    public User get_User(String email) {
        try {
            String sqlQuery = "SELECT * FROM user WHERE email='"+ email +"';";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            resultSet.next(); 
            User user = new User(
                    resultSet.getString(DatabaseTableColumnLabel.USER_USERNAME),
                    resultSet.getString(DatabaseTableColumnLabel.USER_EMAIL),
                    resultSet.getString(DatabaseTableColumnLabel.USER_SALT),
                    resultSet.getString(DatabaseTableColumnLabel.USER_HASHRESULT)
                    );
            return user;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
        return null;

    }

    /**
     * Create event with these information
     * @return
     */
    public boolean createEvent(String owner, String name, String description, int price) {
        String sqlQuery = "INSERT INTO event (owner, name, description, price) VALUES ('"
                + owner + "', '"
                + name + "', '"
                + description + "', "
                + price 
                + ");";
        try {
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            return true;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "", sqle);
            return false;
        }

    }
    
    /**
     * Delete event whose id is eventId
     * @param eventId
     * @return
     */
    public boolean deleteEvent(int eventId) {
        String sqlQuery = "DELETE FROM event WHERE eventId=" + eventId+";";
        try {
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            return true;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "", sqle);
            return false;
        }
    }

    /**
     * Get all tickets that this user have
     * @param email
     * @return
     */
    public Vector<Ticket> get_Ticket_All(String email) {
        String sqlQuery = "SELECT * FROM ticket WHERE owner='" + email +"';";
        Connection connection;
        Vector<Ticket> allTickets = new Vector<Ticket>();
        try {
            connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            while(resultSet.next()) {
                allTickets.add(
                        new Ticket(
                                resultSet.getInt(DatabaseTableColumnLabel.TICKET_TICKETID), 
                                resultSet.getString(DatabaseTableColumnLabel.TICKET_OWNER),
                                resultSet.getInt(DatabaseTableColumnLabel.TICKET_EVENTID),
                                resultSet.getInt(DatabaseTableColumnLabel.TICKET_PRICE),
                                this.get_Event_Name(resultSet.getInt(DatabaseTableColumnLabel.TICKET_EVENTID))
                                )
                        );
            }
            return allTickets;
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return allTickets;
        
    }

    /**
     * Get the ticket whose id is ticketId
     * @param ticketId
     * @return
     */
    public Ticket get_Ticket(int ticketId) {
        try {
            String sqlQuery = "SELECT * FROM ticket WHERE ticketId="+ ticketId +";";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            resultSet.next(); 
            Ticket ticket = new Ticket(
                    resultSet.getInt(DatabaseTableColumnLabel.TICKET_TICKETID), 
                    resultSet.getString(DatabaseTableColumnLabel.TICKET_OWNER),
                    resultSet.getInt(DatabaseTableColumnLabel.TICKET_EVENTID),
                    resultSet.getInt(DatabaseTableColumnLabel.TICKET_PRICE),
                    this.get_Event_Name(resultSet.getInt(DatabaseTableColumnLabel.TICKET_EVENTID))
                    );
            
            return ticket;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
        return null;
    }
    
    /**
     * Set ticket owner 
     * @param ticketId
     * @param targetEmail
     * @return
     */
    public boolean set_Ticket_Owner(int ticketId, String targetEmail) {
        try {
            String sqlQuery = "UPDATE ticket SET owner='" + targetEmail +"' WHERE ticketId="+ ticketId +";";
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            return true;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);//log
        }
        return false;
        
    }

    public boolean purchaseTicket(int eventId, String email) {
        String sqlQuery = "INSERT INTO ticket (owner, eventId, price) VALUES ('"
                + email + "', "
                + eventId + ", "
                + this.get_Event(eventId).getPrice() 
                + ");";
        try {
            Connection connection = DriverManager.getConnection(SQLSERVER + SQLDATABASE + SQLTIMEZONE, SQLUSERNAME, SQLPASSWORD); logger.log(Level.INFO, "SQL connection success");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery); logger.log(Level.INFO, "SQL query: " + sqlQuery);
            return true;
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "", sqle);
            return false;
        }
    }
}
