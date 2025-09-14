package by.novikov.mn.blog_app.service.impl;

import by.novikov.mn.blog_app.dto.CommentDto;
import by.novikov.mn.blog_app.entity.Comment;
import by.novikov.mn.blog_app.entity.Post;
import by.novikov.mn.blog_app.entity.User;
import by.novikov.mn.blog_app.mapper.impl.CommentMapper;
import by.novikov.mn.blog_app.mapper.impl.PostMapper;
import by.novikov.mn.blog_app.repository.CommentRepository;
import by.novikov.mn.blog_app.repository.PostRepository;
import by.novikov.mn.blog_app.repository.UserRepository;
import by.novikov.mn.blog_app.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void createComment(Long postId, CommentDto commentDto, Long userId) {
        Comment comment = Optional.ofNullable(commentDto)
                .map(commentMapper::mapToEntity)
                .orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        User user = userRepository.findById(userId).orElse(null);

        // связываем коммент с постом
        comment.setPost(post);
        // связываем коммент с юзером
        comment.setUser(user);
        commentRepository.save(comment);
    }

    @Override
    public CommentDto findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(commentMapper::mapToDto)
                .orElse(null);
    }

    @Transactional
    @Override
    public void updateComment(CommentDto commentDto, Long commentId) {
        Comment toUpdate = commentRepository.findById(commentId).get();
        toUpdate.setContent(commentDto.getContent());
        commentRepository.save(toUpdate);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
