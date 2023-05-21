package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.dto.requests.UserRequestDto;
import tr.edu.ogu.ceng.dto.responses.UserResponseDto;
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
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
		UserDto savedUser = userService.saveUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
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

}
