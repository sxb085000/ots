package edu.utd.db.ots.rowmapper;

import static edu.utd.db.ots.constant.OtsDBConstant.CLIENT_CID;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.utd.db.ots.domain.User;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

    	User user = new User();
    	
    	user.setCid(resultSet.getInt(CLIENT_CID));
    	

        return user;
    }
}
