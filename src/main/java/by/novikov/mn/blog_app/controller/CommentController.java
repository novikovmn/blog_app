package by.novikov.mn.blog_app.controller;

import by.novikov.mn.blog_app.dto.CommentDto;
import by.novikov.mn.blog_app.dto.PostDto;
import by.novikov.mn.blog_app.entity.Comment;
import by.novikov.mn.blog_app.mapper.impl.CommentMapper;
import by.novikov.mn.blog_app.service.CommentService;
import by.novikov.mn.blog_app.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final CommentMapper commentMapper;

    // создать комментарий

    @PostMapping("/posts/{postId}/comments")
    public String createComment(@PathVariable("postId") Long postId,
                                @ModelAttribute("commentDto") @Valid CommentDto commentDto,
                                BindingResult bindingResult,
                                @RequestParam("userId") Long userId,
                                Model model) {
        if (bindingResult.hasErrors()) {
            // чтобы загрузить именно страницу view-post в случае ошибки валидации
            // нужно заново подгрузить публикацию postDto - т.к. на страничке view-post при
            // первой загрузке было две сущности postDto и commentDto
            PostDto postDto = postService.findPostById(postId);
            model.addAttribute("postDto", postDto);
            return "view-post";
        }
        commentService.createComment(postId, commentDto, userId);
        return "redirect:/posts/" + postId + "/view";
    }

    // посмотреть комменты поста (ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/posts/{postId}/comments")
    public String viewPostComments(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = postService.findPostById(postId);
        List<CommentDto> commentDtos = postDto.getCommentDtos();
        model.addAttribute("commentDtos", commentDtos);
        model.addAttribute("postTitle", postDto.getTitle());
        return "post-comments";
    }

    // форма для изменения коммента (ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/comments/{commentId}/edit")
    public String editCommentForm(@PathVariable("commentId") Long commentId, Model model) {
        CommentDto commentDto = commentService.findCommentById(commentId);
        PostDto postDto = postService.findPostById(commentDto.getPostId());
        model.addAttribute("commentDto", commentDto);
        model.addAttribute("postTitle", postDto.getTitle());
        model.addAttribute("postId", postDto.getId());
        return "edit-comment";
    }

    // изменить коммент (ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/comments/{commentId}")
    public String updateComment(@PathVariable("commentId") Long commentId,
                                @ModelAttribute("commentDto") @Valid CommentDto commentDto,
                                @RequestParam("postId") Long postId,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-comment";
        }
        commentService.updateComment(commentDto, commentId);
        return "redirect:/posts/" + postId + "/comments";
    }

    // удалить коммент (ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("commentId") Long commentId,
                                @RequestParam("postId") Long postId) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId + "/comments";
    }

}
