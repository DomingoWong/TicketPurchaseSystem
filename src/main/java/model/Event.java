package model;

public class Event {
    public Event(int eventId, String owner, String description, String name, int price) {
        this.eventId = eventId;
        this.setOwner(owner);
        this.setDescription(description);
        this.setName(name);
        this.setPrice(price);
    }
    private int eventId;
    private String owner;
    private String description;
    private String name;
    private int price;
    public int getEventId() {
        return this.eventId;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
}
