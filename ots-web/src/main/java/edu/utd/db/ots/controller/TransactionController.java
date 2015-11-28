	package edu.utd.db.ots.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.utd.db.ots.dao.TransactionDao;
import edu.utd.db.ots.domain.Payment;
import edu.utd.db.ots.domain.RestfulResult;
import edu.utd.db.ots.domain.Transaction;
import edu.utd.db.ots.domain.TrxnStatus;

@RestController
@RequestMapping("/trxns")
public class TransactionController {

	@Autowired
	private TransactionDao transactionDao;
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody RestfulResult submit(@RequestBody Transaction trxn) {
		RestfulResult result = new RestfulResult();
		
		try {
			result.success(transactionDao.submitTransaction(trxn));
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		
		// if 
		
		return result;
	}
	
	@RequestMapping(value = "/{trxnId}/pay", method = RequestMethod.PUT) 
	public @ResponseBody RestfulResult pay(@PathVariable("trxnId") int trxnId, Payment payment) {
		RestfulResult result = new RestfulResult();
		
		try {
			result.success(transactionDao.payTransaction(trxnId, payment));
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		
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
	public @ResponseBody RestfulResult updateStatus(@PathVariable("trxnId") int trxnId, String statusString) {
		RestfulResult result = new RestfulResult();
		
		TrxnStatus status = TrxnStatus.UNKNOWN;
		try {
			status = TrxnStatus.valueOf(statusString);
		} catch (Exception e) {
			result.fail("Invalid status type '" + statusString + "'. Must be one of " + Arrays.toString(TrxnStatus.values()));
			return result;
		}
		
		try {
			result.success(transactionDao.updateStatus(trxnId, status));
			
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		
		return result;
	}
	
}
