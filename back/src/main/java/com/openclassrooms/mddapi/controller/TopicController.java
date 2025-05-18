package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.TopicDTO;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Contrôleur pour la gestion des sujets (topics) et des abonnements.
 */
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

  /**
   * Récupère tous les sujets disponibles.
   *
   * @return la liste des sujets
   */
    @GetMapping
    public List<TopicDTO> getAllTopics() {
        return topicService.getAllTopics();
    }

  /**
   * Abonne l'utilisateur à un sujet donné.
   *
   * @param topicId l'identifiant du sujet
   * @param principal l'utilisateur authentifié
   */
    @PostMapping("/subscribe/{topicId}")
    public void subscribe(@PathVariable int topicId, Principal principal) {
        topicService.subscribeUserToTopic(principal.getName(), topicId);
    }

  /**
   * Désabonne l'utilisateur d’un sujet.
   *
   * @param topicId l'identifiant du sujet
   * @param principal l'utilisateur authentifié
   */
    @PostMapping("/unsubscribe/{topicId}")
    public void unsubscribe(@PathVariable int topicId, Principal principal) {
        topicService.unsubscribeUserFromTopic(principal.getName(), topicId);
    }

  /**
   * Récupère la liste des sujets auxquels l’utilisateur est abonné.
   *
   * @param principal l'utilisateur authentifié
   * @return les sujets suivis par l’utilisateur
   */
    @GetMapping("/subscribed")
    public List<TopicDTO> getSubscribedTopics(Principal principal) {
        return topicService.getSubscribedTopics(principal.getName());
    }
}
