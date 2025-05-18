package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository gérant les opérations CRUD pour l'entité {@link Post}.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

  /**
   * Récupère une page de posts appartenant à une liste de topics.
   *
   * @param topics   La liste des topics ciblés.
   * @param pageable Les informations de pagination.
   * @return Une page de posts correspondant aux topics donnés.
   */
    Page<Post> findByTopicIn(List<Topic> topics, Pageable pageable);

}
