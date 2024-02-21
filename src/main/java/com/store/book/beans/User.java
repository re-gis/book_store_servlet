package com.store.book.beans;

import com.store.book.enums.URole;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class User {
    private int id;
    private String username;
    private String phone;
    private String email;
    private String password;
    private URole role;
}
