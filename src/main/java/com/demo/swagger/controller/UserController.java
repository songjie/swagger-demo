package com.demo.swagger.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.swagger.Responses;
import com.demo.swagger.model.User;
import com.demo.swagger.repository.MapBackedRepository;

@Controller
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/user", description = "Operations about user")
public class UserController {
	UserRepository userRepository = new UserRepository();

	static class UserRepository extends MapBackedRepository<String, User> {
	}

	@RequestMapping(method = POST)
	@ResponseBody
	@ApiOperation(value = "Create user", notes = "This can only be done by the logged in user.")
	public ResponseEntity<User> createUser(
			@RequestBody @ApiParam(value = "Created user object", required = true) User user) {

		userRepository.add(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/createWithArray", method = POST)
	@ResponseBody
	@ApiOperation(value = "Creates list of users with given input array")
	public ResponseEntity<User> createUsersWithArrayInput(
			@ApiParam(value = "List of user object", required = true) User[] users) {
		for (User user : users) {
			userRepository.add(user);
		}
		return Responses.ok();
	}

	@RequestMapping(value = "/createWithList", method = POST)
	@ResponseBody
	@ApiOperation(value = "Creates list of users with given input array")
	public ResponseEntity<String> createUsersWithListInput(
			@ApiParam(value = "List of user object", required = true) List<User> users) {
		for (User user : users) {
			userRepository.add(user);
		}
		return Responses.ok();
	}

	@RequestMapping(value = "/{username}", method = PUT)
	@ResponseBody
	@ApiOperation(value = "Updated user", notes = "This can only be done by the logged in user.")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Invalid user supplied"),
		@ApiResponse(code = 404, message = "User not found")})
	public ResponseEntity<String> updateUser(
			@ApiParam(value = "name that need to be deleted", required = true) @PathVariable("username") String username,
			@ApiParam(value = "Updated user object", required = true) User user) {
		if (userRepository.get(username) != null) {
			userRepository.add(user);
			return Responses.ok();
		}
		return Responses.notFound();
	}

	@RequestMapping(value = "/{username}", method = DELETE)
	@ApiOperation(value = "Delete user", notes = "This can only be done by the logged in user.")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Invalid username supplied"),
		@ApiResponse(code = 404, message = "User not found")})
	public ResponseEntity<String> deleteUser(
			@ApiParam(value = "The name that needs to be deleted", required = true) @PathVariable("username") String
			username) {
		if (userRepository.exists(username)) {
			userRepository.delete(username);
			return Responses.ok();
		}
		return Responses.notFound();

	}

	@RequestMapping(value = "/{username}", method = GET)
	@ApiOperation(value = "Get user by user name", response = User.class)
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Invalid username supplied"),
		@ApiResponse(code = 404, message = "User not found")})
	public ResponseEntity<User> getUserByName(
			@ApiParam(value = "The name that needs to be fetched. Use user1 for testing. ", required = true) @PathVariable("username") String username) {
		User user = userRepository.get(username);
		if (null != user) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			throw new NotFoundException(404, "User not found");
		}
	}

	@RequestMapping(value = "/login", method = GET)
	@ApiOperation(value = "Logs user into the system", response = String.class)
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid username/password supplied")})
	public ResponseEntity<String> loginUser(
			@ApiParam(value = "The user name for login", required = true) @RequestParam("username") String username,
			@ApiParam(value = "The password for login in clear text", required = true) @RequestParam("password") String password) {
		return new ResponseEntity<String>("logged in user session:" + System.currentTimeMillis(), HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = GET)
	@ApiOperation(value = "Logs out current logged in user session")
	public ResponseEntity<String> logoutUser() {
		return Responses.ok();
	}
}
