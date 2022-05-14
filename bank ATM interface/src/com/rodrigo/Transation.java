package com.rodrigo;

import java.util.Date;

public class Transation {
	
	private double amount;
	private Date timeStap;
	private String nemo;
	private Account inAccount;
	
	public Transation(double amount, Account inAccount) {
		this.amount = amount;
		this.inAccount = inAccount;
		this.timeStap = new Date();
		this.nemo = "";
	}

	
	public Transation(double amount, String nemo, Account inAccount) {
		this(amount, inAccount);
		this.nemo = nemo;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getSumaryLine() {
		if(this.amount >= 0) {
			return String.format("%s : $%.02f : %s\n", this.timeStap.toString(), 
					this.amount, this.nemo);
		}else {
			return String.format("%s : $(%.02f) : %s\n", this.timeStap.toString(), 
					-this.amount, this.nemo);
		}
	}
	
	

}
