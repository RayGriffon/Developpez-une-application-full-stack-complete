package com.openclassrooms.mddapi.DTO;

import com.openclassrooms.mddapi.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO représentant le profil d'un utilisateur connecté.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
  /** Identifiant unique de l'utilisateur */
  private int id;

  /** Adresse email de l'utilisateur */
  private String email;

  /** Nom d'utilisateur */
  private String username;

  public UserProfileDTO(String email, String username, int id, List<Topic> subscribedTopics) {
    this.email = email;
    this.username = username;
    this.id = id;
  }
}
