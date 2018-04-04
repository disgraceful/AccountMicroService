package com.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.model.UserAccount;

@Repository
public interface AccountRepository extends JpaRepository<UserAccount, Long> {
	public UserAccount findByLogin(String login);
	public UserAccount findByEmail(String email);
	
}
