package com.store.book.servlets;

import com.store.book.beans.Book;
import com.store.book.beans.User;
import com.store.book.dto.UserDTO;
import com.store.book.enums.URole;
import com.store.book.utils.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try {

            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String password = req.getParameter("password");
            String role = req.getParameter("role");
            URole r = URole.USER;

            if (role != null && role.toLowerCase() == "admin") {
                r = URole.ADMIN;
            }

            if (username == null || email == null || password == null || phone == null) {
                response.sendRedirect("register.jsp?error=400");
                return;
            }

            if (!password.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*()-_+=]).{8,20}$")) {
                response.sendRedirect("register.jsp?error=2");
                return;
            }

            if (!email.matches("^\\w+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                response.sendRedirect("register.jsp?error=3");
                return;
            }

            if (!phone.matches("^250\\d{9}") && !phone.matches("^07\\d{8}$")) {
                response.sendRedirect("register.jsp?error=4");
                return;
            }

            Optional<User> eUser = getUser(email);
            if (eUser.isPresent()) {
                response.sendRedirect("register.jsp?error=exists");
                return;
            }

            if (registerUser(username, email, phone, password, r)) {
                response.sendRedirect("login.jsp");
                return;
            }

            response.sendRedirect("register.jsp?error=500");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private Optional<User> getUser(String email) {
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    return Optional.of(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private boolean registerUser(String username, String email, String phone, String password, URole role)
            throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO users (username, email, password, phone, role)VALUES(?, ?, ?, ?, ?)")) {
            String r = null;
            switch (role) {
                case USER:
                    r = "user";
                    break;
                case ADMIN:
                    r = "admin";
                    break;
                default:
                    throw new Exception("Role not accepted");
            }
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, phone);
            stmt.setString(5, r);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
