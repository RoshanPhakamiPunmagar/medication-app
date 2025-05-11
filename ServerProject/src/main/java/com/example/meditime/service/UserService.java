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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);  // Fetch user by ID from UserRepository
    }

    public List<User> findByRole_Id(Long roleId) {
        return userRepository.findByRole_RoleId(roleId);
    }

    public void assignCarerToClient(Long clientId, Long carerUserId) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.setCarerUserId(carerUserId);
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
    user.setRole(role);

    userRepository.save(user);
}

    public void addUserById(String name, String email, String password, Long roleId) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
        user.setRole(role);

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


    @Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRole().getRoleName()) // Role must be a single string like "Carer"
            .build();
}

public User validateUser(String email, String password) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        if (user.getPassword().equals(password)) {
            return user;
        }
    }
    return null;
}

}
