package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.CreatePostDTO;
import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/feed")
    public List<PostDTO> getFeed(Principal principal) {
        return postService.getNewsFeed(principal.getName());
    }

    @PostMapping("/create")
    public void createPost(@RequestBody CreatePostDTO createPostDTO, Principal principal) {
        postService.createPost(createPostDTO, principal.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

}
