package com.store.book.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.store.book.beans.Book;

public class GetBooks {
     // Method to retrieve books from the database
    public static List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book")) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");
                Book book = new Book(id, title, author, price);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("books");
        System.out.println(books);
        return books;
    }
}
