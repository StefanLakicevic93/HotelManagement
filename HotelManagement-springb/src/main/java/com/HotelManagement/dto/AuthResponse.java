package com.HotelManagement.dto;

import com.HotelManagement.entity.UserRole;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;

    private Long userId;

    private UserRole userRole;

}
