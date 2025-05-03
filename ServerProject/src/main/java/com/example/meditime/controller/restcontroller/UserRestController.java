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



    @PostMapping("/check")
    public ResponseEntity<Map<String, String>> checkUser(@RequestParam String email, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<User> user = userRepository.findByEmail(email);

            System.out.println(passwordEncoder.encode("a"));
            boolean x = authenticate(password, user.get().getPassword());

            if (x) {
                response.put("status", LOGIN);
                System.out.println(email + " password" + x);
                return ResponseEntity.ok(response);
            }
        }
        catch (Exception e) {
            response.put("status", INVALID);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    public boolean authenticate(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
