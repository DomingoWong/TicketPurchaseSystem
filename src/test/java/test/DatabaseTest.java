package test;
import java.sql.SQLException;
import java.util.Vector;

import model.DatabaseManager;
import model.Event;

public class DatabaseTest {

    public static void main(String[] args) throws SQLException {
//        new DatabaseManager().DatabaseManagerTest();
//        System.out.println(new DatabaseManager().emailExist("domingo@gmail.com"));
//        System.out.println(new DatabaseManager().emailExist("domingo@gmail.co"));
//        System.out.println(new DatabaseManager().register("domingo@gmail.co", "asd", "pass"));
//        System.out.println(new DatabaseManager().emailExist("domingo@gmail.co"));
//        System.out.println(new DatabaseManager().emailExist("e"));
//        System.out.println(new DatabaseManager().get_User_Info("wongym.domingo@gmail.com", "username"));
//        System.out.println(new DatabaseManager().get_User_Info("wongym.domingo@gmail.com", "email"));
//        System.out.println(new DatabaseManager().get_User_Info("wongym.domingo@gmail.com", "salt"));
//        System.out.println(new DatabaseManager().get_User_Info("wongym.domingo@gmail.com", "hashResult"));
//        Vector<Event> os = new DatabaseManager().get_Event_All();
//        for (Event o : os) {
//            System.out.println("EventId: "+o.getEventId()+"; EventName: "+o.getName()+"; EventDes: "+o.getDescription()+"; EventOwner: "+o.getOwner()+"; EventPrice: "+o.getPrice()); 
//        }
//        System.out.println(new DatabaseManager().get_Event(2).getName());
//        System.out.println(new DatabaseManager().get_User("admin").getEmail());
//        System.out.println(new DatabaseManager().createEvent("admin", "database test", "database test", 2));
//        System.out.println(new DatabaseManager().deleteEvent(4));
//        System.out.println(new DatabaseManager().get_Event_Name(2));
        System.out.println(new DatabaseManager().purchaseTicket(2, "admin"));
    }

}
