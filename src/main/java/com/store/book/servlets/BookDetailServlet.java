package com.store.book.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.utils.GetBookById;
import com.store.book.beans.Book;

public class BookDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("bookDetais.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            Book book = GetBookById.getBookById(Integer.parseInt(id));

            HttpSession session = req.getSession(true);
            session.setAttribute("book", book);
            resp.sendRedirect("bookDetails.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("bookDetails.jsp?error=500");
        }
    }
}
