package gestorPortfolio.dto.project;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MembersIdRequest {
    @NotNull
    private List<Long> membersId;
}
