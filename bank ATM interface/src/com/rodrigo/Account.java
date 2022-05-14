package com.rodrigo;

import java.util.ArrayList;

public class Account {
	
	private String name;
	private double balance;
	private String id;
	private User holder;
	private ArrayList <Transation> transations;
	
	public Account(String name, User holder, Bank bank) {
		super();
		this.name = name;
		this.holder = holder;
		this.id = bank.getNewAccountId();
		this.transations = new ArrayList<Transation>();
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSumaryLine() {
		
		double balance = this.getBalance();
		
		if(balance >= 0) {
			return String.format("%s : $%.02f : %s", this.id, balance, this.name);
		}else {
			return String.format("%s : $(%.02f) : %s", this.id, balance, this.name);

		}
	}
	
	public double getBalance() {
		double balance = 0;
		for(Transation transation : this.transations) {
			balance += transation.getAmount();
		}
		return balance;
	}
	
	public void printTransHistory() {
		System.out.printf("\n Historico de transferencia da conta %s\n", this.id);
		for(int i = this.transations.size() - 1; i >= 0 ; i--) {
			System.out.println(this.transations.get(i).getSumaryLine());
		}
		System.out.println();
	}
	
	public void addTransaction(double amount, String memo) {
		
		Transation newTrans = new Transation(amount, memo, this);
		this.transations.add(newTrans);
	}

}
