package com.example.socialmedia.post;

import com.example.socialmedia.post.dto.NewPost;

import java.util.List;

public interface PostService {

    Post addPost(NewPost newPost);

    Post getPostById(long id);

    Post updatePost(long id, Post post);

    List<Post> getByAuthorId(long id, Integer from, Integer size);

    void deletePostById(long id);
}
