package com.revature.database;

import java.util.Set;

/**
 * 
 * @author Jacob Lathrop
 * Admins are a special type of employee that have access to accounts in the Bank
 *
 */
public class Admin extends Employee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3228204036171601179L;
	
	public Admin() {
		super();
	}
	
	public Admin(String name){
		super(name);
	}
	
	public void cancelAccount(Account account) {
		Bank.getAccounts().remove(account);
	}
	
	public void printAccounts() {
		Set<Account> accounts = Bank.getAccounts();
		for(Account a: accounts) {
			System.out.println(a.toString());
		}
	}
	
	public void printCustomers() {
		Set<Customer> customer = Bank.getCustomers();
		for(Customer c: customer) {
			System.out.println(c.toString());
		}
	}
	
	public void withdraw(double amount,Account acc) throws IllegalArgumentException {
		acc.withdraw(amount);
	}
	
	public void deposit(double amount, Account acc) throws IllegalArgumentException {
		acc.deposit(amount);
	}
	
	public void transfer(double amount, Account a1, Account a2) throws IllegalArgumentException {
		a1.transfer(amount,a2);
	}
}
