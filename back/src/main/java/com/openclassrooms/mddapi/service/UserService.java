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

/**
 * Service responsable de la gestion des utilisateurs.
 * Gère l'enregistrement, la connexion, la mise à jour de profil et l'authentification.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

  /**
   * Enregistre un nouvel utilisateur.
   *
   * @param request Les informations d'enregistrement de l'utilisateur.
   * @throws IllegalArgumentException si un utilisateur avec cet email existe déjà.
   */
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

  /**
   * Connecte un utilisateur et retourne un JWT s'il est authentifié.
   *
   * @param request Les informations de connexion.
   * @return Le token JWT généré.
   * @throws RuntimeException si les informations d'identification sont incorrectes.
   */
    public String loginUser(LoginDTO request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Erreur d'authenfication");
        }
        return jwtService.generateToken(new UsernamePasswordAuthenticationToken(user.getEmail(), null));
    }

  /**
   * Récupère le profil de l'utilisateur connecté.
   *
   * @param authentication L'objet d'authentification.
   * @return Le profil utilisateur correspondant.
   */
    public UserProfileDTO getMe(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        return new UserProfileDTO(
                user.getEmail(),
                user.getUsername(),
                user.getId(),
                user.getSubscribedTopics()
        );
    }

  /**
   * Récupère le profil d'un utilisateur à partir de son email.
   *
   * @param email L'email de l'utilisateur.
   * @return Le profil utilisateur.
   */
    public UserProfileDTO getMe(String email) {
      User user = userRepository.findByEmail(email);
      return new UserProfileDTO(
        user.getEmail(),
        user.getUsername(),
        user.getId(),
        user.getSubscribedTopics()
      );
    }

  /**
   * Met à jour le profil de l'utilisateur.
   *
   * @param request Les nouvelles informations à enregistrer.
   * @param email   L'email de l'utilisateur.
   * @throws RuntimeException si le mot de passe actuel est incorrect.
   */
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

  /**
   * Sauvegarde un utilisateur dans la base de données.
   *
   * @param user L'utilisateur à sauvegarder.
   */
  public void save(User user) {
        userRepository.save(user);
  }

  /**
   * Recherche un utilisateur par email.
   *
   * @param email L'email de l'utilisateur.
   * @return L'utilisateur correspondant.
   */
  public User findByEmail(String email) {
        return userRepository.findByEmail(email);
  }

}
