package com.revature.database;

import java.io.Serializable;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.revature.io.LoggingUtil;

//an account holds money
public class Account implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5869631646022235354L;
	
	private String uniqueID;
	private double balance;
	private boolean approved;
	private List<Customer> owners;
	
	public Account(){
		balance =0;
		approved =false;
		owners = new ArrayList<Customer>();
		uniqueID = UUID.randomUUID().toString();
	}
	
	public Account(double money) {
		balance = money;
		approved=false;
		owners = new ArrayList<Customer>();
		uniqueID = UUID.randomUUID().toString();
	}
	
	public String getUniqueID() {
		return uniqueID;
	}

	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	public List<Customer> getOwners() {
		return owners;
	}

	public void setOwners(List<Customer> owners) {
		this.owners = owners;
	}

	//adds to the balance of the account
	public void deposit(double add) throws IllegalArgumentException {
		if(add<0) {
			throw new IllegalArgumentException();
		}
		balance +=add;
	}
	
	public void withdraw(double subtract) throws IllegalArgumentException {
		if(subtract<0 || subtract>balance) {
			throw new IllegalArgumentException();
		} 
		balance -=subtract;
	}
	
	public void transfer(double transfer, Account recipient) throws IllegalArgumentException {
		if(transfer<0 || transfer>balance) {
			throw new IllegalArgumentException();
		}
		balance -=transfer;
		recipient.deposit(transfer);
		
	}

	@Override
	public String toString() {
		String string = "Account [uniqueID="+uniqueID +", balance=" + balance + ", approved=" + approved + ", owners= [";
		for(Customer o: owners) {
			String temp = o.getName()+ " ";
			string = string.concat(temp);
		}
		string = string.concat("]]");
		return string;
	}

	public boolean isApproved() {
		return approved;
	}

	//only when account is approved give the account it's id
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	//gets amount to deposit
	public static double scanDeposit(Account acc, Scanner scan) {
		double deposit;
		do {
			System.out.println("Enter amount to deposit :");
			deposit = scan.nextDouble();
			if(deposit<0) {
				System.out.println("Invalid amount");
			}
		}while(deposit<0);
		
		return deposit;
	}
	
	public static double scanWithdraw(Account acc, Scanner scan) {
		double withdraw;
		do {
			System.out.println("Enter amount to withdraw :");
			withdraw = scan.nextDouble();
			if(withdraw<0 || withdraw>acc.getBalance()) {
				System.out.println("Invalid amount");
			}
		}while(withdraw<0 || withdraw>acc.getBalance());
		return withdraw;
	}
	
	public static Pair<Double,Account> scanTransfer(Account acc, Scanner scan){
		String id;
		double transfer;
		do {
			System.out.println("Enter amount to transfer :");
			transfer = scan.nextDouble();
			scan.nextLine();
			if(transfer<0 || transfer>acc.getBalance()) {
				System.out.println("Invalid amount");
			}
		}while(transfer<0 || transfer>acc.getBalance());
		do {
			System.out.println("Enter id of account to transfer too :");
			
			id = scan.nextLine();
			if(!Bank.bankHasAccountId(id,Bank.getAccounts())) {
				System.out.println("Invalid account id");
			}
		}while(!Bank.bankHasAccountId(id,Bank.getAccounts()));
		Account acc1 = Bank.accountWithId(id, Bank.getAccounts());
		return new Pair<Double, Account>(transfer,acc1);
	}
}
