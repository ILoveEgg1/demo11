package com.example.demo1.service;

import com.example.demo1.dto.request.UserCreationRequest;
import com.example.demo1.entity.User;
import com.example.demo1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        UserCreationRequest request = new UserCreationRequest();
        request.setUsername("john");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("123456");
        request.setDob(LocalDate.of(1990, 1, 1));

        User user = new User();
        user.setId("123");
        user.setUsername("john");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDob(LocalDate.of(1990, 1, 1));

        when(userRepository.save(any(User.class))).thenReturn(user);

        // WHEN
        User response = userService.createUser(request);

        // THEN
        assertThat(response.getId()).isEqualTo("123");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertThat(capturedUser.getUsername()).isEqualTo("john");
        assertThat(capturedUser.getFirstName()).isEqualTo("John");
        assertThat(capturedUser.getLastName()).isEqualTo("Doe");
        assertThat(capturedUser.getPassword()).isEqualTo("123456");
        assertThat(capturedUser.getDob()).isEqualTo(LocalDate.of(1990, 1, 1));
    }
}
