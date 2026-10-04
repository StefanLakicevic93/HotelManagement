package com.HotelManagement.service.auth;

import com.HotelManagement.dto.SignupRequest;
import com.HotelManagement.dto.UserDto;
import com.HotelManagement.entity.User;
import com.HotelManagement.entity.UserRole;
import com.HotelManagement.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAcc() {
        Optional<User> adminAcc = userRepository.findByUserRole(UserRole.ADMIN);

        if(adminAcc.isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@test.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("adminp"));
            admin.setUserRole(UserRole.ADMIN);
            userRepository.save(admin);

            System.out.println("Admin account created successfully");
        } else {
            System.out.println("Admin account already exists");
        }
    }

    public UserDto createUser(SignupRequest signupRequest) {

        if(userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()) {
            throw new EntityExistsException("User already exists with this email: " + signupRequest.getEmail());
        }
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }

}
