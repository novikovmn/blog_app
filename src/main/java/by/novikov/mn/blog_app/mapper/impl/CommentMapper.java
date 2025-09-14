package by.novikov.mn.blog_app.mapper.impl;

import by.novikov.mn.blog_app.dto.CommentDto;
import by.novikov.mn.blog_app.entity.Comment;
import by.novikov.mn.blog_app.entity.Post;
import by.novikov.mn.blog_app.entity.User;
import by.novikov.mn.blog_app.mapper.Mapper;
import by.novikov.mn.blog_app.repository.PostRepository;
import by.novikov.mn.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentMapper implements Mapper<Comment, CommentDto> {

    private final PostRepository postRepository;    // вместо PostMapper
    private final UserRepository userRepository;

   @Override
    public CommentDto mapToDto(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .postId(entity.getPost().getId())
                .userId(entity.getUser().getId())
                .authorUsername(entity.getUser().getUsername())
                .build();
    }

    @Override
    public Comment mapToEntity(CommentDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .post(getPost(dto.getPostId()))
                .user(getUser(dto.getUserId()))
                .build();
    }

    // данный метод помагает избежать циклической зависимости м-ду PostMapper и CommentMapper
    // по id находим Post (т.к. CommentDto содержит именно id поста)
    private Post getPost(Long postId){
        return Optional.ofNullable(postId)
                .flatMap(postRepository::findById)
                .orElse(null);
    }

    private User getUser(Long userId){
       return Optional.ofNullable(userId)
               .flatMap(userRepository::findById)
               .orElse(null);
    }

}
