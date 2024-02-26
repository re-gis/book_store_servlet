<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.store.book.beans.Book" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart</title>
    <style>
        * {
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
        }

        /* Container */
        .container {
            width: 80%;
            margin: 20px auto;
        }

        .card-container {
            width: 60%;
            display: flex;
            flex-direction: column;
            gap: 0.5em;
        }

        input {
            outline: none;
        }

        /* Card */
        .card {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            margin-bottom: 20px;
        }

        .card-body {
            padding: 20px;
        }

        .card-title {
            margin-top: 0;
            margin-bottom: 10px;
            font-size: 1.2em;
            color: #333;
        }

        .card-text {
            margin-bottom: 5px;
            color: #666;
        }

        /* Navbar */
        .navbar {
            background-color: #333;
            overflow: hidden;
            display: flex;
            justify-content: space-between;
            color: #f2f2f2;
        }

        .navbar a {
            display: flex;
            padding: 14px 20px;
            text-decoration: none;
            color: inherit;
        }

        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }

        .navbar a.active {
            background-color: #4CAF50;
            color: white;
        }

        .navbar-right {
            display: flex;
        }

        .navbar-right a {
            margin-left: 10px;
            color: #f2f2f2;
        }

        /* Login Button */
        .btn-login {
            background-color: #4CAF50 !important;
            color: white !important;
            padding: 14px 20px !important;
            margin: 8px 0 !important;
            border: none !important;
            border-radius: 4px !important;
            cursor: pointer !important;
            width: 100% !important;
        }

        .btn-login:hover {
            background-color: #45a049;
        }

        .delete-btn {
            background-color: red !important;
            color: white !important;
            border: none !important;
            border-radius: 4px !important;
            padding: 8px 16px !important;
            cursor: pointer !important;
            width: 30% !important;
        }

        .delete-btn:hover {
            background-color: #cc0000 !important;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .container {
                width: 90%;
            }
        }

        /* Additional styles for the "Place Order" button */
        .place-order-btn {
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 14px 20px;
            margin-top: 20px;
            cursor: pointer;
            width: 100%;
        }

        .place-order-btn:hover {
            background-color: #45a049;
        }

        /* Navbar */
         .navbar {
            background-color: #333;
            overflow: hidden;
            display: flex; /* Add flex display */
            justify-content: space-between; /* Add space between elements */
        }

        .navbar a {
            float: left;
            display: flex;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;

        }

        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }

        .navbar a.active {
            background-color: #4CAF50;
            color: white;
        }

        .navbar-right {
    float: right;
}

/* Place Order button */
        .place-order-btn {
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 14px 20px;
            margin-top: 20px;
            cursor: pointer;
            width: 100%;
        }

        .place-order-btn:hover {
            background-color: #45a049;
        }

        /* Form styling */
        form {
            margin-top: 20px;
        }

        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 12px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin-top: 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="navbar">
    <a href="/book_store/home" class="active">Home</a>
    <div class="navbar-right">
        <a href="/book_store/logout" style="float: right;">Logout</a>
    </div>
</div>
<div class="container">
    
    <h2><%= session.getAttribute("username") %>, Your Cart</h2>
    
    <% List<Book> cartItems = (List<Book>) session.getAttribute("cart_books"); %>
    <% if (cartItems != null && !cartItems.isEmpty()) { %>
        <div class="card-container">
            <% for (Book book : cartItems) { %>
                <div class="card">
                    <div class="card-body">
                        <h3 class="card-title"><a href="bookDetails.jsp?id=<%= book.getId() %>"><%= book.getTitle() %></a></h3>
                        <p class="card-text"><strong>Author:</strong> <%= book.getAuthor() %></p>
                        <p class="card-text"><strong>Price:</strong> $<%= book.getPrice() %></p>
                        <form action="remove-from-cart" method="post">
                        <input class="delete-btn" value="Remove from Cart" type="submit" />
                        </form>
                    </div>
                </div>
            <% } %>
        </div>
        <!-- Place Order button -->
        <form action="order" method="post">
    <input type="text" name="country" placeholder="Country" required />
    <input type="text" name="province" placeholder="Province" required />
    <input type="text" name="district" placeholder="District" required />
    <input type="submit" class="place-order-btn" value="Place Order" />
</form>
    <% } else { %>
        <p>Your cart is empty.</p>
    <% } %>
</div>
</body>
</html>
