package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.UserService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController

@RequestMapping("/api/user")

public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/getAll")
	public Page<User> getAllUsers(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "username") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<User> users = userService.getAllUsers(pageable);
		return users;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(userService.deleteUser(id));
	}

	@PostMapping("")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User user1 = userService.saveUser(user);
		return new ResponseEntity<>(user1, HttpStatus.CREATED);
	}

}
