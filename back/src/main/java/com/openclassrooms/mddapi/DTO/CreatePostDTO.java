package com.openclassrooms.mddapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO utilisé pour créer un nouveau post.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDTO {
  /** Titre du post */
  private String title;

  /** Contenu du post */
  private String content;

  /** Identifiant du sujet associé au post */
  private int topic;
}
