<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; display: flex; justify-content: center; padding-top: 50px; }
        .card { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 400px; }
        h2 { color: #2c3e50; text-align: center; margin-bottom: 25px; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #34495e; font-weight: bold; }
        input[type="text"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background-color: #3498db; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; margin-top: 10px; transition: background 0.3s; }
        button:hover { background-color: #2980b9; }
        .back-link { display: block; text-align: center; margin-top: 15px; color: #7f8c8d; text-decoration: none; font-size: 14px; }
    </style>
</head>
<body>
    <div class="card">
        <h2>${user.id == 0 ? 'Add New User' : 'Edit Existing User'}</h2>
        <form:form action="save" modelAttribute="user" method="POST">
            <form:hidden path="id" />

            <div class="form-group">
                <label>Full Name</label>
                <form:input path="name" placeholder="Enter name..." />
            </div>

            <div class="form-group">
                <label>Email Address</label>
                <form:input path="email" placeholder="Enter email..." />
            </div>

            <button type="submit">Save User Data</button>
            <a href="list" class="back-link">← Back to List</a>
        </form:form>
    </div>
</body>
</html>