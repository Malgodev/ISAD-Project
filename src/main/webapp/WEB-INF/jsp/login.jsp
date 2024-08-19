<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>
<body>

<div>
    <button onclick="window.location.href='<%= request.getContextPath() %>/login'">Login</button>
    <button onclick="window.location.href='<%= request.getContextPath() %>/register'">Register</button>
    <button onclick="window.location.href='<%= request.getContextPath() %>/change-password'">Change Password</button>
    <button onclick="window.location.href='<%= request.getContextPath() %>/forgot-password'">Forgot Password</button>
</div>

<!-- Login Form -->
<% if ("login".equals(request.getAttribute("mode"))) { %>
    <h2>Login</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <button type="submit">Login</button>
    </form>
<% } %>

<!-- Register Form -->
<% if ("register".equals(request.getAttribute("mode"))) { %>
    <h2>Register</h2>
    <form action="${pageContext.request.contextPath}/register" method="post">
        <label for="newUsername">New Username:</label>
        <input type="text" id="newUsername" name="username" required><br><br>
        <label for="newEmail">New Email:</label>
        <input type="email" id="newEmail" name="email" required><br><br>
        <label for="newPassword">New Password:</label>
        <input type="password" id="newPassword" name="password" required><br><br>
        <button type="submit">Register</button>
    </form>
<% } %>

<!-- Change Password Form -->
<% if ("changePassword".equals(request.getAttribute("mode"))) { %>
    <h2>Change Password</h2>
    <form action="${pageContext.request.contextPath}/change-password" method="post">
        <label for="currentUsername">Username:</label>
        <input type="text" id="currentUsername" name="username" required><br><br>
        <label for="currentPassword">Current Password:</label>
        <input type="password" id="currentPassword" name="password" required><br><br>
        <label for="newPasswordChange">New Password:</label>
        <input type="password" id="newPasswordChange" name="newPassword" required><br><br>
        <button type="submit">Change Password</button>
    </form>
<% } %>

<!-- Forgot Password Form -->
<% if ("forgotPassword".equals(request.getAttribute("mode"))) { %>
    <h2>Forgot Password</h2>
    <form action="${pageContext.request.contextPath}/forgot-password" method="post">
        <label for="forgotUsername">Username:</label>
        <input type="text" id="forgotUsername" name="username" required><br><br>
        <label for="forgotNewPassword">New Password:</label>
        <input type="password" id="forgotNewPassword" name="newPassword" required><br><br>
        <button type="submit">Reset Password</button>
    </form>
<% } %>

<p><%= request.getAttribute("error") %></p>
<p><%= request.getAttribute("message") %></p>

</body>
</html>
