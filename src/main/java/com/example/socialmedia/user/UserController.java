package com.example.socialmedia.user;

import com.example.socialmedia.exception.ValidationException;
import com.example.socialmedia.user.dto.NewUserDto;
import com.example.socialmedia.user.dto.UserDto;
import com.example.socialmedia.utils.JwtRequest;
import com.example.socialmedia.utils.JwtResponse;
import com.example.socialmedia.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),
                    jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ValidationException("Wrong login or password");
        }
        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody NewUserDto newUserDto) {
        User user = userService.createUser(newUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getName(), user.getEmail()));
    }

    @PostMapping("/friends/{userId}/{friendId}")
    public void addFriend(@PathVariable long userId,
                          @PathVariable long friendId) {
        userService.addFriend(userId, friendId);
    }
}
