package com.kiyoung.toy.domain;


import com.kiyoung.toy.domain.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest
{
    @Autowired
    private PostsRepository postsRepository;

    @After
    public void cleanup()
    {
        postsRepository.deleteAll();
    }

    @Test
    public void retrieve_content()
    {
        //given
        String title = "";
        String content = "";

        //when
        postsRepository.save
                (
                        Posts.builder()
                                .title("dummy title")
                                .content("dummy content")
                                .author("yky2798@gmail.com")
                                .build()
                );
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo("dummy title");
        assertThat(posts.getContent()).isEqualTo("dummy content");
    }

    @Test
    public void baseTimeEntityRegister()
    {
        //given
        LocalDateTime now = LocalDateTime.of(2019,3,26,0,0,0);
        postsRepository.save(Posts.builder().author("a").title("t").content("c").build());

        //when
        List<Posts> all = postsRepository.findAll();
        Posts post = all.get(0);

        //then
        assertThat(post.getCreatedTime()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}
