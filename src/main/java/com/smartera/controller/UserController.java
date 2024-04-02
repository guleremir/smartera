package com.smartera.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartera.entities.User;
import com.smartera.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAll() {
		List<User> allUser = userService.findAll();
		return ResponseEntity.ok(allUser);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		Optional<User> optionalUser = userService.findById(id);
		if (optionalUser.isPresent()) {
			return ResponseEntity.ok(optionalUser.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("User not found with id: " + id);
		}
	}

	@PostMapping("/save")
	public User save(@RequestBody User user) {
		return userService.save(user);
	}

	@PutMapping("/update")
	public User update(@RequestBody User user) {

		return userService.update(user);

	}

	@DeleteMapping("/delete")
	public void delete(@RequestBody User user) {
		userService.delete(user.getId());
	}
}
