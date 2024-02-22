package com.store.book.utils;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.store.book.beans.*;

public class CartUtils {
    public static Cart addToCart(String bookId, String ownerEmail) throws SQLException {
        Cart cart = null;
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Get the cart by owner email
            cart = GetCartByOwner(ownerEmail);

            if (cart == null) {
                // If the cart doesn't exist, create a new one
                cart = new Cart();
                cart.setOwner(ownerEmail);
                cart.setItems(new String[] { bookId }); // Assuming bookId is the ID of the book to be added
                cart.setDelivered(false); // Assuming the item is not delivered yet

                // Insert the new cart into the database
                try (PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO cart (owner, items, delivered) VALUES (?, ?::TEXT[], ?)")) {
                    insertStatement.setString(1, cart.getOwner());
                    insertStatement.setArray(2, connection.createArrayOf("TEXT", cart.getItems()));
                    insertStatement.setBoolean(3, false);
                    insertStatement.executeUpdate();
                }
            } else {
                // If the cart already exists, add the item to the existing cart
                String[] existingItems = cart.getItems();
                String[] updatedItems = Arrays.copyOf(existingItems, existingItems.length + 1);
                updatedItems[existingItems.length] = bookId; // Assuming bookId is the ID of the book to be added
                cart.setItems(updatedItems);

                // Update the existing cart in the database
                try (PreparedStatement updateStatement = connection.prepareStatement(
                        "UPDATE cart SET items = ?::TEXT[] WHERE owner = ?")) {
                    updateStatement.setArray(1, connection.createArrayOf("TEXT", cart.getItems()));
                    updateStatement.setString(2, cart.getOwner());
                    updateStatement.executeUpdate();
                }
            }
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
                        cart.setItems((String[]) itemsArray.getArray());
                    }
                    System.out.println(cart.getItems());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;

    }

    public static List<Integer> getCartItemsByOwner(String ownerEmail) {
        List<Integer> cartItems = new ArrayList<>();
        String sql = "SELECT unnest(items) as item FROM cart WHERE owner = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, ownerEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int item = resultSet.getInt("item");
                    cartItems.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }
}
