package com.smartera.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.smartera.dto.ErrorResponseDTO;
import com.smartera.dto.SuccessResponseDTO;
import com.smartera.dto.user.UserDTO;
import com.smartera.dto.user.UserDeleteDTO;
import com.smartera.dto.user.UserEditDTO;
import com.smartera.dto.user.UserGetAllDTO;
import com.smartera.dto.user.UserSaveDTO;
import com.smartera.entities.User;
import com.smartera.service.user.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@GetMapping("/getAll")
	public ResponseEntity<List<UserGetAllDTO>> getAll() {
		List<User> allUser = userService.findAll();
		List<UserGetAllDTO> allUserDTO = new ArrayList<>();
		allUser.forEach(user -> {
			allUserDTO.add(responseMapper.map(user, UserGetAllDTO.class));
		});
		return ResponseEntity.ok(allUserDTO);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		Optional<User> optionalUser = userService.findById(id);
		if (optionalUser.isPresent()) {
			UserDTO dto = responseMapper.map(optionalUser, UserDTO.class);
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("User not found with id: " + id);
		}
	}

	@PostMapping("/save")
	public SuccessResponseDTO save(@RequestBody UserSaveDTO dto) {
		User user = requestMapper.map(dto, User.class);
		userService.save(user);
		return new SuccessResponseDTO("User saved!");
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserEditDTO dto) {
		try {
			User user = requestMapper.map(dto, User.class);
			userService.update(user);
			return ResponseEntity.ok(new SuccessResponseDTO("User updated!"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponseDTO("Failed to update user"));
		}
	}

	@DeleteMapping("/delete")
	public SuccessResponseDTO delete(@RequestBody UserDeleteDTO dto) {
		userService.delete(dto.getId());
		return new SuccessResponseDTO("User deleted!");

	}
}
