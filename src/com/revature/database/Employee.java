package com.revature.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.revature.dao.BankDao;
import com.revature.dao.BankDaoImp;
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
	private int id;
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
	
	public Employee(int id, String name, String password) {
		customers = new HashSet<Customer>();
		this.id =id;
		this.name = name;
		this.password = password;
	}
	
	
	
	//Use this to create new Employees
	//returns an employee and adds them to the bank
	public static Employee createEmployee(String name, String password) {
		BankDao bd = new BankDaoImp();
		Employee employee = new Employee(name,password);
		
		//gets the database generated id and adds the employee to the user table
		int id = bd.createUserPreparedStmt(employee);
		employee.id=id;
		
		//add employee to bank employees
		Bank.getEmployees().add(employee);
		return employee;
	}
	
	//Use this to create employees already in the database
	//returns an employee and adds them to the bank
	public static Employee createEmployee(int id,String name, String password) {
		Employee employee = new Employee(id,name,password);
		
		//add employee to bank employees
		Bank.getEmployees().add(employee);
		return employee;
	}	
	
	//approve or deny requests
	//if approved put the account into the customers accounts list
	//if approved put the customer into the accounts customer list
	//add the customer to the employees customers if not there already
	public void processRequest(boolean boo,Application app) throws IllegalArgumentException {
		//if the account isn't in the bank's applications
		if(!Bank.getApplications().contains(app)) {
			throw new IllegalArgumentException();
		}
		BankDao bd = new BankDaoImp();
		Account acc = app.getAccount();
		Customer processing = app.getCustomer();
		customers.add(processing);
		
		//if the account isn't approved this is a new application
		if(!acc.isApproved()) {
			//set approved to the new value
			acc.setApproved(boo);
			//update the account
			bd.updateAccount(acc);
		}
			
		if(boo) {
			//if approved add to the customers account and the Bank's accounts
			
			processing.getAccounts().add(acc);
			acc.getOwners().add(processing);
			// add row to composite table
			bd.createAccountUserRow(acc.getID(), processing.getId());
			LoggingUtil.logTrace("Account application "+acc.getID()+" approved by "+name);
		} else if(!acc.isApproved()) {
			//if the application is not approved and the account hasn't already been approved
			//delete the account
			bd.deleteAccount(acc.getID());
			Bank.getAccounts().remove(acc);
		}
		//remove from applications after being processsed
		Bank.getApplications().remove(app);
		bd.deleteApplication(app);
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
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
			System.out.println("Press V to view your customers, A to access account applications, or Q to logout");
			cmd = scan.next().charAt(0);
			scan.nextLine();
			
			switch(cmd) {
				case 'V':
					viewCustomers();
					break;
				case 'A':
					accessAccountApplications(scan);
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
		for(Customer c: Bank.getCustomers()) {
			System.out.println(c.toString());
		}
	}
	
	//UI for accessing and processing applications
	public void accessAccountApplications(Scanner scan) {
		//print out all of the applications
		
		List<Application> apps = Bank.getApplications();
		int id=-1;
		
		if(!Bank.getApplications().isEmpty()) {
				System.out.println("Outputing account applications...");
			for(int i=0;i<apps.size();i++) {
				System.out.println(i +": "+ apps.get(i).getAccount().toString() +"----"+apps.get(i).getCustomer().getName());
			}
			while(id>= apps.size() || id<0) {
				System.out.println("Enter application number to approve or deny request");
				id = scan.nextInt();
				LoggingUtil.logDebug(""+id);
				if(id>=apps.size()) System.out.println("Invalid id");
			}
			Application processing = apps.get(id);
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


}
