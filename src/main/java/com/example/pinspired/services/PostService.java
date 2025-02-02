package com.example.pinspired.services;


import com.example.pinspired.dtos.PostDto;
import com.example.pinspired.entities.PostEntity;

import java.util.List;

public interface PostService extends BaseService<PostDto, Long> {
    PostEntity create(PostDto postDto);
    List<PostDto> getPostsByIds(List<Long> postIds);
    List<PostDto> getPostsByUserId(Long userId);

}
