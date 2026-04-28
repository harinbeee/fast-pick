package com.example.fastpick.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fastpick.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

