package com.atul.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atul.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
