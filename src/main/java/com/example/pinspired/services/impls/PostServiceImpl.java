package com.example.pinspired.services.impls;

import com.example.pinspired.dtos.PostDto;
import com.example.pinspired.entities.PostEntity;
import com.example.pinspired.entities.UserEntity;
import com.example.pinspired.mappers.PostMapper;
import com.example.pinspired.repositories.PostRepository;
import com.example.pinspired.repositories.UserRepository;
import com.example.pinspired.services.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;


    @Override
    public List<PostDto> findAll() {
        var posts = postRepository.findAll();
        return postMapper.toDtos(posts);
        // return posts.stream().map(mapper::toDto).toList();
    }

    @Override
    public PostDto findById(Long id) {
        var optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new EntityNotFoundException("Post with id " + id + " not found");
        }

        return postMapper.toDto(optionalPost.get());


//        var post = repository.findById(id).orElse(null);
//
//        if (post == null) {
//            throw new EntityNotFoundException("Post with id " + id + " not found");
//        }
//
//        return mapper.toDto(post);
    }

    @Override
    public PostDto add(PostDto model) {
        return save(model);
    }

    @Override
    public PostDto modify(Long id, PostDto model) {

        if (id != model.getId()) {
            throw new IllegalArgumentException("Id does not match");
        }

        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post with id " + id + " not found");
        }
        // valido a eshte edhe i user-it te njejte (te loguar sipas session / token)

//        var postEntity = mapper.toEntity(model);
//        var savedPost = repository.save(postEntity);
//        return mapper.toDto(savedPost);
        return save(model);
    }

    private PostDto save(PostDto model) {
        var postEntity = postMapper.toEntity(model);
        var savedPost = postRepository.save(postEntity);
        return postMapper.toDto(savedPost);
    }

    @Override
    public void removeById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post with id " + id + " not found");
        }

        postRepository.deleteById(id);
    }

    @Override
    public PostEntity create(PostDto postDto) {
        // Ensure user exists
        if (postDto.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid userId");
        }

        UserEntity user = userRepository.findById(postDto.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("User with id " + postDto.getUserId() + " not found"));

        // Create new post entity
        PostEntity post = new PostEntity();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setImageUrl(postDto.getImageUrl());
        post.setVideoUrl(postDto.getVideoUrl());
        post.setTopic(postDto.getTopic());
        post.setUserEntity(user);

        // Save post to repository
        return postRepository.save(post);
    }

    @Override
    public List<PostDto> getPostsByIds(List<Long> postIds) {

        return postRepository.findAllById(postIds).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());

    };

    @Override
    public List<PostDto> getPostsByUserId(Long userId) {
        return postRepository.findByUserEntityId(userId).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

}
