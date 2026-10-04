package com.HotelManagement.dto;

import com.HotelManagement.entity.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private UserRole userRole;

}
