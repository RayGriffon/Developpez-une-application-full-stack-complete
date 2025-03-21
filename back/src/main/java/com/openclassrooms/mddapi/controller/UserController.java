package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.RegisterRequest;
import com.openclassrooms.mddapi.DTO.UserProfileResponse;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@RequestParam int userId) {
        UserProfileResponse profileResponse = userService.getUserProfile(userId);
        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestParam int userId, @RequestBody RegisterRequest request) {
        userService.updateUserProfile(userId, request);
        return ResponseEntity.ok("Profil mis à jour");
    }
}
