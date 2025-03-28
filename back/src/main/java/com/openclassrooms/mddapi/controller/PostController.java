package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/feed/{userId}")
    public List<PostDTO> getFeed(@PathVariable int userId) {
        return postService.getNewsFeed(userId);
    }

    @PostMapping("/create")
    public void createPost(@RequestParam int userId, @RequestParam int topicId, @RequestParam String title, @RequestParam String content) {
        postService.createPost(userId, topicId, title, content);
    }
}
