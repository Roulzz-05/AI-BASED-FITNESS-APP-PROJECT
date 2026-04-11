package com.fitness.userservise.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fitness.userservise.model.User;


public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    
    
}
