package com.smartera.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.smartera.entities.User;
import com.smartera.repository.user.UserRepository;

public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAll() {
		List<User> users = new ArrayList<>();
		when(userRepository.findAll()).thenReturn(users);

		List<User> result = userService.findAll();

		assertEquals(users, result);
	}

	@Test
	public void testFindById() {
		int id = 1;
		User user = new User();
		user.setId(id);
		when(userRepository.findById(id)).thenReturn(Optional.of(user));

		Optional<User> result = userService.findById(id);

		assertTrue(result.isPresent());
		assertEquals(id, result.get().getId());
	}

	@Test
	public void testSave() {
		User user = new User();
		when(userRepository.save(user)).thenReturn(user);

		User result = userService.save(user);

		assertEquals(user, result);
	}

	@Test
	public void testUpdate() {
		User user = new User();
		user.setId(1);
		user.setUsername("testuser");

		User dbUser = new User();
		dbUser.setId(1);
		dbUser.setUsername("olduser");

		when(userRepository.findById(1)).thenReturn(Optional.of(dbUser));
		when(userRepository.save(dbUser)).thenReturn(dbUser);

		User result = userService.update(user);

		assertEquals(user.getUsername(), result.getUsername());
	}

	@Test
	public void testDelete() {
		int id = 1;

		userService.delete(id);

		verify(userRepository, times(1)).deleteById(id);
	}
}