package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.CommentDTO;
import com.openclassrooms.mddapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment(commentDTO, authentication));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable int postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }
}
