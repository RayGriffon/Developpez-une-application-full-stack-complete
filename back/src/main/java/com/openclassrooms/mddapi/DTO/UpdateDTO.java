package com.openclassrooms.mddapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO utilisé pour mettre à jour le profil utilisateur.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDTO {
  /** Nouveau nom d'utilisateur */
  private String username;

  /** Ancien mot de passe pour vérification */
  private String password;

  /** Nouveau mot de passe à définir */
  private String newPassword;
}
