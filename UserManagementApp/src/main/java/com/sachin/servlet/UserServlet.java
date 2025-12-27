package com.sachin.servlet;

import com.sachin.dao.UserDAO;
import com.sachin.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users/*") // The asterisk allows us to handle /users/1, /users/delete, etc.
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // Fetch from Database instead of ArrayList!
        List<User> userList = userDAO.getAllUsers();
        req.setAttribute("users", userList);
        req.getRequestDispatcher("/displayUsers.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        User user = new User(0, name, email); // ID is 0 because DB auto-increments
        userDAO.insertUser(user);
        resp.sendRedirect("users");
    }

    // This method intercepts EVERY request before doGet/doPost
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        if ("DELETE".equalsIgnoreCase(method)) {
            doDelete(req, resp);
        } else if ("PUT".equalsIgnoreCase(method)) {
            doPut(req, resp);
        } else {
            // If no special method, let the standard service() handle GET/POST
            super.service(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("userId"));

        // Call DAO instead of ArrayList removeIf
        userDAO.removeUser(id);

        // Redirect back to the list
        resp.sendRedirect("users");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("userId"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        // Call DAO instead of ArrayList loop
        userDAO.updateUser(new User(id, name, email));

        resp.sendRedirect("users");
    }
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
