package com.microservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.model.UserAccount;
import com.microservice.repository.AccountRepository;
import com.microservice.reqmodel.UserCreateModel;
import com.microservice.reqmodel.UserLoginModel;
import com.microservice.utils.CryptUtils;

@Service
public class AccountService {
	private final AccountRepository accRepo;

	@Autowired
	public AccountService(AccountRepository accRepo) {
		this.accRepo = accRepo;
	}

	@Transactional
	public List<UserAccount> getUsers() {
		return accRepo.findAll();
	}

	@Transactional
	public UserAccount getUserById(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return accRepo.getOne(id);
	}

	@Transactional
	public UserAccount getUserByLogin(UserLoginModel model) {
		UserAccount user = accRepo.findByLogin(model.getLogin());
		if (user == null || !user.getPassword().equals(CryptUtils.generateHashSHA1(model.getPassword().trim()))) {
			throw new IllegalArgumentException();
		} else {
			return user;
		}
	}

	@Transactional
	public UserAccount createUser(UserCreateModel model) {
		if (model == null) {
			throw new IllegalArgumentException();
		}
		if (model.getLogin().trim().isEmpty() || model.getEmail().trim().isEmpty()
				|| model.getPassword().trim().isEmpty()
				|| !model.getPassword().trim().equals(model.getConfirmPassword().trim())) {
			throw new IllegalArgumentException();
		}
		String encryptedPassword = CryptUtils.generateHashSHA1(model.getPassword().trim());
		UserAccount user = new UserAccount(model.getLogin(), model.getEmail(), encryptedPassword);
		return accRepo.saveAndFlush(user);
	}
}
