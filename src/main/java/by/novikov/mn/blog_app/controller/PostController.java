package by.novikov.mn.blog_app.controller;

import by.novikov.mn.blog_app.dto.CommentDto;
import by.novikov.mn.blog_app.dto.PostDto;
import by.novikov.mn.blog_app.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // SELECT ALL POSTS (ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postService.findAllPosts());
        return "admin-posts";
    }

    // return create post form
    @GetMapping("/posts/new")
    public String createPostForm(@ModelAttribute("postDto") PostDto postDto) {
        return "create-post";
    }

    // CREATE
    @PostMapping("/posts")
    public String createPost(@ModelAttribute("postDto") @Valid PostDto postDto,
                                                        BindingResult bindingResult,
                                                        @RequestParam("userId") Long userId) {
//        ЕСЛИ ЕСТЬ ОШИБКИ
        if(bindingResult.hasErrors()) {
//          перейдем на ту же html-страницу с формой (с учетом ощибок)
            return "create-post";
        }
//        ЕСЛИ НЕТ ОШИБОК
        postService.createPost(postDto, userId);
        return "redirect:/";
    }

    // return edit post form
    @GetMapping("/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = postService.findPostById(postId);
        if(Objects.isNull(postDto)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("postDto", postDto);
        return "edit-post";
    }

    // UPDATE
    @PostMapping("/posts/{postId}")
    public String updatePost(@ModelAttribute("postDto") @Valid PostDto postDto,
                             @PathVariable("postId") Long postId,
                             BindingResult bindingResult,
                             Model model){
        // валидация
        if(bindingResult.hasErrors()){
            return "edit-post";
        }
        postService.updatePost(postDto, postId);
        return "redirect:/posts/" + postId + "/view";
    }

    // DELETE
    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId, HttpSession session) {
        postService.deletePost(postId);
        String currentUserRole = (String)session.getAttribute("CURRENT_USER_ROLE");
        return currentUserRole.equals("ADMIN")
                ? "redirect:/admin/posts"
                : "redirect:/";
    }

    // return the post page
    @GetMapping("/posts/{postId}/view")
    public String viewPost(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = postService.findPostById(postId);
        CommentDto commentDto = new CommentDto();
        if(Objects.isNull(postDto)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("postDto", postDto);
        model.addAttribute("commentDto", commentDto);
        return "view-post";
    }

    // search posts (ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/posts/search")
    public String searchPostsByAdmin(@RequestParam("query") String query, Model model) {
        List<PostDto> posts = postService.searchPosts(query);
        // перезатираем то, что было в "select all" (см. выше)
        // НА НАЙДЕННЫЕ ПОСТЫ
        // НАЗВАЕНИЕ АТРИБУТА НЕ МЕНЯЕМ = "posts"
        model.addAttribute("posts", posts);
        return "admin-posts";
    }

    // search posts (for all)
    @GetMapping("/posts/search")
    public String searchPostsForAll(@RequestParam("query") String query, Model model) {
        List<PostDto> posts = postService.searchPosts(query);
        // НАЗВАЕНИЕ АТРИБУТА НЕ МЕНЯЕМ = "posts"
        model.addAttribute("posts", posts);
        return "view-all-posts";
    }
}
