package edu.utd.db.ots.domain;

import java.sql.Date;

public class Transaction {

	private int transId;
	private int cid;
	private double oilAmt;
	private double oilOwed;
	private double oilPaid;
	private Date transDate;
	private double cachOwed;
	private int traderId;
	private double cachePaid;
	private String status;
	
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public double getOilAmt() {
		return oilAmt;
	}
	public void setOilAmt(double oilAmt) {
		this.oilAmt = oilAmt;
	}
	public double getOilOwed() {
		return oilOwed;
	}
	public void setOilOwed(double oilOwed) {
		this.oilOwed = oilOwed;
	}
	public double getOilPaid() {
		return oilPaid;
	}
	public void setOilPaid(double oilPaid) {
		this.oilPaid = oilPaid;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public double getCachOwed() {
		return cachOwed;
	}
	public void setCachOwed(double cachOwed) {
		this.cachOwed = cachOwed;
	}
	public int getTraderId() {
		return traderId;
	}
	public void setTraderId(int traderId) {
		this.traderId = traderId;
	}
	public double getCachePaid() {
		return cachePaid;
	}
	public void setCachePaid(double cachePaid) {
		this.cachePaid = cachePaid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
