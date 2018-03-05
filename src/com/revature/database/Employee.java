package com.revature.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Employees can view their customers data including account info
 * account balances
 * personal info
 * They also approve or deny accounts
 *   
 */
public class Employee implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7231838518518177640L;
	private Set<Customer> customers;
	
	private String name;
	
	
	public Employee(){
		customers = new HashSet<Customer>();
		
		name = "";
		
	}
	
	public Employee(String name){
		customers = new HashSet<Customer>();
		
		this.name = name;
		
	}
	
	
	public Employee(String name, HashSet<Customer> customers) {
		this.customers = customers;
		this.name = name;
		
		
	}
	
	
	//approve or deny requests
	//if approved put the account into the customers accounts list
	//if approved put the customer into the accounts customer list
	//add the customer to the employees customers if not there already
	public void processRequest(boolean boo,Account acc) throws IllegalArgumentException {
		//if the account isn't in the bank's applications
		if(!Bank.getApplications().containsKey(acc)) {
			throw new IllegalArgumentException();
		}
		
		Customer processing = Bank.getApplications().get(acc);
		customers.add(processing);
		acc.setApproved(boo);
		
		if(boo) {
			//if approved add to the customers account and the Bank's accounts
			
			processing.getAccounts().add(acc);
			acc.getOwners().add(processing);
			Bank.getAccounts().add(acc);
		} 
		//remove from applications after being processsed
		Bank.getApplications().remove(acc);
		
	}
	
	public void processJointRequest(boolean boo,Account acc) throws IllegalArgumentException{
		//If the account isn't in the bank or the jointApplication throw an exception
		if(!Bank.getJointApplications().containsKey(acc) || !Bank.getAccounts().contains(acc) ) {
			throw new IllegalArgumentException();
		}
		
		Customer processing = Bank.getJointApplications().get(acc);
		customers.add(processing);
		
		//If approved add to the customers account
		if(boo) {
			processing.getAccounts().add(acc);
			acc.getOwners().add(processing);
		}
		//remove from applications after being processed
		Bank.getJointApplications().remove(acc);
		
	}
	
	//Employees should be able to get their customers account info
	public List<Account> getCustomerAccount(Customer cust) throws IllegalArgumentException {
		if(!customers.contains(cust)) {
			throw new IllegalArgumentException();
		}
		
		return cust.getAccounts();
	}
	
	
	//Employees should be able to get their customers info
		public void printCustomers() {
		for(Customer c: customers) {
			System.out.println(c.toString());
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "Employee [customers=" + customers + ", name=" + name + "]";
	}

	


}
