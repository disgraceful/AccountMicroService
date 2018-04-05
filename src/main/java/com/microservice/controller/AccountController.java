package com.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.reqmodel.UserCreateModel;
import com.microservice.reqmodel.UserLoginModel;
import com.microservice.service.AccountService;

@RestController
public class AccountController {

	private final AccountService accService;

	@Autowired
	public AccountController(AccountService accService) {
		this.accService = accService;
	}

	@GetMapping("api/")
	public ResponseEntity<?> requests() {
		return new ResponseEntity<>(
				"<a href=\"/api/users\">/api/users</a>" + "<br/>" + "<a href=\"/api/users/1\">/api/users/{id}</a>",
				HttpStatus.OK);
	}

	@GetMapping("/api/users")
	public ResponseEntity<?> getAllUsers() {
		return new ResponseEntity<>(accService.getUsers(), HttpStatus.OK);
	}

	@GetMapping("/api/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable long id) {
		try {
			return new ResponseEntity<>(accService.getUserById(id), HttpStatus.OK);
		} catch (IllegalArgumentException exc) {
			return new ResponseEntity<>("User with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/api/users/create")
	public ResponseEntity<?> createUser(@RequestBody UserCreateModel model) {
		try {
			return new ResponseEntity<>(accService.createUser(model), HttpStatus.OK);
		} catch (IllegalArgumentException exc) {
			return new ResponseEntity<>("Error creating user, invalid credentials", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/api/users/login")
	public ResponseEntity<?>loginUser(@RequestBody UserLoginModel model){
		try {
			return new ResponseEntity<>(accService.getUserByLogin(model),HttpStatus.OK);
		}catch(IllegalArgumentException exc) {
			return new ResponseEntity<>("Invalid credentials, try again", HttpStatus.BAD_REQUEST);
		}
	}

}
