package com.openclassrooms.mddapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO représentant un post avec ses détails et ses commentaires.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
  /** Identifiant unique du post */
  private int id;

  /** Titre du post */
  private String title;

  /** Contenu du post */
  private String content;

  /** Nom ou identifiant de l'auteur du post */
  private String author;

  /** Nom du sujet associé au post */
  private String topic;

  /** Date et heure de création du post */
  private LocalDateTime createdAt;

  /** Liste des commentaires associés à ce post */
  private List<CommentDTO> comments;
}
