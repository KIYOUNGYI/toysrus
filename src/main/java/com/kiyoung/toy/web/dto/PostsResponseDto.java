package com.kiyoung.toy.web.dto;

import com.kiyoung.toy.domain.Posts;

public class PostsResponseDto
{
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity)
    {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
