package com.openclassrooms.mddapi.DTO;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO représentant un commentaire associé à un post.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
  /** Identifiant unique du commentaire */
  private int id;

  /** Contenu textuel du commentaire */
  private String content;

  /** Date et heure de création du commentaire */
  private LocalDateTime createdAt;

  /** Identifiant du post auquel est lié le commentaire */
  private int postId;

  /** Nom ou identifiant de l'auteur du commentaire */
  private String author;
}
