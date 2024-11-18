package gestorPortfolio.dto.user;

import gestorPortfolio.entities.User;
import gestorPortfolio.enums.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    @NotBlank(message = "CPF is mandatory")
    @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits")
    @CPF(message = "CPF invalido")
    private String cpf;
    @NotNull
    private LocalDate dateBirth;
    @NotNull
    private int positionUser;

    public User user() {
        User user=new User();
        user.setName(this.name);
        user.setCpf(this.cpf);
        user.setDateOfBirth(this.dateBirth);
        user.setPosition(positionUser);
        return user ;
    }
}
