package com.rodrigo;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		
		Bank bank = new Bank("Banco do Brasil");
		
		User usuario = bank.addUser("Rodrigo", "Soares", "1234");
		
		Account newAccount = new Account("Cheking", usuario, bank);
		
		usuario.addAccount(newAccount);
		
		bank.addAccount(newAccount);
		
		User curUser;
		while(true) {
			curUser = ATM.mainMenuPrompt(bank, input);
			
			ATM.printUserMenu(curUser, input);
		}
	}
	
	public static User mainMenuPrompt(Bank bank, Scanner input) {
		
		String userCpf;
		
		String id;
		
		User authUser;
		
		do {
			
			System.out.printf("\n\n Bem vindo ao %s \n\n", bank.getName());
			
			System.out.println("Digite seu cpf :");
			userCpf = input.next();
			input.nextLine();
			System.out.println("Digite seu numero registro :");
			id = input.next();
			
			authUser = bank.userLogin(userCpf, id);
			
			if(authUser == null) {
				System.out.println("Cpf e registo errados! "
						+ "Por favor tente novamente");
			}
			
		}while(authUser == null);
		
		return authUser;
		
	}
	
	public static void printUserMenu(User user, Scanner input) {
		
		user.printAccountSumary();
		
		int choise;
		
		do {
			System.out.printf("Bem vindo %s escolha uma opção de gostaria de realizar \n", user.getFirstName());
			System.out.println(" 1) historico de transações");
			System.out.println(" 2) para sacar algum valor");
			System.out.println(" 3) para depositar");
			System.out.println(" 4) para realizar uma transferencia");
			System.out.println(" 5 para sair");
			System.out.println("Digite sua escolha ");
			choise = input.nextInt();
			
			if(choise < 1 || choise > 5) {
				System.out.println("Escolha invalida por favor escolha um numero de 1 a 5");
			}
			
		}while(choise < 1 || choise > 5);
		
		switch(choise) {
		case 1 :
			ATM.showTransHistory(user, input);
			break;
		case 2 :
			ATM.withdrawFounds(user, input);
			break;
		case 3 :
			ATM.depositFounds(user, input);
			break;
		case 4 :
			ATM.transferFounds(user, input);
			break;
		case 5 :
			input.nextLine();
			break;
		}
		
		if(choise != 5) {
			ATM.printUserMenu(user, input);
		}
	}
	
	public static void transferFounds(User theUser, Scanner input) {
		
		int fromAcct;
		int toAcct;
		double amount;
		double acctBalance;
		
		do {
			System.out.printf("Digite o numero da sua conta que aparece entre parenteses (1-%d)\n"
					+ "para transferir um valor : ", theUser.numAccounts());
			fromAcct = input.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Conta invalida por favor tente novamente!");
			}
			
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBalance = theUser.getAcctBalance(fromAcct);
		
		
		do {
			System.out.printf("Digite o numero da sua conta (1-%d)\n"
					+ "transferindo de para : ", theUser.numAccounts());
			toAcct = input.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Conta invalida por favor tente novamente!");
			}
			
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		
		do {
			System.out.printf("Digite o valor da transferencia saldo (%.2f) : ", acctBalance);
			amount = input.nextDouble();
			if(amount < 0) {
				System.out.println("A quantia não pode ser menor que zero.");
			}else if(amount > acctBalance) {
				System.out.printf("O valor não pode ser maior que o saldo\n"
						+ "seu saldo é de: %.2f\n", acctBalance);
			}
			
		}while(amount < 0 || amount > acctBalance);
		
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Tranferido para a conta %s",
				theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format("Tranferido para a conta %s",
				theUser.getAcctUUID(fromAcct)));
	}
	
	public static void showTransHistory(User theUser, Scanner input) {
		
		int theAcc = 0;
		
		do {
			
			System.out.printf("Digite o numero da conta 1)%d "+
			" escolha o numero da conta com as transações que você quer ver ",
			theUser.numAccounts());
		theAcc = input.nextInt() - 1;
		if(theAcc < 0 || theAcc >= theUser.numAccounts()) {
			System.out.println("Conta invalida por favor tente novamente!");
		}
			
		}while(theAcc < 0 || theAcc >= theUser.numAccounts());
		
		theUser.printAccTransHistory(theAcc);
		
	}
	
	public static void withdrawFounds(User theUser, Scanner input) {
		
		int fromAcct;
		double amount = 0;
		double acctBalance;
		String memo;
		
		do {
			System.out.printf("Digite o numero da sua conta (1-%d)\n"
					+ "para sacar um valor : ", theUser.numAccounts());
			fromAcct = input.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Conta invalida por favor tente novamente!");
			}
			
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBalance = theUser.getAcctBalance(fromAcct);
		
		do {
			System.out.printf("Digite o valor do saque seu saldo é %.2f : ", acctBalance);
			amount = input.nextDouble();
			if(amount < 0) {
				System.out.println("A quantia não pode ser menor que zero.");
			}else if(amount > acctBalance) {
				System.out.printf("O valor não pode ser maior que o saldo\n"
						+ "seu saldo é de: %.2f\n", acctBalance);
			}
			
		}while(amount < 0 || amount > acctBalance);
		
		input.nextLine();
		
		System.out.println("Digite o cpf ");
		memo = input.nextLine();
		
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
	}
	
	public static void depositFounds(User theUser, Scanner input) {
		
		int toAcct;
		double amount = 0;
		double acctBalance;
		String memo;
		
		do {
			System.out.printf("Digite o numero da sua conta (1-%d)\n"
					+ "para depositar em : ", theUser.numAccounts());
			toAcct = input.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Conta invalida por favor tente novamente!");
			}
			
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBalance = theUser.getAcctBalance(toAcct);
		
		do {
			System.out.printf("Digite o valor do deposito seu saldo atual é : (%.2f) : ", acctBalance);
			amount = input.nextDouble();
			if(amount < 0) {
				System.out.println("A quantia não pode ser menor que zero.");
			}
			
		}while(amount < 0);
		
		input.nextLine();
		
		System.out.println("Digite o cpf ");
		memo = input.nextLine();
		
		theUser.addAcctTransaction(toAcct, amount, memo);
	}

}
