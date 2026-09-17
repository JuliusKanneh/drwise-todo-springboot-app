package com.wisdomtechinc.drwisetodospringboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wisdomtechinc.drwisetodospringboot.exceptions.InvalidCredentialsException;
import com.wisdomtechinc.drwisetodospringboot.exceptions.UsernameAlreadyExistsException;
import com.wisdomtechinc.drwisetodospringboot.models.AppUser;
import com.wisdomtechinc.drwisetodospringboot.repositories.UserRepository;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock  private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    
    @InjectMocks 
    private UserService userService;

    @Test 
    @DisplayName("register() saves a new user with a hashed password when username is free")
    void register_saveNewUser_whenUsernameNotTaken() {
        String username = "alice";  
        String password = "plain-text";
        String passwordHash = "hashed-value";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(passwordHash);
        when(userRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser result = userService.register(username, password);

        assertEquals(username, result.getUsername());
        assertEquals(passwordHash, result.getPasswordHash());

        verify(userRepository).save(any(AppUser.class));
    }

    @Test 
    @DisplayName("register() throws and never saves when username is already taken")
    public void register_throwsUsernameAlreadyExistsException_whenUsernameTaken(){
        String username = "alice";
        String password = "plaintext";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new AppUser(username, "existing-hash")));

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.register(username, password));

        verify(userRepository, never()).save(any());

    } 

    @Test 
    @DisplayName("login() throws InvalidCredentialsException when username is not found")
    public void login_throwsInvalidCredentialsException_whenUsernameNotFound() {
        String username = "alice";
        String password = "plain-text";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> userService.login(username, password));

        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test 
    @DisplayName("login() throw InvalidCredentialsException when the entered password not match the user password")
    public void login_throwsInvalidCredentialsException_whenPasswordNotMatch() {
        String username = "alice";
        String password = "plaintext";
        String passwordHash = "existing-hash";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new AppUser(username, passwordHash)));
        when(passwordEncoder.matches(password, passwordHash)).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> userService.login(username, password));
    }

    @Test 
    @DisplayName("login() login a user and return a login jwt token when the username is not taken and the password matches the user password")
    public void login_whenUsernameIsFound_andPasswordMatch() {
        String username = "alice";
        String password = "plaintext";
        String passwordHash = "hashed-value";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new AppUser(username, passwordHash)));
        when(passwordEncoder.matches(password, passwordHash)).thenReturn(true);

        when(jwtService.generateToken(username)).thenReturn("header.payload.signature");

        String token = userService.login(username, password);

        assertEquals(3, token.split("\\.").length);
    }
}
