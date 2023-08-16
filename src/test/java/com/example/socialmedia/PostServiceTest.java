package com.example.socialmedia;

import com.example.socialmedia.post.Post;
import com.example.socialmedia.post.PostRepository;
import com.example.socialmedia.post.PostServiceImpl;
import com.example.socialmedia.post.dto.NewPost;
import com.example.socialmedia.user.User;
import com.example.socialmedia.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostServiceImpl service;
    @Mock
    private PostRepository repository;
    @Mock
    private UserRepository userRepository;

    @Test
    void addPost() {
        Post postToSave = Post.builder()
                .id(0L)
                .text("text")
                .title("title")
                .url("url")
                .user(User.builder().build())
                .build();
        NewPost newPost = new NewPost("text", "title", "url", 0);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        when(repository.save(any(Post.class))).thenReturn(postToSave);

        Post actual = service.addPost(newPost);

        assertThat(actual).usingRecursiveComparison().isEqualTo(postToSave);
        verify(repository, times(1)).save(postToSave);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getPostById() {
        Post expectedItem = Post.builder()
                .id(1L)
                .text("text")
                .title("title")
                .url("url")
                .user(User.builder().build())
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(expectedItem));

        Post actual = service.getPostById(1L);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedItem);
        verify(repository, times(1)).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void updatePost() {
        Post postToUpdate = Post.builder()
                .id(1L)
                .text("text")
                .title("title")
                .url("url")
                .user(User.builder().build())
                .build();
        Post post = Post.builder()
                .id(1L)
                .text("text_update")
                .title("title")
                .url("url")
                .user(User.builder().build())
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(postToUpdate));
        when(repository.save(any(Post.class))).thenReturn(post);

        Post actual = service.updatePost(1L, post);

        assertThat(actual).usingRecursiveComparison().isEqualTo(post);
        verify(repository, times(1)).save(post);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deletePost() {
        doNothing().when(repository).deleteById(anyLong());

        service.deletePostById(1L);

        verify(repository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(repository);
    }
}
