package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.TopicDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsable de la gestion des sujets (topics).
 * Permet l'abonnement/désabonnement à des sujets et la récupération de listes.
 */
@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserService userService;

  /**
   * Récupère la liste de tous les sujets disponibles.
   *
   * @return Une liste de TopicDTO.
   */
    public List<TopicDTO> getAllTopics() {
        return topicRepository.findAll().stream().map(topic -> {
            TopicDTO dto = new TopicDTO();
            dto.setId(topic.getId());
            dto.setName(topic.getName());
            dto.setDescription(topic.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

  /**
   * Abonne un utilisateur à un sujet donné.
   *
   * @param email   L'email de l'utilisateur.
   * @param topicId L'identifiant du sujet.
   */
    public void subscribeUserToTopic(String email, int topicId) {
        User user = userService.findByEmail(email);
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        user.getSubscribedTopics().add(topic);
        userService.save(user);
    }

  /**
   * Désabonne un utilisateur d'un sujet.
   *
   * @param email   L'email de l'utilisateur.
   * @param topicId L'identifiant du sujet.
   */
    public void unsubscribeUserFromTopic(String email, int topicId) {
        User user = userService.findByEmail(email);
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        user.getSubscribedTopics().remove(topic);
        userService.save(user);
    }

  /**
   * Récupère la liste des sujets auxquels un utilisateur est abonné.
   *
   * @param email L'email de l'utilisateur.
   * @return Une liste de TopicDTO correspondant aux abonnements de l'utilisateur.
   */
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

  /**
   * Récupère un sujet par son identifiant.
   *
   * @param id L'identifiant du sujet.
   * @return L'entité Topic correspondante.
   */
    public Topic findById(int id) {
        return topicRepository.findById(id).orElseThrow();
    }
}
