# Project 4 Option 1 - Ticket Purchase Application

For this project you will implement a ticket purchase web application (i.e., your own EventBrite!). You will design and implement a two-tier web application with a Java (Jetty/Servlets) front end and an SQL backend. 

### Required Features

You *must* complete all of the following required features for a total of 55 points.

| Points |           Feature            | Description                                                  |
| :----: | :--------------------------: | :----------------------------------------------------------- |
|   5    |      User registration       | Provide a *Sign Up* button that allows a user to register to use your site. Users must be able to enter appropriate account information and user data must be saved appropriately in your system. Passwords must be salted and hashed before saving to the database. |
|   5    |       Login and logout       | Allow a user to login and logout of your site. Maintain the user session appropriately. |
|   5    |    View user information     | Display user account information including *details* for all events for which the user has purchased tickets. |
|   5    |         View events          | Display a list of all events.                                |
|   5    |          View event          | Display details for a specific event.                        |
|   5    |         Create event         | Allow the user to create a new event by entering all appropriate detail. |
|   5    |       Purchase tickets       | Allow the user to purchase tickets for an event.             |
|   5    |       Transfer tickets       | Allow the user to transfer tickets to another user.          |
|   5    | Relational database - Users  | Use a relational database to store *user account* data.      |
|   5    | Relational database - Events | Use a relational database to store *event* data.             |
|   5    |          Deployment          | Deployment.                                                  |

You may use additional database tables.

### Additional Features

Select up to 15 points of additional features. *No* extra credit will be awarded for implementing additional features.

| Points |         Feature          | Description                                                  |
| :----: | :----------------------: | :----------------------------------------------------------- |
|   5    | Show *n* events per page | Provide pagination to allow a user to see some specific number of events per page and scroll to the next page. |
|   5    |   Discount/VIP Tickets   | Provide the ability to specify some tickets as discounted (e.g., students) or more expensive (e.g., VIP). |
| ==5==  | ==Modify/delete event==  | ==Allow a user to modify or delete an event *that s/he has created*.== |
|   10   |          Images          | Integrate images into your site, allow a user to upload images when creating an event and display when the event is viewed. |
|   10   |     Sign in with...      | Use a "Sign in with" feature from your favorite site like Slack, Google, or Facebook. |
| ==10== |      ==Templates==       | ==Use [Velocity](http://velocity.apache.org/) or another template engine to generate your HTML.== |
|   10   |          Search          | Allow a user to search events for particular phrases or other features. |
|   10   |          Hosted          | Run on Amazon Web Services or another hosting site.          |
|   5    |         Branding         | Brand your site with a logo, color scheme, etc.              |

### Other Criteria

In addition, your project will be evaluated based on the following criteria for a total of 30 points.

| Points |      Criteria      |
| :----: | :----------------: |
|   10   |     Code Style     |
|   15   |    Code Quality    |
|   5    | Project Checkpoint |

### Requirements

1. You will use Servlets/Jetty as your web framework.
2. You will use a SQL database to store all data including user information, event information, and ticket transaction/purchase information. You are required to design the database tables. 

### Academic Dishonesty

Any work you submit is expected to be your own original work. If you use any web resources in developing your code you are strongly advised to cite those resources. The only exception to this rule is code that is posted on the class website. The URL of the resource you used in a comment in your code is fine. If I google even a single line of uncited code and find it on the internet you may get a 0 on the assignment or an F in the class. You may also get a 0 on the assignment or an F in the class if your solution is at all similar to that of any other student.

### Demostration

1. **Deployment**
2. Access '/' redirect to '/register'
3. Click <u>login</u> button to '/login', also for <u>Sign Up</u> button
4. **User registration**: show database(Sequel Pro); email is primary key
5. **Login then Logout**
6. Login again, **View user information**
7. Modify User Information: reset Username and password: check database - hashResult is change
8. Login again, click <u>Create Event</u> to **Create event**
9. click <u>Modify</u> to **modify or delete** the event
10. click <u>All Events</u> to **View events**
11. click <u>Detail</u> to **View event**
12. click <u>Purchase</u> to **Purchase tickets**
13. click <u>Transfer</u> to Transfer tickets
14. Show **database**(Sequel Pro), <u>Show Create Table Syntax</u> 
15. Show **velocity** template

