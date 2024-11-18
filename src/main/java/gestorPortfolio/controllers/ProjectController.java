package gestorPortfolio.controllers;

import gestorPortfolio.dto.project.*;
import gestorPortfolio.entities.Project;
import gestorPortfolio.enums.Position;
import gestorPortfolio.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin("*")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody ProjectRequest request){
        try{
            projectService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Project created successfully.");
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create project: " + err.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            List<ProjectResponse> projects = projectService.findAll();
            return ResponseEntity.ok(projects);

        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to List project: " + err.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity <ProjectResponse> findById (@PathVariable Long id){
        try {
            Project project = this.projectService.findById(id);
            ProjectResponse projectResponse= ProjectResponse.project(project);
            return new ResponseEntity<>(projectResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateRequest request, @PathVariable Long id) {
        try {
            ProjectResponse result = projectService.update(request, id);
            return ResponseEntity.status(HttpStatus.OK).body("Project created successfully.");
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update project: " + err.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String mensagem = this.projectService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<String> attributeMembersById(@Valid @RequestBody MembersIdRequest request, @PathVariable Long id) {
        try {
            String result = projectService.attributeMembersById(request, id);
            return ResponseEntity.status(HttpStatus.OK).body("Project created successfully.");
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update menbers: " + err.getMessage());
        }
    }


    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateStatus(@Valid @RequestBody ProjectProgress request, @PathVariable Long id) {
        try {
            String result = projectService.updateStatus(request, id);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update project: " + err.getMessage());
        }
    }

    @GetMapping("/findByTitulo")
    public List<Project> findByTitulo(@RequestParam String nome) {
        return projectService.findProjectsByName(nome);
    }

}
