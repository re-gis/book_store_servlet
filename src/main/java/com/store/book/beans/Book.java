package com.store.book.beans;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
}
