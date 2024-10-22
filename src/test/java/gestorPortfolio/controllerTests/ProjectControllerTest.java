package gestorPortfolio.controllerTests;

import gestorPortfolio.controllers.ProjectController;
import gestorPortfolio.dto.project.ProjectRequest;
import gestorPortfolio.dto.project.ProjectResponse;
import gestorPortfolio.dto.project.UpdateRequest;
import gestorPortfolio.entities.Project;
import gestorPortfolio.entities.User;
import gestorPortfolio.enums.Risk;
import gestorPortfolio.enums.Status;
import gestorPortfolio.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {
    @Autowired
    private ProjectController projectController;

    @MockBean
    private ProjectService projectService;

    private ProjectRequest projectRequest;
    private ProjectResponse projectResponse;
    private Project project;

    @BeforeEach
    void setUp() {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setNameProject("New Website Development");
        projectRequest.setExpectedEndDate(LocalDate.of(2024, 12, 31));
        projectRequest.setManagerId(1L);
        projectRequest.setBudget(50000.00);
        projectRequest.setDescription("Develop a responsive website for the company's portfolio.");
        projectRequest.setRiskProject(Risk.LOW);

        User responsibleManager = new User();
        responsibleManager.setId(1L);
        responsibleManager.setName("John Doe");
        responsibleManager.setCpf("65195950040");
        responsibleManager.setDateOfBirth(LocalDate.of(1990, 1, 1));
        responsibleManager.setPosition(2);

        List<User> members = new ArrayList<>();
        User member1 = new User();
        member1.setId(2L);
        member1.setName("Alice Smith");
        member1.setCpf("65195950040");
        member1.setDateOfBirth(LocalDate.of(1992, 2, 2));
        member1.setPosition(3);

        User member2 = new User();
        member2.setId(3L);
        member2.setName("Bob Johnson");
        member2.setCpf("65195950040");
        member2.setDateOfBirth(LocalDate.of(1994, 3, 3));
        member2.setPosition(3);

        members.add(member1);
        members.add(member2);

        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(1L);
        projectResponse.setName("Project A");
        projectResponse.setStartDate(LocalDate.now());
        projectResponse.setExpectedEEndDate(LocalDate.of(2024, 12, 31));
        projectResponse.setTotalBudget(10000);
        projectResponse.setDescription("Description of Project A");
        projectResponse.setStatus(Status.UNDER_REVIEW);
        projectResponse.setRisk(Risk.LOW);
        projectResponse.setResponsibleManager(responsibleManager);
        projectResponse.setMembers(members);

        project = new Project();
        project.setId(1L);
        project.setName("Project A");
        project.setExpectedEEndDate(LocalDate.of(2024, 12, 31));
        project.setTotalBudget(10000.00);
        project.setDescription("Description of Project A");
        project.setStatus(1);
        project.setRisk(1);
    }

    @Test
    void testSaveProjectSuccess() {
        when(projectService.save(any(ProjectRequest.class))).thenReturn(projectResponse);

        ResponseEntity<String> response = projectController.save(projectRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created successfully.", response.getBody());
    }

    @Test
    void testFindAllProjectsSuccess() {
        List<ProjectResponse> projects = new ArrayList<>();
        projects.add(projectResponse);
        when(projectService.findAll()).thenReturn(projects);

        ResponseEntity<?> response = projectController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void testFindAllProjectsFailure() {
        when(projectService.findAll()).thenThrow(new RuntimeException("Error fetching projects"));

        ResponseEntity<?> response = projectController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Failed to List users: Error fetching projects"));
    }

    @Test
    void testFindProjectByIdSuccess() {
        when(projectService.findById(anyLong())).thenReturn(project);

        ResponseEntity<ProjectResponse> response = projectController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project.getName(), response.getBody().getName());
    }

    @Test
    void testFindProjectByIdFailure() {
        when(projectService.findById(anyLong())).thenThrow(new IllegalArgumentException("Invalid project ID"));

        ResponseEntity<ProjectResponse> response = projectController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateProjectSuccess() {
        when(projectService.update(any(UpdateRequest.class), anyLong())).thenReturn(projectResponse);

        ResponseEntity<?> response = projectController.update(new UpdateRequest(), 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectResponse, response.getBody());
    }

    @Test
    void testUpdateProjectFailure() {
        when(projectService.update(any(UpdateRequest.class), anyLong())).thenThrow(new RuntimeException("Error updating project"));

        ResponseEntity<?> response = projectController.update(new UpdateRequest(), 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Failed to update user: Error updating project"));
    }

    @Test
    void testDeleteProjectSuccess() {
        when(projectService.delete(anyLong())).thenReturn("Project deleted successfully");

        ResponseEntity<String> response = projectController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Project deleted successfully", response.getBody());
    }

    @Test
    void testDeleteProjectFailure() {
        when(projectService.delete(anyLong())).thenThrow(new RuntimeException("Error deleting project"));

        ResponseEntity<String> response = projectController.delete(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
