package by.novikov.mn.blog_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotBlank(message = "Content should not be empty")
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long postId;    // Только ID поста вместо полного объекта
    private Long userId;
    private String authorUsername;
}
