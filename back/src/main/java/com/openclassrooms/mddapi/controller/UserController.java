package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.RegisterDTO;
import com.openclassrooms.mddapi.DTO.UserProfileDTO;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMe(Authentication authentication) {
        return ResponseEntity.ok(userService.getMe(authentication));
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestParam int userId, @RequestBody RegisterDTO request) {
        userService.updateUserProfile(userId, request);
        return ResponseEntity.ok("Profil mis à jour");
    }
}
