package gestorPortfolio.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate expectedEEndDate;

    private LocalDate actualEndDate;

    private double totalBudget;

    private String description;

    private int status;

    private int risk;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "projects_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List <User> members;

    @ManyToOne
    @JsonManagedReference
    private User responsibleManager;
}
