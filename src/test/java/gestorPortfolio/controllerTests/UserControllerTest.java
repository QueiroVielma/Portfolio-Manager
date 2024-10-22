package gestorPortfolio.controllerTests;

import gestorPortfolio.controllers.UserController;
import gestorPortfolio.dto.user.UserRequest;
import gestorPortfolio.dto.user.UserResponse;
import gestorPortfolio.enums.Position;
import gestorPortfolio.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    private UserRequest userRequest;
    private UserResponse userResponse;
    private List<UserResponse> userList;

    @BeforeEach
    void setup() {
        userRequest = new UserRequest("John Doe", "12345678900", LocalDate.of(1992, 2, 2), Position.MANAGER);
        userResponse = new UserResponse(1L, "John Doe", "12345678900", LocalDate.of(1992, 2, 2), Position.MANAGER);

        userList = Arrays.asList(
                new UserResponse(1L, "John Doe", "12345678900", LocalDate.of(1992, 2, 2), Position.MEMBER),
                new UserResponse(2L, "Jane Smith", "12345678900", LocalDate.of(1992, 2, 2), Position.MEMBER)
        );
    }

    @Test
    void testSaveUserSuccess() {
        when(userService.save(any(UserRequest.class))).thenReturn(userResponse);

        ResponseEntity<String> response = userController.save(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created successfully.", response.getBody());
        verify(userService, times(1)).save(userRequest);
    }

    @Test
    void testSaveUserFailure() {
        doThrow(new RuntimeException("Validation error")).when(userService).save(userRequest);

        ResponseEntity<String> response = userController.save(userRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create user: Validation error", response.getBody());
        verify(userService, times(1)).save(userRequest);
    }

    @Test
    void testFindAllUsersSuccess() {
        when(userService.findAll()).thenReturn(userList);

        ResponseEntity<?> response = userController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, ((List<?>) response.getBody()).size());
        verify(userService, times(1)).findAll();
    }

    @Test
    void testFindAllUsersFailure() {
        when(userService.findAll()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = userController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to List users: Database error", response.getBody());
        verify(userService, times(1)).findAll();
    }

    @Test
    void testFindUserByIdSuccess() {
        when(userService.findById(1L)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getNome());
        verify(userService, times(1)).findById(1L);
    }

    @Test
    void testFindUserByIdFailure() {
        when(userService.findById(1L)).thenThrow(new RuntimeException("User not found"));

        ResponseEntity<UserResponse> response = userController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, times(1)).findById(1L);
    }

    @Test
    void testUpdateUserSuccess() {
        when(userService.update(userRequest, 1L)).thenReturn(userResponse);

        ResponseEntity<?> response = userController.update(userRequest, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
        verify(userService, times(1)).update(userRequest, 1L);
    }

    @Test
    void testUpdateUserFailure() {
        when(userService.update(userRequest, 1L)).thenThrow(new RuntimeException("Update error"));

        ResponseEntity<?> response = userController.update(userRequest, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to update user: Update error", response.getBody());
        verify(userService, times(1)).update(userRequest, 1L);
    }
}
