package com.kiyoung.toy.web;

import com.kiyoung.toy.config.auth.LoginUser;
import com.kiyoung.toy.service.posts.PostsService;
import com.kiyoung.toy.web.dto.PostsResponseDto;
import com.kiyoung.toy.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController
{
    private final PostsService postsService;

//    @GetMapping("/")
//    public String index(Model model)
//    {
//        model.addAttribute("posts", postsService.findAllDesc());
//        return "index";
//    }

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("posts/save")
    public String postSave(){return "posts-save";}

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        System.out.println("id=>"+id);
        PostsResponseDto dto = postsService.findById(id);
        System.out.println("dto=>"+dto.toString());
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
