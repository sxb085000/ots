package edu.utd.db.ots.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.utd.db.ots.domain.Payment;
import edu.utd.db.ots.domain.RestfulResult;
import edu.utd.db.ots.domain.Transaction;

@RestController
@RequestMapping("/trxns")
public class TransactionController {

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody RestfulResult submit(@RequestBody Transaction trans) {
		RestfulResult result = new RestfulResult();
		
		return result;
	}
	
	@RequestMapping(value = "/{trxnId}/pay", method = RequestMethod.PUT) 
	public @ResponseBody RestfulResult pay(@PathVariable("trxnId") String trxnId, Payment payment) {
		RestfulResult result = new RestfulResult();
		
		return result;
	}
	
	/**
	 * Used to either approve/disapprove
	 * 
	 * @param trxnId
	 * @param payment
	 * @return
	 */
	@RequestMapping(value = "/{trxnId}/status", method = RequestMethod.PUT) 
	public @ResponseBody RestfulResult updateStatus(@PathVariable("trxnId") String trxnId, Payment payment) {
		RestfulResult result = new RestfulResult();
		
		return result;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody RestfulResult searchTransactions() {
		RestfulResult result = new RestfulResult();
		
		return result;
	}
}
