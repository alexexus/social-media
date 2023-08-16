package com.example.socialmedia.post;

import com.example.socialmedia.post.dto.NewPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody @Valid NewPost newPost) {
        return service.addPost(newPost);
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable long postId) {
        return service.getPostById(postId);
    }

    @PatchMapping("/{postId}")
    public Post updatePost(@PathVariable long postId,
                           @RequestBody Post post) {
        return service.updatePost(postId, post);
    }

    @GetMapping("/users/{userId}")
    public List<Post> getPostsByAuthorId(@PathVariable long userId,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size) {
        return service.getByAuthorId(userId, from, size);
    }

    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable long postId) {
        service.deletePostById(postId);
    }
}
