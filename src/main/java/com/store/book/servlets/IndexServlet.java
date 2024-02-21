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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        try{

            if (session == null || session.getAttribute("username") == null) {
                resp.sendRedirect("login.jsp");
            } else {
                resp.sendRedirect("home.jsp");
            }
        }catch(Exception e){
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }
}
