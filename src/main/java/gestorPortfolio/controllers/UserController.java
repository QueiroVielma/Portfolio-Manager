package gestorPortfolio.controllers;

import gestorPortfolio.dto.user.UserRequest;
import gestorPortfolio.dto.user.UserResponse;
import gestorPortfolio.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody UserRequest request){
        try{
            userService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user: " + err.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            List<UserResponse> users = userService.findAll();
            return ResponseEntity.ok(users);

        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to List users: " + err.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity <UserResponse> findById (@PathVariable Long id){
        try {
            UserResponse responseUser = this.userService.findById(id);
            return new ResponseEntity<>(responseUser, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest request, @PathVariable Long id) {
        try {
            UserResponse result = userService.update(request, id);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user: " + err.getMessage());
        }
    }
}
