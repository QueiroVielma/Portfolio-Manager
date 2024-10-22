package gestorPortfolio.dto.project;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectProgress {
    @NotNull
    private boolean progress;
}
