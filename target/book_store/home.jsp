<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.store.book.beans.Book" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <style>
    *{
        padding:0;
        margin:0;
        box-sizing:border-box;
    }
        /* Container */
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }

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

        /* Heading */
        h1, h2 {
            text-align: center;
        }

        /* Book */
        .book {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 20px;
        }

        /* Book Title */
        .book h2 {
            color: #333;
            margin-bottom: 5px;
        }

        /* Book Details */
        .book p {
            margin: 5px 0;
        }

        /* Strong Tags */
        .book strong {
            color: #666;
        }

        /* Book Link */
        .book a {
            text-decoration: none; /* Remove default underline */
            color: #333; /* Set link color */
            display: block; /* Make link fill the entire book div */
        }

        .span-text{
         text-transform: uppercase;
         color: #4CAF50;   
        }
        /* Navbar */
         .navbar {
            background-color: #333;
            overflow: hidden;
            display: flex; /* Add flex display */
            justify-content: space-between; /* Add space between elements */
        }

        .navbar>div{
            width:50%;
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

.navbar a {
    float: left;
    display: block;
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
    </style>
</head>
<body>
<div class="navbar">
    <a href="#" class="active">Home</a>
    <div class="navbar-right">
        <a href="/book_store/logout" style="float: right;">Logout</a>
        <a href="/book_store/cart" style="float: right;">Cart</a>
    </div>
</div>
    <div class="container">
        <h1>Welcome to the Bookstore <span class="span-text"><%= session.getAttribute("username")%></span></h1>
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
    </div>
</body>
</html>
