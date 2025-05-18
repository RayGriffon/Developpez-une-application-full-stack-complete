package com.openclassrooms.mddapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO représentant un sujet (topic) de discussion.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
  /** Identifiant unique du sujet */
  private int id;

  /** Nom du sujet */
  private String name;

  /** Description détaillée du sujet */
  private String description;
}
