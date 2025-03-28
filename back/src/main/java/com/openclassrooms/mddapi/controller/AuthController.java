package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.LoginDTO;
import com.openclassrooms.mddapi.DTO.RegisterDTO;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO request){
        userService.registerUser(request);
        return ResponseEntity.ok("Utilisateur créé avec succès");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO request) {
        String token = userService.loginUser(request);
        return ResponseEntity.ok(token);
    }
}
