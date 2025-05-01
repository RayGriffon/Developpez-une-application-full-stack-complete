package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.CommentDTO;
import com.openclassrooms.mddapi.DTO.CreatePostDTO;
import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    public List<PostDTO> getNewsFeed(String email) {
        User user = userService.findByEmail(email);
        List<Topic> topics = user.getSubscribedTopics();
        return postRepository.findByTopicInOrderByCreatedAt(topics)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


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

    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow();
    }

    public PostDTO getPostById(int id) {
        Post post = postRepository.findById(id).orElseThrow();
        return convertToDTO(post);
    }

}
