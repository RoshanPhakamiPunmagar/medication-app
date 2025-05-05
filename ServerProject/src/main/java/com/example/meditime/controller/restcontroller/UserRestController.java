package com.example.meditime.controller.restcontroller;

import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import com.example.meditime.security.JwtUtil;
import com.example.meditime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("mobile")
public class UserRestController {


    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String LOGIN = "login";
    private final String REGISTER = "register";
    private final String INVALID = "invalid";
    private final String EXISTS = "exists";

    @PostMapping("/user")
    public ResponseEntity<Map<String, String>> processSignup(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        try {
            if (userService.emailExists(user.getEmail())) {
                response.put("status", EXISTS);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            userService.addUserById(user.getName(), user.getEmail(), user.getPassword(), 2L);
            response.put("status", REGISTER);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", INVALID);
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


//    @PostMapping("/check")
//    public ResponseEntity<Map<String, String>> checkUser(@RequestParam String email, @RequestParam String password) {
//        Map<String, String> response = new HashMap<>();
//        try {
//            Optional<User> user = userRepository.findByEmail(email);
//
//            // Log encoded password
//            System.out.println("Encoded password: " + passwordEncoder.encode("password123"));
//
//            boolean x = authenticate(password, user.get().getPassword());
//
//            if (x) {
//                response.put("status", LOGIN);
//                return ResponseEntity.ok(response);
//            }
//        } catch (Exception e) {
//            response.put("status", INVALID);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }

    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkUser(@RequestParam String email, @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                boolean isAuthenticated = authenticate(password, user.getPassword());

                if (isAuthenticated) {
                    response.put("status", LOGIN);
                    response.put("role", user.getRole().getRoleName());
                    response.put("userId", user.getUserId());
                    return ResponseEntity.ok(response);
                }
            }
            response.put("status", INVALID);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.put("status", INVALID);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }




    public boolean authenticate(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
