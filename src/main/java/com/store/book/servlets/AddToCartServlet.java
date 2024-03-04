package com.store.book.servlets;

import java.io.IOException;

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
            if(email == null){
                return;
            }
            String bookIdParam = req.getParameter("bookId");
            if (bookIdParam == null) {
                // Handle the case where bookId is null, such as redirecting to an error page
                resp.sendRedirect("bookDetails.jsp?error=400");
                return; // End the method execution
            }

            int bookId = Integer.parseInt(bookIdParam);
            String bId = String.valueOf(bookId);
            // Add the book to the user's cart
            Cart cart = CartUtils.addToCart(bId, email);
            if (cart == null) {
                resp.sendRedirect("bookDetails.jsp?error=500");
            }

            session.setAttribute("cart", cart);
            resp.sendRedirect("home.jsp");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("bookDetails.jsp?error=500");
        }
    }

}
