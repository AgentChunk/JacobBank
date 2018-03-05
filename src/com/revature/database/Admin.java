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
	
	public Admin(String name, String  password) {
		super(name,password);
	}
	
	//Use this to create a new Admin
	//returns a new Admin and adds it to the bank
	public static Admin createAdmin(String name,String password) {
		Admin admin = new Admin(name,password);
		Bank.getAdmins().add(admin);
		return admin;
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
	
	@Override
	public String toString() {
		return "Admin [customers=" + this.getCustomers() + ", name=" + this.getName() +", password=" +this.getPassword()+ "]";
	}
	
	//Check if an admin with the name password combo exists
	public static boolean validLogin(String name, String password) {
		//go from the bank list and check if the password username combo exists
		for(Admin a:Bank.getAdmins()) {
			if(a.getName().equals(name) && a.getPassword().equals(password)) {
				return true;
			}
			
		}
		return false;
	}
	
	
	//returns the customer with the name password combo
	public static Admin getEmployee(String name, String password) {
		
		//go through the bank and find the customer with name and password combo
		for(Admin a:Bank.getAdmins()) {
			if(a.getName().equals(name) && a.getPassword().equals(password)) {
				return a;
			}
		}
		
		
		
		return null;
	}
}
