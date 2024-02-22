package com.store.book.servlets;

import com.store.book.beans.Book;
import com.store.book.beans.Cart;
import com.store.book.beans.User;
import com.store.book.enums.URole;
import com.store.book.utils.CartUtils;
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

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (password == null || email == null) {
                response.sendRedirect("login.jsp?error=1");
                return;
            }
            User user = authenticateUser(email, password);
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("email", user.getEmail());
                List<Book> books = GetBooks.getBooks();
                session.setAttribute("books", books);

                if (user.getRole().equals(URole.ADMIN)) {
                    String r = "admin";
                    session.setAttribute("role", r);
                    response.sendRedirect("admin.jsp");
                    return;
                } else {
                    Cart cart = CartUtils.GetCartByOwner(email);
                    String r = "user";
                    session.setAttribute("role", r);
                    session.setAttribute("cart", cart);
                    response.sendRedirect("home.jsp");
                    return;
                }
            } else {
                response.sendRedirect("login.jsp?error=1");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    public User authenticateUser(String email, String password) {
        User user = null;
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("SELECT * FROM users WHERE email=? AND password=?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setUsername(resultSet.getString("username"));
                    String r = resultSet.getString("role");
                    URole role = null;
                    if (r.equalsIgnoreCase("admin")) {
                        role = URole.ADMIN;
                    } else if (r.equalsIgnoreCase("user")) {
                        role = URole.USER;
                    } else {
                        throw new Exception("Role not found!");
                    }
                    user.setRole(role);
                    user.setPhone(resultSet.getString("phone"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
