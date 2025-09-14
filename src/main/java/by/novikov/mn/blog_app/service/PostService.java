package by.novikov.mn.blog_app.service;

import by.novikov.mn.blog_app.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> findAllPosts();
    void createPost(PostDto postDto, Long userId);
    PostDto findPostById(Long postId);
    void updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);
    List<PostDto> searchPosts(String query);

}
