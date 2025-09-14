package by.novikov.mn.blog_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PostDto {
    private Long id;
    @NotBlank(message = "Заголовок не должен быть пустым")
    private String title;
    @NotBlank(message = "Содержимое не должно быть пустым")
    private String content;
    @NotBlank(message = "Описание не должно быть пустым")
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDto> commentDtos;
    private Long userId;
    private String authorUsername;    // автор
}
