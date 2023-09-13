package com.carbid.auth.authservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {
    private String userName;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String message;
}
