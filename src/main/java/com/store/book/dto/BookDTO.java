package com.store.book.dto;

import com.store.book.utils.DatabaseUtil;
import lombok.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class BookDTO {
    private String title;
    private String author;
    private double price;

    public int generatebookId() {
        int bookId = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) FROM book")) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    bookId = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookId + 1;
    }
}
