<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.store.book.beans.Book" %>
<%@ page import="com.store.book.beans.Cart" %>
<%@ page import="com.store.book.utils.CartUtils" %>
<%@ page import="com.store.book.utils.GetBookById" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Shopping Cart</h1>
        <div class="cart-items">
            <% 
            out.println(session.getAttribute("items"));
            %>
        </div>
        <a href="index.jsp" class="btn-login">Continue Shopping</a>
    </div>
</body>
</html>
