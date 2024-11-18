package gestorPortfolio.dto.user;

import gestorPortfolio.entities.User;
import gestorPortfolio.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String name;
    private String cpf;
    private LocalDate dateBirth;
    private int positionUser;

    public static UserResponse user(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getCpf(),
                user.getDateOfBirth(),
                user.getPosition()
        );
    }
}
