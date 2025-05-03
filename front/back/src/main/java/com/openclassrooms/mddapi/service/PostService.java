package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.CommentDTO;
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

    public List<PostDTO> getNewsFeed(int userId) {
        User user = userService.findById(userId);
        List<Topic> topics = user.getSubscribedTopics();
        return postRepository.findByTopicInOrderByCreatedAt(topics).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void createPost(int userId, int topicId, String title, String content) {
        User user = userService.findById(userId);
        Topic topic = topicService.findById(topicId);
        Post post = new Post();
        post.setAuthor(user);
        post.setTopic(topic);
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
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
}
