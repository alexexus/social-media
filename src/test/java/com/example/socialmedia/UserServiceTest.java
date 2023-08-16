package com.example.socialmedia;

import com.example.socialmedia.role.Role;
import com.example.socialmedia.role.RoleRepository;
import com.example.socialmedia.user.User;
import com.example.socialmedia.user.UserRepository;
import com.example.socialmedia.user.UserService;
import com.example.socialmedia.user.dto.NewUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;

    @Test
    void createUser() {
        User userToSave = User.builder()
                .id(0L)
                .name("name")
                .email("email@mail.ru")
                .password("100")
                .roles(List.of(new Role()))
                .friends(new HashSet<>())
                .build();
        NewUserDto newUserDto = new NewUserDto("name", "email@mail.ru", "100", "100");
        when(repository.save(any(User.class))).thenReturn(userToSave);
        when(passwordEncoder.encode(anyString())).thenReturn("100");
        when(roleRepository.findByName(anyString())).thenReturn(new Role());

        User actual = service.createUser(newUserDto);

        assertThat(actual).usingRecursiveComparison().isEqualTo(userToSave);
        verify(repository, times(1)).save(userToSave);
        verify(repository, times(1)).findByName("name");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getUserById() {
        User expectedUser = User.builder()
                .id(1L)
                .name("name")
                .email("email@mail.ru")
                .password("100")
                .roles(List.of(new Role()))
                .friends(new HashSet<>())
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(expectedUser));

        User actual = service.findById(1L);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedUser);
        verify(repository, times(1)).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getUserByName() {
        User expectedUser = User.builder()
                .id(1L)
                .name("name")
                .email("email@mail.ru")
                .password("100")
                .roles(List.of(new Role()))
                .friends(new HashSet<>())
                .build();
        when(repository.findByName(anyString())).thenReturn(Optional.of(expectedUser));

        User actual = service.findByName("name").get();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedUser);
        verify(repository, times(1)).findByName("name");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void addFriend() {
        User user1 = User.builder()
                .id(1L)
                .name("name")
                .email("email@mail.ru")
                .password("100")
                .roles(List.of(new Role()))
                .friends(new HashSet<>())
                .build();
        User user2 = User.builder()
                .id(2L)
                .name("name")
                .email("email@mail.ru")
                .password("100")
                .roles(List.of(new Role()))
                .friends(new HashSet<>())
                .build();
        when(repository.findById(1L)).thenReturn(Optional.of(user1));
        when(repository.findById(2L)).thenReturn(Optional.of(user2));

        service.addFriend(1L, 2L);

        assertThat(user1.getFriends().size()).usingRecursiveComparison().isEqualTo(1);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).findById(2L);
        verify(repository, times(1)).save(user1);
        verifyNoMoreInteractions(repository);
    }
}
