package gestorPortfolio.dto.project;

import gestorPortfolio.entities.Project;
import gestorPortfolio.entities.User;
import gestorPortfolio.enums.Risk;
import gestorPortfolio.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProjectResponse {
    private Long id;
    private String nameProject;
    private LocalDate startDate;
    private LocalDate expectedEEndDate;
    private LocalDate actualEndDate;
    private double budget;
    private String description;
    private int status;
    private int riskProject;
    private List<User> members;
    private User responsibleManager;

    public static ProjectResponse project(Project project){

        return new ProjectResponse(
            project.getId(),
            project.getName(),
            project.getStartDate(),
            project.getExpectedEEndDate(),
            project.getActualEndDate(),
            project.getTotalBudget(),
            project.getDescription(),
            project.getStatus(),
            project.getRisk(),
            project.getMembers(),
            project.getResponsibleManager()
        );
    }


}
