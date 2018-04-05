package com.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.microservice.model.UserAccount;
import com.microservice.repository.AccountRepository;
import com.microservice.utils.CryptUtils;

@Component
public class AccountMicroserviceDBInitializer implements ApplicationRunner {

	@Autowired
	private AccountRepository accRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		UserAccount user1 = new UserAccount("login", "123@123.com", CryptUtils.generateHashSHA1("password"));
		UserAccount user2 = new UserAccount("test", "test@123.com", CryptUtils.generateHashSHA1("qwerty"));
		UserAccount user3 = new UserAccount("admin", "admin@123.com", CryptUtils.generateHashSHA1("asd$asd"));
		accRepo.save(user1);
		accRepo.save(user2);
		accRepo.save(user3);
	}

}
