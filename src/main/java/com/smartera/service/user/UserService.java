package com.smartera.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartera.entities.User;
import com.smartera.repository.user.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// GetAll
	public List<User> findAll() {
		return userRepository.findAll();
	}

	// GetById
	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}

	// Save user
	public User save(User user) {
		return userRepository.save(user);
	}

	// Update user
	public User update(User user) {
		User dbUser = userRepository.findById(user.getId()).get();
		dbUser.setPerm(user.getPerm());
		dbUser.setUsername(user.getUsername());
		return userRepository.save(dbUser);
	}

	// Delete user
	public void delete(int id) {
		userRepository.deleteById(id);
	}

}
