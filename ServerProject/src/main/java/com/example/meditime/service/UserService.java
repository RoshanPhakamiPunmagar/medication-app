//Amy Wickham 121785021
package com.example.meditime.service;

import com.example.meditime.model.Client;
import com.example.meditime.model.Role;
import com.example.meditime.model.User;
import com.example.meditime.repository.ClientRepository;
import com.example.meditime.repository.RoleRepository;
import com.example.meditime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MailgunService mailgunService;


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);  // Fetch user by ID from UserRepository
    }

    public List<User> findByRoleId(Long roleId) {
        return userRepository.findByRoleId(roleId);
    }


    public void assignCarerToClient(Long clientId, Long carerUserId) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.setCarerUserId(carerUserId);
            clientRepository.save(client);
            System.out.println("Assigning client " + clientId + " to carer " + carerUserId);

        } else {
            throw new RuntimeException("Client not found");
        }
    }


    @Transactional
    public void removeCarerFromClient(Long clientId) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.setCarerUserId(null); // Unassign the carer
            clientRepository.save(client);
        } else {
            throw new RuntimeException("Client not found");
        }
    }




    // Return all users in the system
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Autowired
private PasswordEncoder passwordEncoder;



    public void addUser(String name, String email, String password, String roleName) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        user.setRoleId(role.getRoleId());


        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setEmailVerified(false);

        userRepository.save(user);

        mailgunService.sendVerificationEmail(user.getEmail(), token);

        // ADD THIS LINE FOR DEBUGGING
        System.out.println("Generated verification token: " + token);
    }



    public void addUserById(String name, String email, String password, Long roleId) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoleId(roleId);
        userRepository.save(user);
    }



    // Delete a user by ID
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Check if email already exists
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found for user"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(role.getRoleName())
                .build();
    }


    public User validateUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }


}
