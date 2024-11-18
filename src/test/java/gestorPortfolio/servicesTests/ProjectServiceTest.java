package gestorPortfolio.servicesTests;

import gestorPortfolio.dto.project.ProjectProgress;
import gestorPortfolio.dto.project.ProjectRequest;
import gestorPortfolio.dto.project.ProjectResponse;
import gestorPortfolio.entities.Project;
import gestorPortfolio.entities.User;
import gestorPortfolio.enums.Risk;
import gestorPortfolio.enums.Status;
import gestorPortfolio.repositoies.ProjectRepository;
import gestorPortfolio.repositoies.UserRespository;
import gestorPortfolio.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTest {
    @MockBean
    ProjectRepository projectRepository;
    @MockBean
    UserRespository userRespository;
    @Autowired
    ProjectService projectService;

    private Project project;
    private User user;

    @BeforeEach
    void setup(){

        user = new User();
        user.setId(1L);
        user.setName("Manager");
        user.setPosition(2);

        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setStatus(Status.UNDER_REVIEW.getValue());
        project.setMembers(new ArrayList<>());
        project.setRisk(1);
        project.setResponsibleManager(user);
    }

    @Test
    void testSaveProjectSuccess() {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setNameProject("New Website Development");
        projectRequest.setExpectedEndDate(LocalDate.of(2024, 12, 31));
        projectRequest.setManagerId(1L);
        projectRequest.setBudget(50000.00);
        projectRequest.setDescription("Develop a responsive website for the company's portfolio.");
        projectRequest.setRiskProject(Risk.LOW);

        when(userRespository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectResponse response = projectService.save(projectRequest);

        assertNotNull(response);
        assertEquals("Test Project", response.getName());
        assertEquals(Status.UNDER_REVIEW, response.getStatus());
    }

    @Test
    void testSaveProjectInvalidManager() {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setNameProject("New Website Development");
        projectRequest.setExpectedEndDate(LocalDate.of(2024, 12, 31));
        projectRequest.setManagerId(2L);
        projectRequest.setBudget(50000.00);
        projectRequest.setDescription("Develop a responsive website for the company's portfolio.");
        projectRequest.setRiskProject(Risk.LOW);
        when(userRespository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        when(userRespository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> projectService.save(projectRequest));

        assertEquals("Invalid user ID: 2", exception.getMessage());
    }

    @Test
    void testFindByIdSuccess() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project result = projectService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> projectService.findById(1L));

        assertEquals("Invalid Project ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteProjectSuccess() {
        project.setStatus(3);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        String result = projectService.delete(1L);

        assertEquals("User deleted successfully", result);
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProjectFailure() {
        project.setStatus(5);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        String result = projectService.delete(1L);

        assertEquals("It is not possible to delete the project.", result);
        verify(projectRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testUpdateStatusSuccessProgress() {
        ProjectProgress projectProgress = new ProjectProgress();
        projectProgress.setProgress(true);
        project.setStatus(5); // Status inicial

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        String result = projectService.updateStatus(projectProgress, 1L);

        assertEquals("Status: " + Status.fromValue(6), result);
        verify(projectRepository).save(project);
        assertEquals(6, project.getStatus());
    }

    @Test
    public void testUpdateStatusSuccessCompleted() {
        project.setStatus(7);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        String result = projectService.updateStatus(new ProjectProgress(), 1L);

        assertEquals("project completed, estatus: " + Status.fromValue(7), result);
        verify(projectRepository, never()).save(project);
    }

    @Test
    public void testUpdateStatusFailNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            projectService.updateStatus(new ProjectProgress(), 1L);
        });
    }

}
