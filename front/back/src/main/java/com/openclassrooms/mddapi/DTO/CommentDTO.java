package com.openclassrooms.mddapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private int id;
    private String content;
    private LocalDateTime createdAt;
    private int postId;
}
