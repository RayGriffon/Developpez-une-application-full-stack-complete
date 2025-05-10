package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.TopicDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserService userService;

    public List<TopicDTO> getAllTopics() {
        return topicRepository.findAll().stream().map(topic -> {
            TopicDTO dto = new TopicDTO();
            dto.setId(topic.getId());
            dto.setName(topic.getName());
            dto.setDescription(topic.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    public void subscribeUserToTopic(String email, int topicId) {
        User user = userService.findByEmail(email);
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        user.getSubscribedTopics().add(topic);
        userService.save(user);
    }

    public void unsubscribeUserFromTopic(String email, int topicId) {
        User user = userService.findByEmail(email);
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        user.getSubscribedTopics().remove(topic);
        userService.save(user);
    }

    public List<TopicDTO> getSubscribedTopics(String email) {
        User user = userService.findByEmail(email);
        return user.getSubscribedTopics().stream().map(topic -> {
            TopicDTO dto = new TopicDTO();
            dto.setId(topic.getId());
            dto.setName(topic.getName());
            dto.setDescription(topic.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    public Topic findById(int id) {
        return topicRepository.findById(id).orElseThrow();
    }
}
