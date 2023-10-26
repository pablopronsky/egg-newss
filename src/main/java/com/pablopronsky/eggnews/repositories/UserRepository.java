package com.pablopronsky.eggnews.repositories;

import com.pablopronsky.eggnews.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email ")
    public User searchByEmail(@Param("email")String email);
}
