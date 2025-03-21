package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.LoginRequest;
import com.openclassrooms.mddapi.DTO.RegisterRequest;
import com.openclassrooms.mddapi.DTO.UserProfileResponse;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        save(user);
    }

    public String loginUser(LoginRequest request) {
        // @TODO Implémenter JWT et token
        User user = userRepository.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Erreur d'authenfication");
        }
        return "jwt-token";
    }

    public UserProfileResponse getUserProfile(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Erreur user"));
        return new UserProfileResponse(user.getEmail(), user.getUsername());
    }

    public void updateUserProfile(int userId, RegisterRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Erreur user"));
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

}
