<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Edit User</title></head>
<body>
    <h2>Update User</h2>
    <form action="users" method="POST">
        <input type="hidden" name="_method" value="PUT">
        <input type="hidden" name="userId" value="${param.id}">

        Name: <input type="text" name="name" value="${param.name}"><br>
        Email: <input type="text" name="email" value="${param.email}"><br>

        <button type="submit">Update User</button>
    </form>
    <a href="users">Cancel</a>
</body>
</html>