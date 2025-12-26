package com.sachin.servlet;

import com.sachin.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users") // This tells Tomcat to send any request to /users here
public class UserServlet extends HttpServlet {

    // Our "In-Memory" Database
    private List<User> userList = new ArrayList();

    @Override
    public void init() {
        // Pre-fill with one user so it's not empty
        userList.add(new User(1, "Sachin", "sachin@gmail.com"));
    }

    // GET: Fetch all users or one user
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<h1>User List</h1>");
        for(User u : userList) {
            out.println("<p>ID: " + u.getId() + " | Name: " + u.getName() + " | Email: " + u.getEmail() + "</p>");
        }
        out.println("<a href='index.html'>Back to Home</a>");
    }

    // POST: Save a new user
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = userList.size() + 1;
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        userList.add(new User(id, name, email));
        resp.sendRedirect("users"); // Go back to the list
    }

    // NOTE: HTML Forms don't support PUT/DELETE easily.
    // Usually, we use POST with a hidden "action" parameter or JavaScript.
    // For this basic version, we will focus on GET and POST first.

    /*
    What resp.sendRedirect does here
    This is one of the most important concepts in web development called the Post-Redirect-Get (PRG) Pattern.

    Here is exactly what resp.sendRedirect("users") does, step-by-step:
    1. The "Two-Request" Dance
    Unlike a normal method call, this triggers a conversation between the Server and the Browser.
     1. Step 1 (The POST): The browser sends the user data (name=John) to the server.
     2. Step 2 (The Logic): Your Servlet saves John to the ArrayList.
     3. Step 3 (The Command): Instead of sending HTML back immediately,
        your Servlet sends a special response code: 302 (Found) and a header Location: users.
            Translation: "I am done. Please go visit this other URL now."
     4. Step 4 (The Auto-Pilot): The Browser receives this 302 code.
        It automatically throws away the old POST request and sends a brand-new GET request to the users URL.
     5. Step 5 (The Result): The Server receives the GET request and sends back the HTML list of users.

     Why do we do this? (The "Refresh" Problem)
     Imagine if you didn't redirect. Imagine if, after saving the user, you just rendered the list immediately inside doPost.
        The Scenario: A user fills out the form, clicks "Save", and sees the list.
        The Accident: The user thinks "Oh, the page didn't load right," and hits Refresh (F5).
        The Result: The browser will pop up a warning: "Confirm Form Resubmission?"
        The Disaster: If they click "Yes," the browser sends the POST request again. You now have two "John" in your database.

     By using sendRedirect:
      1. The browser is forced to move to a new URL (the GET URL).
      2. The "last action" the browser remembers is a GET (viewing the list).
      3. If the user hits Refresh now, it just re-loads the list. It does not re-submit the form

         Command                      "sendRedirect(""users"")"
     Technical Action               Sends HTTP 302 Status code.
     Who does the work?             The Browser makes a 2nd request.
     URL Bar                        Changes (e.g., from .../users (post) to .../users (get)).
     Data                           The request object is destroyed. A new request starts.
     Main Use                       After a form submission (POST) to prevent duplicates.

     It's a safety mechanism to keep the user's browser history clean!
     */
}
