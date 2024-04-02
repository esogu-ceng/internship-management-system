package tr.edu.ogu.ceng.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.dto.requests.UserRequestDto;
import tr.edu.ogu.ceng.dto.responses.UserResponseDto;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.UserService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController

@RequestMapping("/api/user")

public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/getAll")
	public Page<UserDto> getAllUsers(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "username") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<UserDto> users = userService.getAllUsers(pageable);
		return users;
	}

	@PostMapping
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(userDto, User.class);
		User savedUser = userService.addUser(user);

		UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
		return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
	}

	@PutMapping("/admin")
	public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto user) {
		UserResponseDto userDto = userService.updateUser(user);
		return ResponseEntity.ok(userDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(userService.deleteUser(id));
	}

	@PutMapping("/activity/{id}")
	public ResponseEntity<Boolean> setUserActivity(@PathVariable(name = "id") Long id,
			@RequestParam(defaultValue = "true") boolean status) {
		return ResponseEntity.ok(userService.setUserActivity(id, status));
	}

	@GetMapping(value = { "/admin/auth", "/student/auth", "/company-supervisor/auth", "/faculty-supervisor/auth" })
	public UserDto getLoggedInUser() {
		return userService.getLoggedInUser();
	}

}
