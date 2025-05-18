package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.CreatePostDTO;
import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Contrôleur pour la gestion des publications (posts).
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

  /**
   * Récupère le fil d’actualité de l’utilisateur connecté.
   *
   * @param principal l'utilisateur authentifié
   * @param page numéro de page pour la pagination
   * @param size nombre d'éléments par page
   * @param sortDir direction du tri ("asc" ou "desc")
   * @return une page de publications personnalisée
   */
    @GetMapping("/feed")
    public Page<PostDTO> getFeed(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return postService.getNewsFeed(principal.getName(), page, size, sortDir);
    }

  /**
   * Crée une nouvelle publication.
   *
   * @param createPostDTO les données du post à créer
   * @param principal l'utilisateur authentifié
   */
    @PostMapping("/create")
    public void createPost(@RequestBody CreatePostDTO createPostDTO, Principal principal) {
        postService.createPost(createPostDTO, principal.getName());
    }

  /**
   * Récupère une publication par son identifiant.
   *
   * @param id identifiant du post
   * @return le post correspondant
   */
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

}
