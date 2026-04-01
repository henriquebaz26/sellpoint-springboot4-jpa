package com.sellpoint.SellPoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellpoint.SellPoint.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {   

}
