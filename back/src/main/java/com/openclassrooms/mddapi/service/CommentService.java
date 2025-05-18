package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.CommentDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service gérant la logique métier liée aux commentaires.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

  /**
   * Ajoute un commentaire à un post, associé à l'utilisateur authentifié.
   *
   * @param commentDTO      Données du commentaire.
   * @param authentication  Authentification en cours.
   * @return Le commentaire ajouté, encapsulé dans un DTO.
   */
    public CommentDTO addComment(CommentDTO commentDTO, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPost(postService.findById(commentDTO.getPostId()));
        comment.setAuthor(user);
        Comment savedComment = commentRepository.save(comment);
        return new CommentDTO(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getPost().getId(),
                savedComment.getAuthor().getUsername()
        );
    }

  /**
   * Récupère tous les commentaires associés à un post donné.
   *
   * @param postId L'identifiant du post.
   * @return Liste des commentaires convertis en DTO.
   */
    public List<CommentDTO> getCommentsByPost(int postId) {
        return commentRepository.findByPostId(postId).stream().map(comment ->
                new CommentDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getPost().getId(),
                        comment.getAuthor().getUsername()
                )
        ).collect(Collectors.toList());
    }
}
