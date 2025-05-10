package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.LoginDTO;
import com.openclassrooms.mddapi.DTO.RegisterDTO;
import com.openclassrooms.mddapi.DTO.UpdateDTO;
import com.openclassrooms.mddapi.DTO.UserProfileDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDTO request) {
        if(userRepository.findByEmail(request.getEmail()) != null){
            throw new IllegalArgumentException("Utilisateur déjà existant");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        save(user);
    }

    public String loginUser(LoginDTO request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Erreur d'authenfication");
        }
        return jwtService.generateToken(new UsernamePasswordAuthenticationToken(user.getEmail(), null));
    }

    public UserProfileDTO getMe(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        return new UserProfileDTO(
                user.getEmail(),
                user.getUsername(),
                user.getId(),
                user.getSubscribedTopics()
        );
    }

  public void updateUserProfile(UpdateDTO request, String email) {
    User user = userRepository.findByEmail(email);

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Mot de passe actuel incorrect.");
    }

    user.setUsername(request.getUsername());

    if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
      user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    userRepository.save(user);
  }

  public void save(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
