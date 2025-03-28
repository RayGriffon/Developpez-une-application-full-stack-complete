package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.TopicDTO;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public List<TopicDTO> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping("/subscribe/{userId}/{topicId}")
    public void subscribe(@PathVariable int userId, @PathVariable int topicId) {
        topicService.subscribeUserToTopic(userId, topicId);
    }

    @PostMapping("/unsubscribe/{userId}/{topicId}")
    public void unsubscribe(@PathVariable int userId, @PathVariable int topicId) {
        topicService.unsubscribeUserFromTopic(userId, topicId);
    }
}
