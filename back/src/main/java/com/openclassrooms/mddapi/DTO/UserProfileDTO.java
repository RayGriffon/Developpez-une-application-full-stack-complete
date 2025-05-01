package com.openclassrooms.mddapi.DTO;

import com.openclassrooms.mddapi.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private int id;
    private String email;
    private String username;

    public UserProfileDTO(String email, String username, int id, List<Topic> subscribedTopics) {
        this.email = email;
        this.username = username;
        this.id = id;
    }
}
