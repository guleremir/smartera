package com.smartera.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smartera.dto.ErrorResponseDTO;
import com.smartera.dto.SuccessResponseDTO;
import com.smartera.dto.user.UserDTO;
import com.smartera.dto.user.UserDeleteDTO;
import com.smartera.dto.user.UserEditDTO;
import com.smartera.dto.user.UserGetAllDTO;
import com.smartera.dto.user.UserSaveDTO;
import com.smartera.entities.User;
import com.smartera.service.user.UserService;

public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Mock
	private ModelMapper requestMapper;

	@Mock
	private ModelMapper responseMapper;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAll() {
		List<User> userList = new ArrayList<>();
		when(userService.findAll()).thenReturn(userList);

		List<UserGetAllDTO> userGetAllDTOList = new ArrayList<>();
		when(responseMapper.map(any(), Mockito.eq(UserGetAllDTO.class)))
				.thenReturn(new UserGetAllDTO());

		ResponseEntity<List<UserGetAllDTO>> responseEntity = userController
				.getAll();

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(0, responseEntity.getBody().size());
	}

	@Test
	public void testGetById_WhenUserExists() {
		int userId = 1;
		User user = new User();
		user.setId(userId);
		Optional<User> optionalUser = Optional.of(user);

		when(userService.findById(userId)).thenReturn(optionalUser);

		when(responseMapper.map(any(), Mockito.eq(UserDTO.class)))
				.thenReturn(new UserDTO());

		ResponseEntity<?> responseEntity = userController.getById(userId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testGetById_WhenUserNotExists() {
		int userId = 1;
		Optional<User> optionalUser = Optional.empty();

		when(userService.findById(userId)).thenReturn(optionalUser);

		ResponseEntity<?> responseEntity = userController.getById(userId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testSave() {
		UserSaveDTO dto = new UserSaveDTO();
		dto.setUsername("John Doe");

		User user = new User();
		user.setUsername(dto.getUsername());

		when(requestMapper.map(dto, User.class)).thenReturn(user);

		SuccessResponseDTO responseDTO = userController.save(dto);

		assertNotNull(responseDTO);
		assertEquals("User saved!", responseDTO.getMessage());

		verify(requestMapper, times(1)).map(dto, User.class);
		verify(userService, times(1)).save(user);
	}

	@Test
	public void testUpdate() {
		UserEditDTO dto = new UserEditDTO();
		dto.setId(1);

		User user = new User();
		user.setId(dto.getId());

		when(requestMapper.map(dto, User.class)).thenReturn(user);

		ResponseEntity<?> responseEntity = userController.update(dto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("User updated!",
				((SuccessResponseDTO) responseEntity.getBody()).getMessage());

		verify(requestMapper, times(1)).map(dto, User.class);
		verify(userService, times(1)).update(user);
	}

	@Test
	public void testUpdate_WhenExceptionThrown() {
		UserEditDTO dto = new UserEditDTO();
		dto.setId(1);

		User user = new User();
		user.setId(dto.getId());

		when(requestMapper.map(dto, User.class)).thenReturn(user);
		doThrow(new RuntimeException()).when(userService).update(user);

		ResponseEntity<?> responseEntity = userController.update(dto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
				responseEntity.getStatusCode());
		assertEquals("Failed to update user",
				((ErrorResponseDTO) responseEntity.getBody()).getMessage());

		verify(requestMapper, times(1)).map(dto, User.class);
		verify(userService, times(1)).update(user);
	}

	@Test
	public void testDelete() {
		UserDeleteDTO dto = new UserDeleteDTO();
		dto.setId(1);

		SuccessResponseDTO responseDTO = userController.delete(dto);

		assertNotNull(responseDTO);
		assertEquals("User deleted!", responseDTO.getMessage());

		verify(userService, times(1)).delete(dto.getId());
	}

}