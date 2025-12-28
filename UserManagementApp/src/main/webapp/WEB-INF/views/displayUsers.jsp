<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Management System</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
        .container { max-width: 900px; margin: auto; background: white; padding: 25px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); }

        .header-area { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; margin-bottom: 20px; padding-bottom: 10px; }
        h2 { color: #2c3e50; margin: 0; }

        .btn-add { background-color: #27ae60; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold; transition: 0.3s; }
        .btn-add:hover { background-color: #219150; box-shadow: 0 4px 8px rgba(0,0,0,0.2); }

        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th { background-color: #34495e; color: white; text-align: left; padding: 15px; }
        td { padding: 12px 15px; border-bottom: 1px solid #eee; color: #555; }
        tr:hover { background-color: #f9f9f9; }

        .action-links a { text-decoration: none; padding: 5px 12px; border-radius: 4px; font-size: 13px; font-weight: bold; margin-right: 5px; }
        .edit-link { color: #3498db; border: 1px solid #3498db; }
        .edit-link:hover { background: #3498db; color: white; }
        .delete-link { color: #e74c3c; border: 1px solid #e74c3c; }
        .delete-link:hover { background: #e74c3c; color: white; }

        .no-users { text-align: center; padding: 20px; color: #7f8c8d; font-style: italic; }
    </style>
</head>
<body>

<div class="container">
    <div class="header-area">
        <h2>User Management</h2>
        <a href="showForm" class="btn-add">+ Add New User</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email Address</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td><strong>${u.id}</strong></td>
                    <td>${u.name}</td>
                    <td>${u.email}</td>
                    <td class="action-links">
                        <a href="updateForm?userId=${u.id}" class="edit-link">Edit</a>
                        <a href="delete?userId=${u.id}"
                           class="delete-link"
                           onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty users}">
                <tr>
                    <td colspan="4" class="no-users">No users found in the database.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

</body>
</html>