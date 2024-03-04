<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.store.book.beans.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Orders</title>
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
        <h1 style="">Orders</h1>
       
            <% List<Order> orders = (List<Order>) session.getAttribute("orders"); %>
            <% if (orders != null && !orders.isEmpty()) { %>
             <table style="width:100%;border-collapse:collapse" border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Owner</th>
                    <th>Cart ID</th>
                    <th>Country</th>
                    <th>Province</th>
                    <th>District</th>
                    <th>action</th>
                </tr>
            </thead>
            <tbody>
            <% for (Order order : orders) { %>
                <tr>
                        <td><%= order.getId()%></td>
                        <td><%= order.getOwner()%></td>
                        <td><%= order.getCartId()%></td>
                        <td><%= order.getCountry()%></td>
                        <td><%= order.getProvince()%></td>
                        <td><%= order.getDistrict()%></td>
                        <td style="padding:0 20px;"><a href="/book_store/deliver?id=<%= order.getId()%>"><button style="padding-top:" class="place-order-btn">DELIVER</button></a></td>
                </tr>
            <% } %>
            <% } else { %>
                <tr>
                    <td colspan="6">No Order found!</td>
                </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
