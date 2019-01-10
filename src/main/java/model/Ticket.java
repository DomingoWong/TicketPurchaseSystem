package model;

public class Ticket {

    public Ticket(int ticketId, String owner, int eventId, int price, String eventName) {
        this.setTicketId(ticketId);
        this.setOwner(owner);
        this.setEventId(eventId);
        this.setPrice(price);
        this.setEventName(eventName);
        
    }

    private int ticketId;
    private String owner;
    private int eventId;
    private String eventName;
    private int price;
    
    /**
     * @return the ticketId
     */
    public int getTicketId() {
        return ticketId;
    }
    /**
     * @param ticketId the ticketId to set
     */
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }
    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
    /**
     * @return the eventId
     */
    public int getEventId() {
        return eventId;
    }
    /**
     * @param eventId the eventId to set
     */
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }
    /**
     * @return the eventName
     */
    public String getEventName() {
        return eventName;
    }
    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
}
