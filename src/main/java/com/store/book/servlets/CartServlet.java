package com.store.book.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.beans.Book;
import com.store.book.beans.Cart;
import com.store.book.utils.CartUtils;
import com.store.book.utils.GetBookById;

public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        try {
            HttpSession session = req.getSession(true);
            String email = session.getAttribute("email").toString();

            // Retrieve cart items for the current user
            List<Integer> cartItems = CartUtils.getCartItemsByOwner(email);

            // Iterate over the cart items and retrieve the corresponding books
            for (int itemId : cartItems) {
                Book book = GetBookById.getBookById(itemId);
                if (book != null) {
                    books.add(book);
                }
            }
            System.out.println(session.getAttribute("items"));
            // Set the list of books in the session attribute
            session.setAttribute("items", books);

            // Redirect to cart.jsp
            resp.sendRedirect("cart.jsp");
        } catch (SQLException e) {
            // Redirect to cart.jsp with error code 500
            resp.sendRedirect("cart.jsp?error=500");
        }
    }
}
