package com.store.book.utils;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.store.book.beans.*;

public class CartUtils {
    public static Cart addToCart(int bookId, String ownerEmail) throws SQLException {
        Cart cart = null;
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO cart (owner, items) VALUES (?, ?::TEXT[])")) {

            // Get the cart by owner email
            cart = GetCartByOwner(ownerEmail);

            if (cart == null) {
                // If the cart doesn't exist, create a new one
                cart = new Cart();
                cart.setOwner(ownerEmail);
                cart.setItems(new int[] { bookId }); // Assuming bookId is the ID of the book to be added
                cart.setDelivered(false); // Assuming the item is not delivered yet
            } else {
                int[] existingItems = cart.getItems();
                int[] updatedItems = Arrays.copyOf(existingItems, existingItems.length + 1);
                updatedItems[existingItems.length] = bookId; // Assuming bookId is the ID of the book to be added
                cart.setItems(updatedItems);
            }

            // Set parameters and execute the SQL statement
            statement.setString(1, cart.getOwner());
            statement.setArray(2, connection.createArrayOf("TEXT",
                    Arrays.stream(cart.getItems()).mapToObj(String::valueOf).toArray()));

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cart;
    }

    public static Cart GetCartByOwner(String ownerEmail) throws SQLException {
        Cart cart = null;
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM cart WHERE owner = ?")) {
            statement.setString(1, ownerEmail);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cart = new Cart();
                    cart.setId(resultSet.getInt("id"));
                    cart.setDelivered(resultSet.getBoolean("delivered"));

                    Array itemsArray = resultSet.getArray("items");

                    if (itemsArray != null) {
                        cart.setItems((int[]) itemsArray.getArray());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;

    }

    public static List<Integer> getCartItemsByOwner(String ownerEmail) {
        List<Integer> cartItems = new ArrayList<>();
        String sql = "SELECT items FROM cart WHERE owner = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, ownerEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Array itemsArray = resultSet.getArray("items");

                    if (itemsArray != null) {
                        Integer[] itemsIntegerArray = (Integer[]) itemsArray.getArray();
                        for (Integer item : itemsIntegerArray) {
                            cartItems.add(item);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }
}
