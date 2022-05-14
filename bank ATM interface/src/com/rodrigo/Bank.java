package com.rodrigo;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	private String name;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts;
	
	public Bank(String name) {

		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
		
	}
	
	public void addAccount(Account anAccount) {
		this.accounts.add(anAccount);
	}
	
	public String getNewUserId() {
		String id = "";
		Random randow = new Random();
		int leng = 7;
		boolean nonUnique = false;
		do {
			for(int i = 0; i < leng; i++) {
				id += ((Integer) randow.nextInt(10)).toString();
			}
			for(User you : this.users) {
				if(id.compareTo(you.getId()) == 0) {
					nonUnique = true;
					break;
				}
			}
		}while(nonUnique);
		return id;
	}
	public String getNewAccountId() {
		String id = "";
		Random randow = new Random();
		int leng = 10;
		boolean nonUnique = false;
		do {
			for(int i = 0; i < leng; i++) {
				id += ((Integer) randow.nextInt(10)).toString();
			}
			for(Account acc : this.accounts) {
				if(id.compareTo(acc.getId()) == 0) {
					nonUnique = true;
					break;
				}
			}
		}while(nonUnique);
		return id;
	}
	
	
	
	public User addUser(String firstName, String lastName, String id) {
		
		User newUser = new User(firstName, lastName, id, this);
		
		this.users.add(newUser);
		
		Account newAccount = new Account("Savings", newUser, this);
		
		newUser.addAccount(newAccount);
		
		this.accounts.add(newAccount);
		
		return newUser;
	}
	
	public User userLogin(String userCpf, String userId) {
		
		for(User user : this.users) {
			if(user.getId().compareTo(userId) == 0 && user.validateCpf(userCpf)) {
				return user;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	
	

}
