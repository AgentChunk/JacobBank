package com.revature.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Customers register with a username and password and can apply for an account
 */
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7900604410002075094L;
	private String name;
	private String password;
	private List<Account> accounts;
	
	public Customer() {
		name="";
		password="";
		accounts = new ArrayList<Account>();
	}
	
	//register
	public Customer(String name, String password) {
		this.name=name;
		this.password=password;
		this.accounts = new ArrayList<Account>();
		
	}
	
	//apply for an account
	public Account applyForAccount() {
		
		Account toApprove = new Account();
		//add the application to the bank
		Bank.addApplication(toApprove,this);
	
		return toApprove;
		
	}
	
	//apply for a joint account already opened by another person
	//The account need to already have been approved for another person
	public void applyForJointAccount(Account joint) throws IllegalArgumentException{
		//If the Bank doesn't have the account requested throw and exception
		if(!Bank.getAccounts().contains(joint)) {
			throw new IllegalArgumentException();
		}
		Bank.addJointApplication(joint, this);
	}
	
	//adds add to account if account is approved
	public void deposit(double add, int index) throws IllegalArgumentException {
		if(accounts.get(index).isApproved()) {
			accounts.get(index).deposit(add);
		}
	}
	
	//subtracts subtract if account is approved
	public void withdraw(double subtract, int index) throws IllegalArgumentException {
		if(accounts.get(index).isApproved()) {
			accounts.get(index).withdraw(subtract);
		}
	}
	
	//transfers transfer from a1 to a2 if both accounts are approved
	public void transfer(double transfer, int a1, int a2) throws IllegalArgumentException {
		if(accounts.get(a1).isApproved() && accounts.get(a2).isApproved()) {
			accounts.get(a1).transfer(transfer, accounts.get(a2));
		}
	}
	
	
	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", password=" + password + ", accounts=" + accounts + "]";
	}
	
	
	
}
