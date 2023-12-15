package com.example.order.repository;

import com.example.order.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    @Query("select u FROM User u WHERE u.userName = :userName ")
    Optional<User> findByUserName(@Param("userName") String userName);


    Optional<User> findByUserNameAndPassword(String userName, String password);

    Optional<User> findByEmailAndPassword(String userName, String password);
    Optional<User> findByEmail(String email);
}
