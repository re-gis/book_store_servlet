<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.store.book.beans.Book" %>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Details</title>
    <style>
    *{
        padding: 0;
        margin: 0;
        box-sizing: border-box;
    }
        /* Container */
        .container {
            width: 50%;
            margin: 0 auto;
            padding: 20px;
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
        }

        .card-text {
            margin-bottom: 5px;
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
        .delete-btn {
            background-color:red;
        }
    </style>
</head>
<body>
<div class="navbar">
    <a href="#" class="active">Book Details</a>
    <div class="navbar-right">
    <%
    String role1 = (String) session.getAttribute("role");
    if (role1 != null && role1.equals("admin")) { 
    %>
<a href="admin.jsp">Home</a>
        <a href="/book_store/logout">Logout</a>
    <% } else { %>
    <a href="home.jsp">Home</a>
        <a href="/book_store/logout">Logout</a>
        <a href="/book_store/cart">Cart</a>
<% } %>


    </div>
</div>
<div class="container">
    <% 
    List<Book> books = (List<Book>) session.getAttribute("books");
    String bookIdParam = request.getParameter("id");
    int id = Integer.parseInt(bookIdParam);
    Book book = null;
    if (books != null && !books.isEmpty()) {
        // get the book equal to the id
        for(Book b : books){
            if(b.getId() == id){
                book = b;
            }
        }
    }
    if (book != null) { %>
        <div class="card">
            <div class="card-body">
                <h3 class="card-title"><%= book.getTitle() %></h3>
                <p class="card-text"><strong>Author:</strong> <%= book.getAuthor() %></p>
                <p class="card-text"><strong>Price:</strong> $<%= book.getPrice() %></p>
            </div>
        </div>
    <% } else { %>
        <p>No book details available...</p>
    <% } %>
   <% 
    String role = (String) session.getAttribute("role");
    if (role != null && role.equals("admin")) { 
%>
    <!-- Display the Delete button for admin -->
    <form action="deleteBook" method="post"> <!-- Replace deleteBook with your delete servlet URL -->
        <input type="hidden" name="bookId" value="<%= book.getId() %>">
        <button type="submit" class="btn-login delete-btn">Delete</button>
    </form>
<% } else { %>
    <!-- Display the Add to Cart button for regular users -->
    <form action="addToCart" method="post"> <!-- Replace addToCart with your add to cart servlet URL -->
        <input type="hidden" name="bookId" value="<%= book.getId() %>">
        <button type="submit" class="btn-login">Add To Cart</button>
    </form>
<% } %>

</div>
</body>
</html>
