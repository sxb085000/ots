package edu.utd.db.ots.rowmapper;

import static edu.utd.db.ots.constant.OTSDBConstants.CLIENT_CID;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_CASH_OWED;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_CASH_PAID;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_OIL_AMT;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_OIL_OWED;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_OIL_PAID;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_STATUS;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_TRADER_ID;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_TRANS_DATE;
import static edu.utd.db.ots.constant.OTSDBConstants.TRANSACTION_XID;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.utd.db.ots.domain.Transaction;


public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {

    	Transaction trxn = new Transaction();
    	
    	trxn.setTransId(resultSet.getInt(TRANSACTION_XID));
    	trxn.setCid(resultSet.getInt(CLIENT_CID));
    	trxn.setOilAmt(resultSet.getDouble(TRANSACTION_OIL_AMT));
    	trxn.setOilOwed(resultSet.getDouble(TRANSACTION_OIL_OWED));
    	trxn.setTransDate(resultSet.getDate(TRANSACTION_TRANS_DATE));
    	trxn.setCachOwed(resultSet.getDouble(TRANSACTION_CASH_OWED));
    	trxn.setTraderId(resultSet.getInt(TRANSACTION_TRADER_ID));
    	trxn.setOilPaid(resultSet.getDouble(TRANSACTION_OIL_PAID));
    	trxn.setCachePaid(resultSet.getDouble(TRANSACTION_CASH_PAID));
    	trxn.setStatus(resultSet.getString(TRANSACTION_STATUS));
    	
        return trxn;
    }
}
