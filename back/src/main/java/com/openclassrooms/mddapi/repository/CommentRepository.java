package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository permettant l'accès aux entités {@link Comment} en base de données.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  /**
   * Récupère tous les commentaires associés à un post spécifique.
   *
   * @param postId L'identifiant du post.
   * @return La liste des commentaires liés au post donné.
   */
    List<Comment> findByPostId(int postId);
}
