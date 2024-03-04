package com.store.book.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.beans.Cart;
import com.store.book.utils.CartUtils;
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

            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                int cartId = (int) cart.getId();
                if (!OrderUtils.PlaceOrder(useremail, cartId, country, province, district)) {
                    resp.sendRedirect("error.jsp");
                    return;
                }
                session.removeAttribute("cart_books");
                session.removeAttribute("items");

                // first delete the cart and the session
                if (!CartUtils.deleteCart(cartId)) {
                    resp.sendRedirect("error.jsp");
                    return;
                }
                resp.sendRedirect("home.jsp");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }

}
