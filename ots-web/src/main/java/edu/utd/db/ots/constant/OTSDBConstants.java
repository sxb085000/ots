package edu.utd.db.ots.constant;

public class OTSDBConstants {

	/**
	 * table names
	 */
	public static final String TABLE_CLIENT_AUTH = "Client_Auth";
	public static final String TABLE_CLIENT = "Client";
	public static final String TABLE_TRANSACTION = "Transaction";
	
	/**
	 * columns names (not all, but things likely to change)
	 */
	
	public static final String CLIENT_CID = "Cid";
	public static final String CLIENT_FNAME = "First_name";
	public static final String CLIENT_LNAME = "Last_name";
	public static final String CLIENT_STREET_ADDR = "Street_addr";
	public static final String CLIENT_CITY = "City";
	public static final String CLIENT_STATE = "State";
	public static final String CLIENT_ZIP = "Zip";
	public static final String CLIENT_EMAIL = "Email";
	public static final String CLIENT_CELL_NUM = "Cell_number";
	public static final String CLIENT_PHONE_NUM = "Ph_number";

	public static final String CLIENT_AUTH_PASS = "Password";
	
	public static final String TRANSACTION_XID = "Transid";
	public static final String TRANSACTION_OIL_AMT = "Oil_amt";
	public static final String TRANSACTION_OIL_OWED = "Oil_owed";
	public static final String TRANSACTION_TRANS_DATE = "Trans_date";
	public static final String TRANSACTION_CASH_OWED = "Cash_owed";
	public static final String TRANSACTION_TRADER_ID = "Trader_id";
	public static final String TRANSACTION_OIL_PAID = "Oil_paid";
	public static final String TRANSACTION_CASH_PAID = "Cash_paid";
	public static final String TRANSACTION_STATUS = "Status";
	
	
	
}
