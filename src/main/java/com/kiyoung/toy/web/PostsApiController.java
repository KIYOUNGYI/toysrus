package com.kiyoung.toy.web;

import com.kiyoung.toy.service.posts.PostsService;
import com.kiyoung.toy.web.dto.PostsResponseDto;
import com.kiyoung.toy.web.dto.PostsSaveRequestDto;
import com.kiyoung.toy.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto)
    {
        Long result = postsService.update(id,requestDto);
        return result;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id)
    {
        PostsResponseDto result = postsService.findById(id);
        return result;
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id)
    {
        postsService.delete(id);
        return id;
    }
}
