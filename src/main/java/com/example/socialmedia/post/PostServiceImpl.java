package com.example.socialmedia.post;

import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.post.dto.NewPost;
import com.example.socialmedia.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final UserRepository userRepository;

    @Override
    public Post addPost(NewPost newPost) {
        Post post = new Post();
        post.setTitle(newPost.getTitle());
        post.setText(newPost.getText());
        post.setUrl(newPost.getUrl());
        post.setUser(userRepository.findById(newPost.getAuthorId())
                .orElseThrow(() -> new NotFoundException("User not found")));
        return repository.save(post);
    }

    @Override
    public Post getPostById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));
    }

    @Override
    public Post updatePost(long id, Post post) {
        Post oldPost = getPostById(id);
        if (post.getText() != null && !post.getText().isBlank()) {
            oldPost.setText(post.getText());
        }
        if (post.getTitle() != null && !post.getTitle().isBlank()) {
            oldPost.setTitle(post.getTitle());
        }
        if (post.getUrl() != null && !post.getUrl().isBlank()) {
            oldPost.setUrl(post.getUrl());
        }
        return repository.save(oldPost);
    }

    @Override
    public List<Post> getByAuthorId(long id, Integer from, Integer size) {
        return repository.findByUserId(id, PageRequest.of(from / size, size));
    }

    @Override
    public void deletePostById(long id) {
        repository.deleteById(id);
    }

}
