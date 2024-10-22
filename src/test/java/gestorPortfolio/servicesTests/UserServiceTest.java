package gestorPortfolio.servicesTests;

import gestorPortfolio.dto.user.UserRequest;
import gestorPortfolio.dto.user.UserResponse;
import gestorPortfolio.entities.User;
import gestorPortfolio.enums.Position;
import gestorPortfolio.repositoies.UserRespository;
import gestorPortfolio.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRespository userRespository;

    private User user;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setCpf("12345678901");
        user.setDateOfBirth(LocalDate.of(1990, 1, 1));
        user.setPosition(Position.MEMBER.getValue());

        userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setCpf("12345678901");
        userRequest.setDateBirth(LocalDate.of(1990, 1, 1));
        userRequest.setPositionUser(Position.MEMBER);
    }

    @Test
    void testSaveUserSuccess() {
        when(userRespository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.save(userRequest);

        assertNotNull(response);
        assertEquals("John Doe", response.getNome());
        assertEquals("12345678901", response.getCpf());
    }

    void testUpdateUserSuccess() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Jane Doe");
        existingUser.setCpf("123456789");
        existingUser.setDateOfBirth(LocalDate.of(1990, 1, 1));
        existingUser.setPosition(2);

        when(userRespository.findById(1L)).thenReturn(Optional.of(existingUser));

        UserRequest userRequest = new UserRequest();
        userRequest.setName("Jane Smith");
        userRequest.setCpf("987654321");
        userRequest.setDateBirth(LocalDate.of(1992, 2, 2));
        userRequest.setPositionUser(Position.MANAGER);

        UserResponse updatedUserResponse = userService.update(userRequest, 1L);

        assertEquals("Jane Smith", updatedUserResponse.getNome());
        assertEquals("987654321", updatedUserResponse.getCpf());
    }

    @Test
    void testFindByIdSuccess() {
        when(userRespository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getNome());
    }

    @Test
    void testFindByIdNotFound() {
        when(userRespository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.findById(1L));

        assertEquals("Invalid user ID: 1", exception.getMessage());
    }

    @Test
    void testFindAllUsers() {
        when(userRespository.findAll()).thenReturn(List.of(user));

        List<UserResponse> users = userService.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getNome());
    }
}
