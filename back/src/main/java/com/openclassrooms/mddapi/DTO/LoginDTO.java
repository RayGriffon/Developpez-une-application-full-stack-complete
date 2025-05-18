package com.openclassrooms.mddapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO utilisé pour la connexion d'un utilisateur.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
  /** Adresse email de l'utilisateur */
  private String email;

  /** Mot de passe de l'utilisateur */
  private String password;
}
