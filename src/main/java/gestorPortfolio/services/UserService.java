package gestorPortfolio.services;

import gestorPortfolio.dto.user.UserRequest;
import gestorPortfolio.dto.user.UserResponse;
import gestorPortfolio.entities.User;
import gestorPortfolio.repositoies.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRespository userRespository;

    public UserResponse save(UserRequest userRequest) {
        User user= userRequest.user();
        return UserResponse.user(userRespository.save(user));
    }

    public UserResponse update(UserRequest userRequest, long id) {
        User user = this.userRespository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        user.setName(userRequest.getName());
        user.setDateOfBirth(userRequest.getDateBirth());
        user.setCpf(userRequest.getCpf());
        user.setPosition(userRequest.getPositionUser().getValue());

        return UserResponse.user(this.userRespository.save(user));
    }

    public UserResponse findById(Long id){
        User user=this.userRespository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        return UserResponse.user(user);
    }

    public List<UserResponse> findAll(){
        List <User> users= this.userRespository.findAll();
        return users.stream()
                .map(UserResponse::user)
                .collect(Collectors.toList());
    }
}
