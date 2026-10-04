package com.HotelManagement.service.auth;

import com.HotelManagement.dto.SignupRequest;
import com.HotelManagement.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

}
