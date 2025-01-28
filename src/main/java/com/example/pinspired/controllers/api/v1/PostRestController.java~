package dev.naimsulejmani.gr1facebook.controllers.api.v1;

import dev.naimsulejmani.gr1facebook.dtos.PostDto;
import dev.naimsulejmani.gr1facebook.services.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// ish dsath -> /api/v1/users/{userId}/posts
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostRestController {
    private final PostService service;


    @GetMapping
    public List<PostDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PostDto findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto add(@Valid @RequestBody PostDto model) {
        return service.add(model);
    }

    @PutMapping("/{id}")
    public PostDto modify(@PathVariable long id, @Valid @RequestBody PostDto model) {
        return service.modify(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@Valid @Positive @PathVariable long id) {
        service.removeById(id);
    }

    @GetMapping("/default")
    public PostDto defaultPost() {
        return new PostDto();
    }

}













