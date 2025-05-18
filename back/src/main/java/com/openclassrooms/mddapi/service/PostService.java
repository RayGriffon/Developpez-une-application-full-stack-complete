package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.CommentDTO;
import com.openclassrooms.mddapi.DTO.CreatePostDTO;
import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsable de la gestion des publications (posts).
 * Gère la création, la récupération et la conversion en DTO des publications.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

  /**
   * Récupère le fil d'actualité d'un utilisateur basé sur les sujets auxquels il est abonné.
   *
   * @param email   L'email de l'utilisateur.
   * @param page    Le numéro de la page.
   * @param size    Le nombre d'éléments par page.
   * @param sortDir La direction du tri ("asc" ou "desc").
   * @return Une page de PostDTO correspondant au fil d'actualité.
   */
    public Page<PostDTO> getNewsFeed(String email, int page, int size, String sortDir) {
        User user = userService.findByEmail(email);
        List<Topic> topics = user.getSubscribedTopics();

        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending());

        return postRepository.findByTopicIn(topics, pageable)
                .map(this::convertToDTO);
    }

  /**
   * Crée une nouvelle publication à partir des données fournies.
   *
   * @param createPostDTO Les données de la publication à créer.
   * @param userName      Le nom (ou email) de l'auteur de la publication.
   */
    public void createPost(CreatePostDTO createPostDTO, String userName) {
        User user = userService.findByEmail(userName);
        Topic topic = topicService.findById(createPostDTO.getTopic());
        Post newPost = new Post();
        newPost.setAuthor(user);
        newPost.setTitle(createPostDTO.getTitle());
        newPost.setContent(createPostDTO.getContent());
        newPost.setTopic(topic);
        newPost.setCreatedAt(LocalDateTime.now());
        postRepository.save(newPost);
    }

  /**
   * Convertit un objet Post en PostDTO.
   *
   * @param post L'entité Post à convertir.
   * @return Le DTO correspondant.
   */
    private PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor().getUsername());
        dto.setTopic(post.getTopic().getName());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setComments(post.getComments().stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            return commentDTO;
        }).collect(Collectors.toList()));
        return dto;
    }

  /**
   * Recherche une publication par son identifiant.
   *
   * @param id L'identifiant de la publication.
   * @return La publication correspondante.
   */
    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow();
    }

  /**
   * Récupère une publication sous forme de DTO.
   *
   * @param id L'identifiant de la publication.
   * @return Le DTO correspondant à la publication.
   */
    public PostDTO getPostById(int id) {
        Post post = postRepository.findById(id).orElseThrow();
        return convertToDTO(post);
    }

}
