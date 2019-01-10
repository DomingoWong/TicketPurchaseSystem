package model;

/**
 * Store the column label of Database Table user
 * @author domingo
 *
 */
public class DatabaseTableColumnLabel {
    public static final String USER_USERNAME = "username";
    public static final String USER_EMAIL = "email"; //PRIMARY KEY
    public static final String USER_SALT = "salt";
    public static final String USER_HASHRESULT = "hashResult";
    
    public static final String EVENT_EVENTID = "eventId"; //PRIMARY KEY
    public static final String EVENT_OWNER = "owner"; //FOREIGN KEY user(email)
    public static final String EVENT_DESCRIPTION = "description";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_PRICE = "price";
    

    public static final String TICKET_TICKETID = "ticketId"; //int PRIMARY KEY
    public static final String TICKET_OWNER = "owner"; //string email FOREIGN KEY user(email)
    public static final String TICKET_EVENTID = "eventId"; //int FOREIGN KEY event(eventId)
    public static final String TICKET_PRICE = "price";//int 
    
}
