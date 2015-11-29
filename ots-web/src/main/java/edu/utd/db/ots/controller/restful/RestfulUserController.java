package edu.utd.db.ots.controller.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.utd.db.ots.dao.TransactionDao;
import edu.utd.db.ots.dao.UserDao;
import edu.utd.db.ots.domain.AuthInfo;
import edu.utd.db.ots.domain.RestfulResult;
import edu.utd.db.ots.domain.User;

@RestController
@RequestMapping("/users")
public class RestfulUserController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody RestfulResult register(@RequestBody User user) {
		RestfulResult result = new RestfulResult();
		
		try {
			result.success(userDao.addUser(user));
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/{userId}/profile", method = RequestMethod.GET)
	public @ResponseBody RestfulResult getProfile(@PathVariable("userId") int userId) {
		RestfulResult result = new RestfulResult();
		
		try {
			result.success(userDao.getUserById(userId));
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		
		return result;
	}

	@RequestMapping(value = "/{userId}/profile", method = RequestMethod.PUT)
	public @ResponseBody RestfulResult updateProfile(@PathVariable("userId") int userId, @RequestBody User user) {
		RestfulResult result = new RestfulResult();
		
		try {
			result.success(userDao.updateUser(userId, user));
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody RestfulResult updateProfile(@RequestBody AuthInfo authInfo) {
		RestfulResult result = new RestfulResult();
		
		try {
			result.success(userDao.login(authInfo));
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public @ResponseBody RestfulResult searchForUsers(@RequestParam(name = "fn") String fname, @RequestParam(name = "ln") String lname,
			@RequestParam(name = "addr") String addr, @RequestParam(name = "phone") String phone) {
		RestfulResult result = new RestfulResult();
		
		try {
			result.success(userDao.searchUser(fname, lname, addr, phone));
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "{cid}/trxns", method = RequestMethod.GET)
	public @ResponseBody RestfulResult searchTransactions(@PathVariable(value = "cid") int cid) {
		RestfulResult result = new RestfulResult();
		
		try {
			result.success(transactionDao.searchByUser(cid));
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		
		return result;
	}

	
}
