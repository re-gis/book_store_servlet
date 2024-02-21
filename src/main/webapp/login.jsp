<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        /* Container */
        .container {
            width: 50%;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        /* Login Title */
        .login-title {
            text-align: center;
        }

        /* Form Label */
        .form-label {
            display: block;
            margin-bottom: 5px;
        }

        /* Form Input */
        .form-input {
            width: calc(100% - 10px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        /* Login Button */
        .btn-login {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }

        .btn-login:hover {
            background-color: #45a049;
        }

        /* Error Message */
        .error-msg {
            color: red;
            margin-top: 10px;
        }

        /* Register Link */
        .register-link {
            text-align: center;
        }

        .register-link a {
            color: #007bff;
            text-decoration: none;
        }

        .register-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="login-title">Login</h2>
        <form action="login" method="post">
            <label for="email" class="form-label">Email:</label>
            <input type="email" id="email" name="email" class="form-input" required>
            <label for="password" class="form-label">Password:</label>
            <input type="password" id="password" name="password" class="form-input" required>
            <button type="submit" class="btn-login">Login</button>
        </form>
        <% if (request.getParameter("error") != null) { %>
            <p class="error-msg">Invalid email or password</p>
        <% } %>
        <p class="register-link">Don't have an account? <a href="register.jsp" class="register-link">Register</a></p>
    </div>
</body>
</html>
