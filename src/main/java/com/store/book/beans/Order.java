package com.store.book.beans;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private int id;
    private String owner;
    private int cartId;
    private String country;
    private String province;
    private String district;
}
