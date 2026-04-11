package com.fitness.userservise.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.userservise.Repository.UserRepository;
import com.fitness.userservise.dto.RegisterRequest;
import com.fitness.userservise.dto.UserResponse;
import com.fitness.userservise.model.User;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class UserService {

@Autowired
private UserRepository userRepo;

public UserResponse register(RegisterRequest request) {

    if(userRepo.existsByEmail(request.getEmail())){
        throw new RuntimeException("Email already exists");
    }
    User user=new User();
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    user.setFirstname(request.getFirstname());
    user.setLastname(request.getLastname());
    
    User savedUser=userRepo.save(user);
    UserResponse response=new UserResponse();
    response.setId(savedUser.getId());
    response.setEmail(savedUser.getEmail());
    response.setPassword(savedUser.getPassword());
    response.setFirstname(savedUser.getFirstname());
    response.setLastname(savedUser.getLastname());
    response.setCreatedAt(savedUser.getCreatedAt());
    response.setUpdatedAt(savedUser.getUpdatedAt());
    return response;   
}

    public UserResponse getUserProfile(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstname(user.getFirstname());
        userResponse.setLastname(user.getLastname());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }

    public Boolean existByUserId(String userId) {
        log.info("Calling User validation api for userId:{}",userId);
        return userRepo.existsById(userId);
    }
}

