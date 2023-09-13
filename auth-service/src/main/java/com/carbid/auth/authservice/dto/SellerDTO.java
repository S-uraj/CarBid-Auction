package com.carbid.auth.authservice.dto;

import com.carbid.auth.authservice.entity.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO extends UserData {
    private long sellerId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
}
