package com.fitness.userservise.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.userservise.dto.RegisterRequest;
import com.fitness.userservise.dto.UserResponse;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.fitness.userservise.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> ValdiateUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.existByUserId(userId));
    }

    


}
