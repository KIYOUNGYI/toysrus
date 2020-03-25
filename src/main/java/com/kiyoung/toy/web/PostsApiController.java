package com.kiyoung.toy.web;

import com.kiyoung.toy.service.posts.PostsService;
import com.kiyoung.toy.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController
{
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto)
    {
        Long result = postsService.save(requestDto);
        System.out.println("controller /api/v1/posts result:"+result);
        return result;
    }
}
