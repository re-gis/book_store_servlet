package com.store.book.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
