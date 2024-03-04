package com.store.book.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.utils.OrderUtils;

public class DeliverServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String id = req.getParameter("id");
            int i = Integer.parseInt(id);
            if (OrderUtils.deliverOrder(i)) {
                HttpSession session = req.getSession(true);
                session.removeAttribute("orders");
                resp.sendRedirect("admin.jsp");
                return;
            } else {
                resp.sendRedirect("error.jsp");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
