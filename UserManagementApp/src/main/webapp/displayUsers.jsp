<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>User List</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 50px; background-color: #f0f2f5; }
        .container { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        h2 { color: #1a73e8; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #1a73e8; color: white; }
        tr:hover { background-color: #f1f1f1; }
        .back-link { display: inline-block; margin-top: 20px; text-decoration: none; color: #1a73e8; font-weight: bold; }
    </style>
</head>
<body>

<div class="container">
    <h2>Registered Users</h2>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>
                        <form action="users" method="POST" style="display:inline;">
                            <input type="hidden" name="_method" value="DELETE">
                            <input type="hidden" name="userId" value="${user.id}">
                            <button type="submit" onclick="return confirm('Are you sure?')">Delete</button>
                        </form>

                        <a href="editUser.jsp?id=${user.id}&name=${user.name}&email=${user.email}">Edit</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <a href="index.jsp" class="back-link">← Add Another User</a>
</div>

</body>
</html>