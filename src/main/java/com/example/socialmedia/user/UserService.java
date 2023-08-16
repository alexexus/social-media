package com.example.socialmedia.user;

import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.exception.ValidationException;
import com.example.socialmedia.role.RoleRepository;
import com.example.socialmedia.user.dto.NewUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void addFriend(long userId, long friendId) {
        User user = findById(userId);
        User friend = findById(friendId);
        user.getFriends().add(friend);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name).orElseThrow(() -> new NotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    public User createUser(NewUserDto newUserDto) {
        if (!newUserDto.getPassword().equals(newUserDto.getConfirmedPassword())) {
            throw new ValidationException("Password mismatch");
        }
        if (findByName(newUserDto.getName()).isPresent()) {
            throw new ValidationException("User with this name already exists");
        }
        User user = new User();
        user.setEmail(newUserDto.getEmail());
        user.setName(newUserDto.getName());
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }
}
