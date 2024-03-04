package com.store.book.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.beans.Order;
import com.store.book.utils.OrderUtils;

public class AdminOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // first get the orders from the database
        try {
            List<Order> orders = OrderUtils.getOrders();
            HttpSession session = req.getSession(true);
            session.setAttribute("orders", orders);
            resp.sendRedirect("orders.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
