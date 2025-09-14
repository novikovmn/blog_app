package by.novikov.mn.blog_app.service.impl;

import by.novikov.mn.blog_app.dto.PostDto;
import by.novikov.mn.blog_app.entity.Post;
import by.novikov.mn.blog_app.mapper.impl.PostMapper;
import by.novikov.mn.blog_app.repository.PostRepository;
import by.novikov.mn.blog_app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public List<PostDto> findAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createPost(PostDto postDto, Long userId) {
        postDto.setUserId(userId);
        postRepository.save(postMapper.mapToEntity(postDto));
    }

    @Override
    public PostDto findPostById(Long postId) {
       return postRepository.findById(postId)
               .map(postMapper::mapToDto)
               .orElse(null);
    }

    @Transactional
    @Override
    public void updatePost(PostDto postDto, Long postId) {
        Post toUpdate = postRepository.findById(postId).get();
        toUpdate.setTitle(postDto.getTitle());
        toUpdate.setContent(postDto.getContent());
        toUpdate.setDescription(postDto.getDescription());
        postRepository.save(toUpdate);
    }

    @Transactional
    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }



    @Override
    public List<PostDto> searchPosts(String query) {
       return postRepository.searchPosts(query).stream()
                    .map(postMapper::mapToDto)
                     .collect(Collectors.toList());
    }
}
