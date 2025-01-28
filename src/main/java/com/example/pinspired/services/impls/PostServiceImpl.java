package com.example.pinspired.services.impls;

import com.example.pinspired.dtos.PostDto;
import com.example.pinspired.mappers.PostMapper;
import com.example.pinspired.repositories.PostRepository;
import com.example.pinspired.services.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostMapper mapper;


    @Override
    public List<PostDto> findAll() {
        var posts = repository.findAll();
        return mapper.toDtos(posts);
        // return posts.stream().map(mapper::toDto).toList();
    }

    @Override
    public PostDto findById(Long id) {
        var optionalPost = repository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new EntityNotFoundException("Post with id " + id + " not found");
        }

        return mapper.toDto(optionalPost.get());


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

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Post with id " + id + " not found");
        }
        // valido a eshte edhe i user-it te njejte (te loguar sipas session / token)

//        var postEntity = mapper.toEntity(model);
//        var savedPost = repository.save(postEntity);
//        return mapper.toDto(savedPost);
        return save(model);
    }

    private PostDto save(PostDto model) {
        var postEntity = mapper.toEntity(model);
        var savedPost = repository.save(postEntity);
        return mapper.toDto(savedPost);
    }

    @Override
    public void removeById(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Post with id " + id + " not found");
        }

        repository.deleteById(id);
    }
}
