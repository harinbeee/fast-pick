package com.example.fastpick.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fastpick.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByMail(String mail);

	@Query(
		"""
		select u.mail
		from User u
		where u.mail = :mail
		"""
	)
	Optional<String> findExistMail(String mail);

	boolean existsByMail(String mail);
}


