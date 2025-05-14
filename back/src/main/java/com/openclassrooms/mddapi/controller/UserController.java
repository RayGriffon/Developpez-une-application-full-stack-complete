package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.UpdateDTO;
import com.openclassrooms.mddapi.DTO.UserProfileDTO;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
  public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody UpdateDTO request, Principal principal) {
    userService.updateUserProfile(request, principal.getName());
    UserProfileDTO updatedProfile = userService.getMe(principal.getName());
    return ResponseEntity.ok(updatedProfile);
  }

}
