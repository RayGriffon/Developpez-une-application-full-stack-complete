package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.CommentDTO;
import com.openclassrooms.mddapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des commentaires liés aux publications.
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

  /**
   * Ajoute un nouveau commentaire à un post.
   *
   * @param commentDTO le commentaire à ajouter
   * @param authentication les informations de l'utilisateur connecté
   * @return le commentaire ajouté
   */
    @PostMapping("/add")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment(commentDTO, authentication));
    }

  /**
   * Récupère tous les commentaires d’un post donné.
   *
   * @param postId l'identifiant du post
   * @return la liste des commentaires liés à ce post
   */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable int postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }
}
