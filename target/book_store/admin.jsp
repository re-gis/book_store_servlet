<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.store.book.beans.Book" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <style>
    *{
        padding: 0;
        margin: 0;
        box-sizing: border-box;
    }
        /* Additional CSS styles specific to admin.jsp */
.container {
    width: 80%;
    margin: auto;
    padding-top: 20px;
}

h1 {
    text-align: center;
    margin-bottom: 20px;
}

p {
    text-align: center;
    margin-bottom: 20px;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    margin-bottom: 10px;
}

li a {
    color: #007bff;
    text-decoration: none;
}

li a:hover {
    text-decoration: underline;
}

.button {
    display: inline-block;
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    width: 100%;
    text-align: center;
    text-decoration: none;
}

.button:hover {
    background-color: #45a049;
}
/* Additional CSS styles specific to admin.jsp */
.card-container {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
}

.card {
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
    overflow: hidden;
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

    </style>
</head>
<body>
<div class="navbar">
    <a href="admin.jsp" class="active">Home</a>
    <div class="navbar-right">
           <%
    String role1 = (String) session.getAttribute("role");
    if (role1 != null && role1.equals("admin")) { 
    %>
        <a href="/book_store/logout">Logout</a>
    <% } else { %>
        <a href="/book_store/logout">Logout</a>
        <a href="/book_store/cart">Cart</a>
<% } %>
    </div>
</div>
    <div class="container">
    <h1>Welcome to the Admin Panel</h1>
    <p>You have access to administrative features here.</p>

    <h2>Available Books</h2>
    <div class="card-container">
 
        <% 
        List<Book> books = (List<Book>) session.getAttribute("books");
           if (books != null && !books.isEmpty()) {
               for (Book book : books) { %>
                   <div class="card">
                       <div class="card-body">
                           <h3 class="card-title"><a href="bookDetails.jsp?id=<%= book.getId() %>"><%= book.getTitle() %></a></h3>
                           <p class="card-text"><strong>Author:</strong> <%= book.getAuthor() %></p>
                           <p class="card-text"><strong>Price:</strong> $<%= book.getPrice() %></p>
                       </div>
                   </div>
               <% }
           } else { %>
               <p>No books available.</p>
           <% } %>
    </div>

    <!-- Add a new book link -->
    <a class="button" href="addBook.jsp">Add a New Book</a>
</div>
</body>
</html>
