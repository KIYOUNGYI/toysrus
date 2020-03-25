package com.kiyoung.toy.domain.posts;


import com.kiyoung.toy.domain.Posts;
import com.kiyoung.toy.domain.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
