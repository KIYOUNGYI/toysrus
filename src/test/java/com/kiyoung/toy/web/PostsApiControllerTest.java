package com.kiyoung.toy.web;

import com.kiyoung.toy.domain.Posts;
import com.kiyoung.toy.domain.PostsRepository;
import com.kiyoung.toy.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest
{
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown()
    {
        postsRepository.deleteAll();
    }

    @Test
    public void posts_register() throws Exception
    {
        //given
        String title = "dummy title";
        String author ="yky2798@gmail.com";
        String content = "dummy content";
        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .author(author)
                .content(content)
                .title(title).build();
        System.out.println(">>>> port : "+ port);
        String url = "http://localhost:"+port+"/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,postsSaveRequestDto,Long.class);
        System.out.println("responseEntity : "+responseEntity);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("body length:"+responseEntity.getBody());
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> dummyList = postsRepository.findAll();
        assertThat(dummyList.get(0).getContent()).isEqualTo(content);
        assertThat(dummyList.get(0).getAuthor()).isEqualTo(author);
        assertThat(dummyList.get(0).getTitle()).isEqualTo(title);

    }
}