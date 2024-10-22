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
    private String name;
    private LocalDate startDate;
    private LocalDate expectedEEndDate;
    private LocalDate actualEndDate;
    private double totalBudget;
    private String description;
    private Status status;
    private Risk risk;
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
            Status.fromValue(project.getStatus()),
            Risk.fromValue(project.getRisk()),
            project.getMembers(),
            project.getResponsibleManager()
        );
    }


}
