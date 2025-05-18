package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.UpdateDTO;
import com.openclassrooms.mddapi.DTO.UserProfileDTO;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Contrôleur pour la gestion des informations de profil utilisateur.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

  /**
   * Récupère le profil de l'utilisateur actuellement connecté.
   *
   * @param authentication les informations d'authentification
   * @return les données du profil utilisateur
   */
    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMe(Authentication authentication) {
        return ResponseEntity.ok(userService.getMe(authentication));
    }

  /**
   * Met à jour le profil de l’utilisateur.
   *
   * @param request les nouvelles informations du profil
   * @param principal l'utilisateur authentifié
   * @return le profil mis à jour
   */
  @PutMapping("/profile")
  public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody UpdateDTO request, Principal principal) {
    userService.updateUserProfile(request, principal.getName());
    UserProfileDTO updatedProfile = userService.getMe(principal.getName());
    return ResponseEntity.ok(updatedProfile);
  }

}
