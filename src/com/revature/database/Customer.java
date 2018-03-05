package com.revature.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Customers register with a username and password and can apply for an account
 */
public class Customer implements Serializable{

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
	
	//Use this to make new customers
	//Creates a customer and adds them to the Bank
	public static Customer createCustomer(String name, String password) {
		Customer customer = new Customer(name,password);
		Bank.getCustomers().add(customer);
		return customer;
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
	public void deposit(double add, Account acc) throws IllegalArgumentException {
		if(acc.isApproved() && accounts.contains(acc)) {
			acc.deposit(add);
		}
	}
	
	//subtracts subtract if account is approved
	public void withdraw(double subtract, Account acc) throws IllegalArgumentException {
		if(acc.isApproved()) {
			acc.withdraw(subtract);
		}
	}
	
	//transfers transfer from a1 to a2 if both accounts are approved
	public void transfer(double transfer, Account a1, Account a2) throws IllegalArgumentException {
		if(a1.isApproved() && a2.isApproved()) {
			a1.transfer(transfer, a2);
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

	//Checks if a customer with name and password exists
	public static boolean validLogin(String name, String password) {
		//go from the bank list and check if the password username combo exists
		for(Customer c:Bank.getCustomers()) {
			if(c.name.equals(name) && c.password.equals(password)) {
				return true;
			}
			
		}
		return false;
	}
	
	
	//returns the customer with the name password combo
	public static Customer getCustomer(String name, String password) {
		
		//go through the bank and find the customer with name and password combo
		for(Customer c:Bank.getCustomers()) {
			if(c.name.equals(name) && c.password.equals(password)) {
				return c;
			}
		}
		
		return null;
	}
	
	
	
}
