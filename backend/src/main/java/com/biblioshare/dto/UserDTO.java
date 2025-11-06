package com.biblioshare.dto;

import com.biblioshare.entity.UserRole;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String city;
    private String address;
    private UserRole role;
}
