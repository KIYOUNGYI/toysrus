package com.kiyoung.toy.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.http.MediaType;
import com.kiyoung.toy.domain.Posts;
import com.kiyoung.toy.domain.PostsRepository;
import com.kiyoung.toy.web.dto.PostsSaveRequestDto;
import com.kiyoung.toy.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @After
    public void tearDown()
    {
        postsRepository.deleteAll();
    }

    @Before
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

//    @Test
//    @WithMockUser(roles="USER")
//    public void posts_register() throws Exception {
//        //given
//        String title = "dummy title";
//        String author = "yky2798@gmail.com";
//        String content = "dummy content";
//        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
//                .author(author)
//                .content(content)
//                .title(title).build();
//        System.out.println(">>>> port : " + port);
//        String url = "http://localhost:" + port + "/api/v1/posts";
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, postsSaveRequestDto, Long.class);
//        System.out.println("responseEntity : " + responseEntity);
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        System.out.println("body length:" + responseEntity.getBody());
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        List<Posts> dummyList = postsRepository.findAll();
//        assertThat(dummyList.get(0).getContent()).isEqualTo(content);
//        assertThat(dummyList.get(0).getAuthor()).isEqualTo(author);
//        assertThat(dummyList.get(0).getTitle()).isEqualTo(title);
//    }
//
//    @Test
//    @WithMockUser(roles="USER")
//    public void posts_update() throws Exception
//    {
//        //given
//        Posts dummy = Posts.builder().author("author").content("content").title("title").build();
//        postsRepository.save(dummy);
//
//        Long id = dummy.getId();
//        String content = "fixed content";
//        String title = "fixed title";
//        String author = "fixed author";
//
//        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().
//                content(content).
//                title(title).
//                build();
//
//        String url = "http://localhost:"+port+"/api/v1/posts/"+id;
//        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(title);
//        assertThat(all.get(0).getContent()).isEqualTo(content);
//    }
@Test
@WithMockUser(roles="USER")
public void Posts_등록된다() throws Exception {
    //given
    String title = "title";
    String content = "content";
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
            .title(title)
            .content(content)
            .author("author")
            .build();

    String url = "http://localhost:" + port + "/api/v1/posts";

    //when
    mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
            .andExpect(status().isOk());

    //then
    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(title);
    assertThat(all.get(0).getContent()).isEqualTo(content);
}

    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정된다() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
