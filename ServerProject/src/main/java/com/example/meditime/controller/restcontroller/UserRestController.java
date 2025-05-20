package com.example.meditime.controller.restcontroller;

import com.example.meditime.model.LoginRequest;
import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import com.example.meditime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("mobile")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    public ResponseEntity<Map<String, String>> processSignup(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        try {
            if (userService.emailExists(user.getEmail())) {
                response.put("status", "exists");
                return ResponseEntity.ok(response);
            }

            // Encode password
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            // Set default role (e.g., user role)
            user.setRoleId(2L);

            // Save user
            userRepository.save(user);

            response.put("status", "register");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "invalid");
            return ResponseEntity.ok(response);
        }
    }



    @GetMapping("/userCarer")
    public ResponseEntity<List<User>> getAllCarers() {
        try {
            List<User> carers = userService.findByRoleId(2L);
            return ResponseEntity.ok(carers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/assignCarerToClient")
    public ResponseEntity<Map<String, String>> assignCarerToClient(@RequestParam Long clientId, @RequestParam Long carerUserId) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<User> carer = userService.findById(carerUserId);
            if (carer.isPresent() && carer.get().getRoleId() == 2) {
                // Assign the carer to the client
                userService.assignCarerToClient(clientId, carerUserId);
                response.put("status", "carerAssigned");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "invalidCarer");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/removeCarerFromClient")
    public ResponseEntity<Map<String, String>> removeCarerFromClient(@RequestParam Long clientId) {
        // Response map to hold status messages
        Map<String, String> response = new HashMap<>();

        try {
            // Call the service to remove the carer association from the client
            userService.removeCarerFromClient(clientId);

            // If successful, return a response indicating the carer was removed
            response.put("status", "carerRemoved");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Print stack trace for debugging in case of an error
            e.printStackTrace();

            // Return an error response with HTTP 500 status code
            response.put("status", "error");
            return ResponseEntity.status(500).body(response);
        }
    }


    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<User> optionalUser = userService.findByEmail(request.getEmail());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    response.put("status", "login");
                    response.put("userId", user.getUserId());
                    response.put("roleId", user.getRoleId());
                    return ResponseEntity.ok(response);
                }
            }

            response.put("status", "invalid");

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "invalid");
        }

        return ResponseEntity.ok(response);
    }

}
