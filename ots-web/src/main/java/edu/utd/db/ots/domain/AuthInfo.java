package edu.utd.db.ots.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AuthInfo {

	private int cid;
	private String pass;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String encryptedPass() {
		String encrypted = null;
		// for password encryption
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			byte[] digestedBytes = md.digest();
			
			// then converting it to hashed format
			StringBuilder sb = new StringBuilder();
            for(int i=0; i< digestedBytes.length ;i++)
            {
                sb.append(Integer.toString((digestedBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            encrypted = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return encrypted;
	}
	
}
