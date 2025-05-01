package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.TopicDTO;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @PostMapping("/subscribe/{topicId}")
    public void subscribe(@PathVariable int topicId, Principal principal) {
        topicService.subscribeUserToTopic(principal.getName(), topicId);
    }

    @PostMapping("/unsubscribe/{topicId}")
    public void unsubscribe(@PathVariable int topicId, Principal principal) {
        topicService.unsubscribeUserFromTopic(principal.getName(), topicId);
    }

    @GetMapping("/subscribed")
    public List<TopicDTO> getSubscribedTopics(Principal principal) {
        return topicService.getSubscribedTopics(principal.getName());
    }
}
