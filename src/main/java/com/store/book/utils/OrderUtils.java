package com.store.book.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderUtils {
    public static boolean PlaceOrder(String useremail, int cartId, String country, String province, String district) {
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ")) {
            return false;

        } catch (Exception e) {
        }
        return false;
    }
}
