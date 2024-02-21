package com.store.book.servlets;

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
import java.util.*;

import com.store.book.beans.*;

public class DeleteBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("admin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookId = request.getParameter("bookId");
        if (bookId != null) {
            try {
                System.out.println(bookId);
                int id = Integer.parseInt(bookId);
                boolean deleted = deleteBook(id);
                if (deleted) {
                    HttpSession session = request.getSession(true);
                    List<Book> books = GetBooks.getBooks();

                    session.setAttribute("books", books);
                    response.sendRedirect("admin.jsp");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendRedirect("admin.jsp?error=400");
        }
    }

    private boolean deleteBook(int bookId) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM book WHERE id = ?")) {
            stmt.setInt(1, bookId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
