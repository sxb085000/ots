package edu.utd.db.ots.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.utd.db.ots.domain.AuthInfo;
import edu.utd.db.ots.domain.RestfulResult;
import edu.utd.db.ots.domain.User;

@RestController
@RequestMapping("/users")
public class UserController {

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody RestfulResult register(@RequestBody User user) {
		RestfulResult result = new RestfulResult();
		
		return result;
	}
	
	@RequestMapping(value = "/{userId}/profile", method = RequestMethod.GET)
	public @ResponseBody RestfulResult getProfile(@PathVariable("userId") String userId) {
		RestfulResult result = new RestfulResult();
		
		return result;
	}

	@RequestMapping(value = "/{userId}/profile", method = RequestMethod.PUT)
	public @ResponseBody RestfulResult updateProfile(@PathVariable("userId") String userId, @RequestBody User user) {
		RestfulResult result = new RestfulResult();
		
		return result;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody RestfulResult updateProfile(@RequestBody AuthInfo authInfo) {
		RestfulResult result = new RestfulResult();
		
		return result;
	}

	
}
