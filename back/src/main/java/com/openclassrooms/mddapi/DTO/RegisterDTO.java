package com.openclassrooms.mddapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO utilisé pour l'enregistrement d'un nouvel utilisateur.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
  /** Adresse email de l'utilisateur */
  private String email;

  /** Nom d'utilisateur choisi */
  private String username;

  /** Mot de passe choisi */
  private String password;
}
