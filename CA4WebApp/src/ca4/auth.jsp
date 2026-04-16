<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Login / Sign Up</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 450px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 8px;
            border: 1px solid #ccc;
        }

        h1 {
            text-align: center;
        }

        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0 15px 0;
            box-sizing: border-box;
        }

        button {
            padding: 10px 16px;
            margin-right: 10px;
            cursor: pointer;
        }

        .message {
            margin-bottom: 15px;
            color: #333;
            font-weight: bold;
        }
    </style>
    <script>
        function setAction(actionType) {
            document.getElementById("action").value = actionType;
        }
    </script>
</head>
<body>

    <div class="container">
        <h1>Customer Access</h1>

        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            <div class="message"><%= message %></div>
        <%
            }
        %>

        <form action="auth" method="post">
            <input type="hidden" id="action" name="action" value="login">

            <label>Email</label>
            <input type="email" name="email" required>

            <label>Password</label>
            <input type="password" name="password" required>

            <button type="submit" onclick="setAction('login')">Login</button>
            <button type="submit" onclick="setAction('signup')">Sign Up</button>
        </form>
    </div>

</body>
</html>