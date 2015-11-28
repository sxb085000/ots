package edu.utd.db.ots.rowmapper;

import static edu.utd.db.ots.constant.OTSDBConstants.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.utd.db.ots.domain.User;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

    	User user = new User();
    	
    	user.setCid(resultSet.getInt(CLIENT_CID));
    	user.setFname(resultSet.getString(CLIENT_FNAME));
    	user.setLname(resultSet.getString(CLIENT_LNAME));
    	user.setStreetAddr(resultSet.getString(CLIENT_STREET_ADDR));
    	user.setCity(resultSet.getString(CLIENT_CITY));
    	user.setState(resultSet.getString(CLIENT_STATE));
    	user.setZip(resultSet.getInt(CLIENT_ZIP));
    	user.setEmail(resultSet.getString(CLIENT_EMAIL));
    	user.setCellNum(resultSet.getString(CLIENT_CELL_NUM));
    	user.setPhoneNum(resultSet.getString(CLIENT_PHONE_NUM));
    	
        return user;
    }
}
