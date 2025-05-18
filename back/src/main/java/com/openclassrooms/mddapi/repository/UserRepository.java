package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository permettant l'accès aux utilisateurs ({@link User}).
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Récupère un utilisateur à partir de son adresse email.
   *
   * @param email L'email de l'utilisateur.
   * @return L'utilisateur correspondant.
   */
    User findByEmail(String email);
}
