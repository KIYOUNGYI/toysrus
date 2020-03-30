package com.kiyoung.toy.service.posts;

import com.kiyoung.toy.domain.Posts;
import com.kiyoung.toy.domain.PostsRepository;
import com.kiyoung.toy.web.dto.PostsListResponseDto;
import com.kiyoung.toy.web.dto.PostsResponseDto;
import com.kiyoung.toy.web.dto.PostsSaveRequestDto;
import com.kiyoung.toy.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto)
    {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto)
    {
        Posts posts = postsRepository.findById(id).
                orElseThrow(
                        ()->new IllegalArgumentException("해당 사용자가 없습니다. id->"+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id)
    {
        Posts entity = postsRepository.findById(id).
                orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다. id->"+id));
        PostsResponseDto result = new PostsResponseDto(entity);
        return result;
    }

    @org.springframework.transaction.annotation.Transactional(readOnly=true)
    public List<PostsListResponseDto> findAllDesc()
    {
        return postsRepository.findAllDesc()
                .stream().map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void delete(Long id)
    {
        Posts posts = postsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 게시글이 없습니다. id->"+id));
        postsRepository.delete(posts);
    }

}
