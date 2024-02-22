package com.store.book.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderUtils {
    public static boolean PlaceOrder(String useremail, int cartId, String country, String province, String district) {
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO orders (cartId, country, province, district, owner) VALUES (?,?,?,?,?)")) {

            preparedStatement.setInt(1, cartId);
            preparedStatement.setString(2, country);
            preparedStatement.setString(3, province);
            preparedStatement.setString(4, district);
            preparedStatement.setString(5, useremail);

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
