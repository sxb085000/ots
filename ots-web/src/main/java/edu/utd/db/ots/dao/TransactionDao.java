package edu.utd.db.ots.dao;

import static edu.utd.db.ots.constant.OTSDBConstants.CLIENT_CID;
import static edu.utd.db.ots.constant.OTSDBConstants.TABLE_CLIENT;
import static edu.utd.db.ots.constant.OTSDBConstants.TABLE_TRANSACTION;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_XID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import edu.utd.db.ots.domain.ClientLevel;
import edu.utd.db.ots.domain.Payment;
import edu.utd.db.ots.domain.Transaction;
import edu.utd.db.ots.domain.TrxnStatus;
import edu.utd.db.ots.domain.User;
import edu.utd.db.ots.rowmapper.TransactionRowMapper;
import edu.utd.db.ots.rowmapper.UserRowMapper;


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
	public static final String QUERY_UPDATE_TRXN_STATUS = "UPDATE " + TABLE_TRANSACTION
			+ "SET Status = ? WHERE Trans_id = ?";
	public static final String QUERY_INVALIDATE_CLIENT_LEVEL = "UPDATE " + TABLE_CLIENT + " SET LEVEL = '" + ClientLevel.GOLD + "' "
			+ " WHERE " + CLIENT_CID + " IN (SELECT " + CLIENT_CID + " FROM " + TABLE_TRANSACTION + " WHERE Status = '" + TrxnStatus.APPROVED + "' "
					+ "GROUP BY " + CLIENT_CID + ", MONTH(Trans_date) HAVING SUM(Oil_amt) > 30) AND Level = '" + ClientLevel.SILVER + "'";
	public static final String QUERY_SELECT_TRANSACTION_BY_CLIENT_NAME = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + CLIENT_CID + " = ?";
	
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

	
	@Transactional
	public boolean updateStatus(int trxnId, TrxnStatus status) {
		boolean success = false;
		
		// update transaction to the status given
		int numUpdated = jdbcTemplate.update(QUERY_UPDATE_TRXN_PAYMENT, status.toString(), trxnId);
		
		
		// invalidate customer status
		if (numUpdated > 0 && TrxnStatus.APPROVED == status) {
			// if yes, update customer rating
			jdbcTemplate.update(QUERY_INVALIDATE_CLIENT_LEVEL);
		}
		
		success = true;
		return success;
	}
	
	public List<Transaction> searchByUser(int cid) {
		return jdbcTemplate.query(QUERY_SELECT_TRANSACTION_BY_CLIENT_NAME, new Integer[]{cid}, new TransactionRowMapper());
	}
	
}
