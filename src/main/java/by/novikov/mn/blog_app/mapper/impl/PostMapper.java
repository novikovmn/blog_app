package by.novikov.mn.blog_app.mapper.impl;

import by.novikov.mn.blog_app.dto.PostDto;
import by.novikov.mn.blog_app.entity.Post;
import by.novikov.mn.blog_app.entity.User;
import by.novikov.mn.blog_app.mapper.Mapper;
import by.novikov.mn.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper implements Mapper<Post, PostDto> {

    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    @Override
    public PostDto mapToDto(Post entity) {
        return PostDto.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .description(entity.getDescription())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .commentDtos(
                            entity.getComments().stream()
                                    .map(commentMapper::mapToDto)
                                    .collect(Collectors.toList())
                    ).userId(entity.getUser().getId())
                    .authorUsername(entity.getUser().getUsername())
                    .build();
    }

    @Override
    public Post mapToEntity(PostDto dto) {
        return Post.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .comments(
                        Optional.ofNullable(dto.getCommentDtos())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(commentMapper::mapToEntity)
                                .collect(Collectors.toList())
                ).user(getUser(dto.getUserId()))
                .build();
    }

    private User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
