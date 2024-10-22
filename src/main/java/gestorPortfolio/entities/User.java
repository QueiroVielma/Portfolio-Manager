package gestorPortfolio.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Entity

public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private LocalDate dateOfBirth;

    private int position;

    @ManyToMany (mappedBy = "members")
    @JsonBackReference
    private List<Project> projectsMembers;

    @OneToMany (mappedBy = "responsibleManager")
    @JsonBackReference
    private List<Project> projectsManager;
}
