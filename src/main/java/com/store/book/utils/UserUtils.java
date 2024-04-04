package com.store.book.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.store.book.beans.User;

public class UserUtils {
    public static final User getUserByEmail(String email) throws Exception {
        try (
                Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            stmt.setString(1, email);
            // adding the comment
            ResultSet set = stmt.executeQuery();
            String e = set.getString("email");
            String uname = set.getString("username");
            String phone = set.getString("phone");
            int id = set.getInt("id");

            User user = new User();
            user.setEmail(e);
            user.setPhone(phone);
            user.setId(id);
            user.setUsername(uname);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
