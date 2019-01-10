package model;

public class User {
    public User(String username, String email, String salt, String hashResult) {
        this.setUsername(username);
        this.setEmail(email);
        this.setSalt(salt);
        this.setHashResult(hashResult);
    }

    private String username;
    private String email;
    private String salt;
    private String hashResult;
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }
    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
    /**
     * @return the hashResult
     */
    public String getHashResult() {
        return hashResult;
    }
    /**
     * @param hashResult the hashResult to set
     */
    public void setHashResult(String hashResult) {
        this.hashResult = hashResult;
    }
    
}
