package gestorPortfolio.services;

import gestorPortfolio.dto.project.*;
import gestorPortfolio.entities.Project;
import gestorPortfolio.entities.User;
import gestorPortfolio.enums.Status;
import gestorPortfolio.repositoies.ProjectRepository;
import gestorPortfolio.repositoies.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRespository userRespository;

    public ProjectResponse save(ProjectRequest projectRequest){
        User manager= this.userRespository.findById(projectRequest.getResponsibleManager())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + projectRequest.getResponsibleManager()));
        if (manager.getPosition()!=2){
            throw new IllegalArgumentException("User is not a manager: " + manager.getName());
        }
        Project project= projectRequest.project();
        project.setStartDate(LocalDate.now());
        project.setStatus(Status.UNDER_REVIEW.getValue());
        project.setResponsibleManager(manager);
        return ProjectResponse.project(this.projectRepository.save(project));
    }

    public ProjectResponse update(UpdateRequest projectRequest, long id){
        Project project= this.findById(id);
        project.setName(projectRequest.getNameProject());
        project.setRisk(projectRequest.getRiskProject().getValue());
        project.setTotalBudget(projectRequest.getBudget());
        project.setDescription(projectRequest.getDescription());
        project.setId(id);
        return ProjectResponse.project(this.projectRepository.save(project));
    }

    public Project findById(Long id){
        Project project=this.projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project ID: " + id));
        return project;
    }

    public List<ProjectResponse> findAll(){
        List <Project> projects= this.projectRepository.findAll();
        return projects.stream()
                .map(ProjectResponse::project)
                .collect(Collectors.toList());
    }

    public String delete(Long id){
        Project project= this.findById(id);
        if(project.getStatus()<4 || project.getStatus()==8 ) {
            projectRepository.deleteById(id);
            return "User deleted successfully";
        }else {
            return "It is not possible to delete the project.";
        }
    }

    public String attributeMembersById(MembersIdRequest menbersId, Long projectId){
        List<User> menbers=userRespository.findAllById(menbersId.getMembersId());
        Project project= this.findById(projectId);
        project.setMembers(menbers);
        projectRepository.save(project);
        return"Members added with success";
    }

    public String updateStatus(ProjectProgress projectProgress, Long projectId){
        Project project= this.findById(projectId);
        if(project.getStatus()>6){
            return "project completed, estatus: "+ Status.fromValue(project.getStatus());
        }
        if (projectProgress.isProgress() && project.getStatus() < 7) {
            project.setStatus(project.getStatus()+1);
        } else {
            project.setStatus(8);
        }
        if(project.getStatus()>6){
            project.setActualEndDate(LocalDate.now());
        }
        projectRepository.save(project);
        return "Status: "+ Status.fromValue(project.getStatus());
    }

    public List<Project> findProjectsByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }

}
