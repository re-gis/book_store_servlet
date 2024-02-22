package com.store.book.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.beans.Cart;
import com.store.book.utils.CartUtils;

public class AddToCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // get the current user the book id
            HttpSession session = req.getSession(true);
            String email = session.getAttribute("email").toString();
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            String bId = String.valueOf(bookId);
            // Add the book to the user's cart
            Cart cart = CartUtils.addToCart(bId, email);
            if (cart == null) {
                resp.sendRedirect("bookDetails.jsp?error=500");
            }

            session.setAttribute("cart", cart);
            resp.sendRedirect("cart.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("bookDetails.jsp?error=500");
        }
    }

}
