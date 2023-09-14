package com.example.finalproject.user.repository;

import com.example.finalproject.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.restaurants.foods", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> getUserById(long id);
}
