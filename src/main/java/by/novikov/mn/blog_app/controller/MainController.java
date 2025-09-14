package by.novikov.mn.blog_app.controller;

import by.novikov.mn.blog_app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;

    @GetMapping("/")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.findAllPosts());
        return "view-all-posts";
    }
}
