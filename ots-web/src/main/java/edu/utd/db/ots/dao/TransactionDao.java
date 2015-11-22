package edu.utd.db.ots.dao;

import static edu.utd.db.ots.constant.OtsDBConstant.CLIENT_AUTH_PASS;
import static edu.utd.db.ots.constant.OtsDBConstant.CLIENT_CID;
import static edu.utd.db.ots.constant.OtsDBConstant.TABLE_CLIENT;
import static edu.utd.db.ots.constant.OtsDBConstant.TABLE_CLIENT_AUTH;
import static edu.utd.db.ots.constant.OtsDBConstant.TABLE_TRANSACTION;
import static edu.utd.db.ots.constant.OtsDBConstant.TRANSACTION_XID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.utd.db.ots.domain.AuthInfo;
import edu.utd.db.ots.domain.Payment;
import edu.utd.db.ots.domain.Transaction;
import edu.utd.db.ots.domain.User;


@Repository
public class TransactionDao {
	
	/**
	 * queries to prepare
	 */
	public static final String QUERY_CREATE_TRXN = "INSERT INTO " + TABLE_TRANSACTION + 
			" (Cid, Oil_amt, Oil_owed, Trans_date, Cach_owed, Trader_id, Oil_paid, Cach_paid, Status) VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String QUERY_UPDATE_TRXN_PAYMENT = "UPDATE " + TABLE_TRANSACTION
			+ "SET Cash_paid += ? WHERE Trans_id = ?";
	public static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + CLIENT_CID + " = ?";
	public static final String QUERY_UPDATE_USER = 
			"UDPATE user SET First_name = ?, Last_name = ?, Street_addr = ?, City = ?, State = ?, Zip = ?, Email = ?, Cell_number = ?, Ph_number = ? WHERE Cid = ?";
	public static final String QUERY_AUTHENTICATE = "SELECT * FROM " + TABLE_CLIENT_AUTH + " WHERE " + CLIENT_CID + " = ? AND " + CLIENT_AUTH_PASS + " = ?";
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Transactional
	public Transaction submitTransaction(final Transaction trxn) {

		// add users to client table first
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement pstmt = conn.prepareStatement(QUERY_CREATE_TRXN, new String[]{TRANSACTION_XID});
						
						pstmt.setInt(1, trxn.getCid());
						pstmt.setDouble(2, trxn.getOilAmt());
						pstmt.setDouble(3, trxn.getOilOwed());
						pstmt.setDate(4, trxn.getTransDate());
						pstmt.setDouble(5, trxn.getCachOwed());
						pstmt.setInt(6, trxn.getTraderId());
						pstmt.setDouble(7, trxn.getCachePaid());
						pstmt.setDouble(8, trxn.getOilPaid());
						pstmt.setString(9, trxn.getStatus());
						
						return pstmt;
					}
				},
				keyHolder
				);
		
		final int transId = keyHolder.getKey().intValue();
		trxn.setTransId(transId);
		
		// if oil paid is given
		
		return trxn;
	}
	
	/**
	 * used only for cash payment
	 * @param trxnId
	 * @param payment
	 * @return
	 */
	public boolean payTransaction(int trxnId, Payment payment) {
		boolean success = false;
		
		int numUpdated = jdbcTemplate.update(QUERY_UPDATE_TRXN_PAYMENT, payment.getCachPaid(), trxnId);
		success = numUpdated > 0;
		
		return success;
	}

	public User updateUser(int cid, User user) {
		
		jdbcTemplate.update(QUERY_UPDATE_USER, 
				user.getFname(), 
				user.getLname(), 
				user.getStreetAddr(), 
				user.getCity(), 
				user.getState(), 
				user.getZip(), 
				user.getEmail(),
				user.getCellNum(),
				user.getPhoneNum(),
				cid);
		
		user.setCid(cid);
		return user;
	}
	
	public boolean login(AuthInfo authInfo) {
		boolean success = false;
		
		Integer cid = jdbcTemplate.query(QUERY_AUTHENTICATE, new Object[]{authInfo.getCid(), authInfo.encryptedPass()}, new ResultSetExtractor<Integer>() {

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next() ? rs.getInt(1) : null;
			}
		});
		
		if(cid != null && cid.intValue() == authInfo.getCid()) {
			success = true;
		}
			
		return success;
	}
	
}
