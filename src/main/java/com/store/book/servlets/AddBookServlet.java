package com.store.book.servlets;

import com.store.book.beans.Book;
import com.store.book.utils.DatabaseUtil;
import com.store.book.utils.GetBooks;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AddBookServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("addBook.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve book details from the request parameters
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        double price = Double.parseDouble(request.getParameter("price"));

        try {
            if (addBook(title, author, price)) {
                List<Book> books = GetBooks.getBooks();
                HttpSession session = request.getSession(true);
                session.setAttribute("books", books);

                response.sendRedirect("admin.jsp");
                return;
            }
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("addBook.jsp?error=500");
    }

    private boolean addBook(String title, String author, double price) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("INSERT INTO book (title, author, price) VALUES (?, ?, ?)")) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
