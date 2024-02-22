package com.store.book.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.beans.Book;
import com.store.book.beans.Cart;
import com.store.book.utils.OrderUtils;

public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // if the user is an admin will get the orders and deliver them
        resp.sendRedirect("order.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // get the user address
            String country = req.getParameter("country");
            String province = req.getParameter("province");
            String district = req.getParameter("district");

            // get cart items from session and user email
            HttpSession session = req.getSession(true);
            String useremail = (String) session.getAttribute("email");
            List<Book> books = (List<Book>) session.getAttribute("cart_books");

            Cart cart = (Cart) session.getAttribute("cart");
            int cartId = (int) cart.getId();

            if(!OrderUtils.PlaceOrder(useremail, cartId, country, province, district)){
                resp.sendRedirect("error.jsp");
                return;
            }

            session.removeAttribute("cart");
            session.removeAttribute("cart_books");
            session.removeAttribute("items");
            resp.sendRedirect("home.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }

}
