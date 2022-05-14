package com.rodrigo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	
	private String firstName;
	private String lastName;
	private String cpf;
	private String id;
	private byte [] pinHash;
	private ArrayList <Account> accounts;
	
	public User(String firstName, String lastName, String cpf, Bank bank) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		try {
			MessageDigest MensageDigest = MessageDigest.getInstance("MD5");
			this.pinHash = MensageDigest.digest(cpf.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caugth NoSuchAlgoritmException");
			e.printStackTrace();
			System.exit(1);
		}
		this.id = bank.getNewUserId();
		this.accounts = new ArrayList<Account>();
		
		System.out.printf("New user %s, %s with ID %s created\n", firstName, lastName, this.id);
		
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean validateCpf(String aCpf) {
		
		try {
			MessageDigest mensageDigest = MessageDigest.getInstance("MD5");
			return mensageDigest.isEqual(mensageDigest.digest(aCpf.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caugth NoSuchAlgoritmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void printAccountSumary() {
		
		System.out.printf("\n\n%s ´s resumo da conta\n", this.firstName);
		for(int i = 0; i < accounts.size(); i++) {
			System.out.printf(" %d) %s\n ", i+1 ,this.accounts.get(i).getSumaryLine());
		}
		System.out.println();
	}
	
	public int numAccounts() {
		return this.accounts.size();
	}
	
	public void printAccTransHistory(int accountIndex) {
		this.accounts.get(accountIndex).printTransHistory();
	}
	
	public double getAcctBalance(int acctIndex) {
		return this.accounts.get(acctIndex).getBalance();
	}
	
	public String getAcctUUID(int acctIndex) {
		return this.accounts.get(acctIndex).getId();
	}
	public void addAcctTransaction(int acctIndex, double amount, String memo) {
		this.accounts.get(acctIndex).addTransaction(amount , memo);
	}
}
