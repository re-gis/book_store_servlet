package com.store.book.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.beans.Book;
import com.store.book.utils.CartUtils;
import com.store.book.utils.GetBookById;

public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = new ArrayList<Book>();
        try {

            HttpSession session = req.getSession(true);
            String email = session.getAttribute("email").toString();

            // Retrieve cart items for the current user
            List<Integer> cartItems = CartUtils.getCartItemsByOwner(email);
            session.setAttribute("items", cartItems);

            for (Integer item : cartItems) {
                // get the book from the database using the cartitem
                Book book = GetBookById.getBookById(item);
                books.add(book);
            }

            session.setAttribute("cart_books", books);

            // Redirect to cart.jsp
            resp.sendRedirect("cart.jsp");
            return;
        } catch (Exception e) {
            // Redirect to cart.jsp with error code 500
            resp.sendRedirect("cart.jsp?error=500");
            return;
        }
    }
}
