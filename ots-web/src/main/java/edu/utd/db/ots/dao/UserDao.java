package edu.utd.db.ots.dao;

import static edu.utd.db.ots.constant.OTSDBConstants.CLIENT_AUTH_PASS;
import static edu.utd.db.ots.constant.OTSDBConstants.CLIENT_CID;
import static edu.utd.db.ots.constant.OTSDBConstants.TABLE_CLIENT;
import static edu.utd.db.ots.constant.OTSDBConstants.TABLE_CLIENT_AUTH;

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
import edu.utd.db.ots.domain.User;
import edu.utd.db.ots.rowmapper.UserRowMapper;

@Repository
public class UserDao {
	
	/**
	 * queries to prepare
	 */
	public static final String QUERY_ADD_USER = "INSERT INTO " + TABLE_CLIENT + 
			" (First_name, Last_name, Street_addr, City, State, Zip, Email, Cell_number, Ph_number) VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String QUERY_ADD_USER_AUTH = "INSERT INTO " + TABLE_CLIENT_AUTH + "(" + CLIENT_CID + "," + CLIENT_AUTH_PASS + ") VALUES (?, ?)";
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
	public User addUser(final User user) {

		// add users to client table first
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement pstmt = conn.prepareStatement(QUERY_ADD_USER, new String[]{CLIENT_CID});
						
						pstmt.setString(1, user.getFname());
						pstmt.setString(2, user.getLname());
						pstmt.setString(3, user.getStreetAddr());
						pstmt.setString(4, user.getCity());
						pstmt.setString(5, user.getState());
						pstmt.setInt(6, user.getZip());
						pstmt.setString(7, user.getEmail());
						pstmt.setString(8, user.getCellNum());
						pstmt.setString(9, user.getPhoneNum());
						
						return pstmt;
					}
				},
				keyHolder
				);
		
		final int cid = keyHolder.getKey().intValue();
		user.setCid(cid);
		
		// add user to auth
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement pstmt = conn.prepareStatement(QUERY_ADD_USER_AUTH);
						
						pstmt.setInt(1, cid);
						pstmt.setString(2, user.encryptedPass());
						
						return pstmt;
					}
				});
		
		return user;
	}
	
	public User getUserById(int cid) {
		return jdbcTemplate.query(QUERY_SELECT_USER_BY_ID, new Integer[]{cid}, new ResultSetExtractor<User>() {
			@Override
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next() ? new UserRowMapper().mapRow(rs, 1) : null;
			}
		});
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
