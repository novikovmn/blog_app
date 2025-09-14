package by.novikov.mn.blog_app.service;

import by.novikov.mn.blog_app.dto.CommentDto;

public interface CommentService {

    void createComment(Long postId, CommentDto commentDto, Long userId);
    CommentDto findCommentById(Long commentId);
    void updateComment(CommentDto commentDto, Long commentId);
    void deleteComment(Long commentId);
}
