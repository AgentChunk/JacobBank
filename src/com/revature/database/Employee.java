package com.revature.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.revature.io.LoggingUtil;


/**
 * Employees can view their customers data including account info
 * account balances
 * personal info
 * They also approve or deny accounts
 *   
 */
public class Employee implements Serializable, Login {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7231838518518177640L;
	private Set<Customer> customers;
	
	private String name;
	private String password;
	
	
	public Employee(){
		customers = new HashSet<Customer>();
		password ="";
		name = "";
		
	}
	
	
	public Employee(String name,String password) {
		customers = new HashSet<Customer>();
		this.name = name;
		this.password = password;
	}
	
	
	
	//Use this to create new Employees
	//returns an employee and adds them to the bank
	public static Employee createEmployee(String name, String password) {
		Employee employee = new Employee(name,password);
		Bank.getEmployees().add(employee);
		return employee;
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
			LoggingUtil.logTrace("Account application "+acc.getUniqueID()+" approved by "+name);
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
			LoggingUtil.logTrace("Joint application "+acc.getUniqueID()+" approved by "+name);
		}
		//remove from applications after being processed
		Bank.getJointApplications().remove(acc);
		
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	@Override
	public String toString() {
		return "Employee [customers=" + customers + ", name=" + name + ", password=" + password + "]";
	}

	//Checks if an employee with name and password exists
	public boolean validLogin(String name, String password) {
		//go from the bank list and check if the password username combo exists
		for(Employee c:Bank.getEmployees()) {
			if(c.name.equals(name) && c.password.equals(password)) {
				return true;
			}
			
		}
		return false;
	}
	

	@Override
	public Employee login(String name, String password) {
		
		//go through the bank and find the customer with name and password combo
		for(Employee e:Bank.getEmployees()) {
			if(e.name.equals(name) && e.password.equals(password)) {
				return e;
			}
		}
		
		return null;
	}


	@Override
	public void runLoggedOn(Scanner scan) {

		char cmd;
		boolean loggedOn =true;
		
		LoggingUtil.logTrace("Employee "+ name+ " logged on");
		System.out.println("Hello "+ name );
		
		while(loggedOn) {
			System.out.println("Press V to view your customers, A to access account applications, J to access joint account applications, or Q to logout");
			cmd = scan.next().charAt(0);
			scan.nextLine();
			
			switch(cmd) {
				case 'V':
					viewCustomers();
					break;
				case 'A':
					accessAccountApplications(scan);
					break;
				case 'J':
					accessJointApplicatons(scan);
					break;
				case 'Q':
					loggedOn =false;
					break;
				default:
					System.out.println("Invalid command");
			}

		}
		
	}
	
	public void viewCustomers() {
		//print out all of the employees customers and their info
		System.out.println("Outputing employee's customers...");
		for(Customer c: customers) {
			System.out.println(c.toString());
		}
	}
	
	//UI for accessing and processing applications
	public void accessAccountApplications(Scanner scan) {
		//print out all of the applications
		
		Set<Account> apps = Bank.getApplications().keySet();
		String id="";
		
		if(!Bank.getApplications().isEmpty()) {
				System.out.println("Outputing account applications...");
			for(Account a:apps) {
				System.out.println(a.toString() +"----"+Bank.getApplications().get(a).toString());
			}
			while(!Bank.bankHasAccountId(id,Bank.getApplications().keySet())) {
				System.out.println("Enter an account id to approve or deny an account");
				id = scan.nextLine();
				LoggingUtil.logDebug(id);
				if(!Bank.bankHasAccountId(id,Bank.getApplications().keySet())) System.out.println("Invalid id");
			}
			Account processing = Bank.accountWithId(id,Bank.getApplications().keySet());
			char approve;
			do{
				System.out.println("Approve? (Y/N)");
				approve = scan.next().charAt(0);
				scan.nextLine();
				if(approve!='Y' && approve!='N') {
					System.out.println("Invalid input");
				}
				
			}while(approve!='Y' && approve!='N');
			
			if(approve=='Y') {
				processRequest(true, processing);
			}else if(approve =='N') {
				processRequest(false, processing);
			}
		}
		else {
			System.out.println("Applications is empty");
		}
	}

	//UI for accessing and processing joint applications
	public void accessJointApplicatons(Scanner scan) {
		//print out all of the joint applications
		System.out.println("Outputing joint account applications...");
		Set<Account> apps = Bank.getJointApplications().keySet();
		String id;
		
		if(!Bank.getJointApplications().isEmpty()) {
		
			for(Account a:apps) {
				System.out.println(a.toString() +"----"+Bank.getJointApplications().get(a).toString());
			}
			do {
				System.out.println("Enter an account id to approve or deny an account");
				id = scan.nextLine();
				LoggingUtil.logDebug(id);
				if(!Bank.bankHasAccountId(id,Bank.getJointApplications().keySet())) System.out.println("Invalid id");
			}while(!Bank.bankHasAccountId(id,Bank.getJointApplications().keySet()));
			Account processing = Bank.accountWithId(id,Bank.getJointApplications().keySet());
			char approve;
			do{
				System.out.println("Approve? (Y/N)");
				approve = scan.next().charAt(0);
				scan.nextLine();
				if(approve!='Y' && approve!='N') {
					System.out.println("Invalid input");
				}
				
			}while(approve!='Y' && approve!='N');
			
			if(approve=='Y') {
				processJointRequest(true, processing);
			}else if(approve =='N') {
				processJointRequest(false, processing);
			}
		}else {
			System.out.println("Joint Applications is empty");
		}
	}

}
