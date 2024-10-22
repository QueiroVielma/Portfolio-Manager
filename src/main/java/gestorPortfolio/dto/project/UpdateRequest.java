package gestorPortfolio.dto.project;

import gestorPortfolio.entities.Project;
import gestorPortfolio.enums.Risk;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UpdateRequest {
    @NotBlank(message = "title is mandatory")
    @Size(min = 2, max = 120, message = "Title must be between 2 and 120 characters")
    private String nameProject;
    @NotNull
    private double budget;
    @NotBlank(message = "Description is mandatory")
    private String description;

    private Risk riskProject;

    public Project project (){
        Project project= new Project();
        project.setName(this.nameProject);
        project.setTotalBudget(this.budget);
        project.setRisk(this.riskProject.getValue());
        project.setDescription(this.description);
        return project;
    }
}
