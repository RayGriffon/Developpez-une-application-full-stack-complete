package com.openclassrooms.mddapi.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private int id;
    private String content;
    private LocalDateTime createdAt;
    private int postId;
}
