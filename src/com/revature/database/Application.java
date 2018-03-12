package com.revature.database;

public class Application {
	
	private Customer customer;
	private Account account;
	
	public Application(Account acc, Customer cust) {
		account=acc;
		customer=cust;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Account getAccount() {
		return account;
	}
	
	
	
}
