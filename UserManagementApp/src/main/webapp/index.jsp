<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Management - Add</title>
    <style>
        body { font-family: sans-serif; margin: 50px; line-height: 1.6; }
        .container { max-width: 400px; background: #f9f9f9; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        input { width: 100%; padding: 8px; margin: 10px 0; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; cursor: pointer; border: none; border-radius: 4px; margin-top: 10px; }
        .btn-save { background-color: #28a745; color: white; }
        /* Style for our new View button */
        .btn-view { background-color: #007bff; color: white; text-decoration: none; display: inline-block; text-align: center; }
        .divider { margin: 20px 0; border-top: 1px solid #ccc; text-align: center; }
    </style>
</head>
<body>

<div class="container">
    <h2>Add New User</h2>
    <form action="users" method="POST">
        <label>Name:</label>
        <input type="text" name="name" required placeholder="Enter name">

        <label>Email:</label>
        <input type="email" name="email" required placeholder="Enter email">

        <button type="submit" class="btn-save">Save User</button>
    </form>

    <div class="divider">OR</div>

    <a href="users" class="btn-view" style="width: 100%; padding: 10px; box-sizing: border-box;">View All Users</a>
</div>

</body>
</html>