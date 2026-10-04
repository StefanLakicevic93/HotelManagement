package com.HotelManagement.controller.auth;

import com.HotelManagement.config.JwtUtil;
import com.HotelManagement.dto.AuthRequest;
import com.HotelManagement.dto.AuthResponse;
import com.HotelManagement.dto.SignupRequest;
import com.HotelManagement.dto.UserDto;
import com.HotelManagement.entity.User;
import com.HotelManagement.repository.UserRepository;
import com.HotelManagement.service.auth.AuthService;
import com.HotelManagement.service.jwt.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        try {
            UserDto userDto = authService.createUser(signupRequest);
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        } catch (EntityExistsException entityExistsException) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create user", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad credentials");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse();

        if(optionalUser.isPresent()) {
            authResponse.setJwt(jwt);
            authResponse.setUserRole(optionalUser.get().getUserRole());
            authResponse.setUserId(optionalUser.get().getId());
        }
        return authResponse;
    }

}
