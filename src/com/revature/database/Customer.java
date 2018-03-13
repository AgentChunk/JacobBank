package com.revature.database;

import java.io.Serializable;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.dao.BankDao;
import com.revature.dao.BankDaoImp;
import com.revature.io.LoggingUtil;

/*
 * Customers register with a username and password and can apply for an account
 */
public class Customer implements Serializable, Login{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7900604410002075094L;
	private int id;
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
	
	public Customer(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.accounts = new ArrayList<Account>();
	}
	
	//Use this to make new customers
	//Creates a customer and adds them to the Bank
	public static Customer createCustomer(String name, String password) {
		Customer customer = new Customer(name,password);
		BankDao bd = new BankDaoImp();
		//create the user in the database and retrieve its id
		int id=bd.createUserPreparedStmt(customer);
		customer.id=id;
		
		//add the customer to the bank
		Bank.getCustomers().add(customer);
		return customer;
	}


	public static Customer createCustomer(int id, String name, String password) {
		
		Customer customer = new Customer(id,name,password);
		Bank.getCustomers().add(customer);
		return customer;
	}
	
	
	
	//apply for an account
	public Account applyForAccount() {
		BankDao bd = new BankDaoImp();
		Account toApprove = new Account();
		Application app = new Application(toApprove,this);
		//create the account in the table and get it's generate id
		int id=bd.createAccountPreparedStmt(toApprove);
		toApprove.setID(id);
		
		//add the application to the bank
		Bank.addApplication(app);
		Bank.addAccount(toApprove);
		
		//add the application to the database
		bd.createAppPreparedStmt(app);
		
		return toApprove;
		
	}
	
	//apply for a joint account already opened by another person
	//The account need to already have been approved for another person
	public void applyForJointAccount(Account joint) throws IllegalArgumentException{
		//If the Bank doesn't have the account requested throw and exception
		if(!Bank.getAccounts().contains(joint)) {
			throw new IllegalArgumentException();
		}
		BankDao bd = new BankDaoImp();
		Application app = new Application(joint,this);
		//add the app to the database
		bd.createAppPreparedStmt(app);
		//add app to the bank
		Bank.addApplication(app);
	}
	
	//adds add to account if account is approved
	public void deposit(double add, Account acc) throws IllegalArgumentException {
		if(acc.isApproved() && accounts.contains(acc)) {
			BankDao bd = new BankDaoImp();
			bd.deposit(acc.getID(), add);
			acc.deposit(add);
			LoggingUtil.logTrace("Customer "+name+" deposited "+ add+" to account "+acc.getID());
		}
	}
	
	//subtracts subtract if account is approved
	public void withdraw(double subtract, Account acc) throws IllegalArgumentException {
		if(acc.isApproved()) {
			BankDao bd = new BankDaoImp();
			bd.withdraw(acc.getID(), subtract);
			acc.withdraw(subtract);
			LoggingUtil.logTrace("Customer "+name+" deposited "+ subtract +" to account "+acc.getID());
		}
	}
	
	//transfers transfer from a1 to a2 if both accounts are approved
	public void transfer(double transfer, Account a1, Account a2) throws IllegalArgumentException {
		if(a1.isApproved() && a2.isApproved()) {
			BankDao bd = new BankDaoImp();
			bd.transfer(a1.getID(), a2.getID(),transfer);
			a1.transfer(transfer, a2);
			LoggingUtil.logTrace("Customer "+name+" transfered "+ transfer+" from account "+ a1.getID()+" to account "+a2.getID());
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

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", password=" + password + ", accounts=" + accounts + "]";
	}

	//Checks if a customer with name and password exists
	public boolean validLogin(String name, String password) {
		//go from the bank list and check if the password username combo exists
		for(Customer c:Bank.getCustomers()) {
			if(c.name.equals(name) && c.password.equals(password)) {
				return true;
			}
			
		}
		return false;
	}
	
	
	//returns the customer with the name password combo
	public Customer login(String name, String password) {
		
		//go through the bank and find the customer with name and password combo
		for(Customer c:Bank.getCustomers()) {
			if(c.name.equals(name) && c.password.equals(password)) {
				return c;
			}
		}
		
		return null;
	}
	
	
	
	//this is run when the user logs on
	public void runLoggedOn(Scanner scan) {
		
		char cmd;
		boolean loggedOn =true;		
		
		LoggingUtil.logTrace("Customer " + name + " logged on");
		
		System.out.println("Hello " + name + ", these are you current accounts and their balances");
		for(int i=0;i<accounts.size();i++) {
			System.out.println("Account :"+ accounts.get(i).getID()+ " Balance :" + accounts.get(i).getBalance());
		}
		while(loggedOn) {
			System.out.println("Press A to apply for an account, J to apply for a joint account, S to select an account, or Q to logout");
			cmd = scan.next().charAt(0);
			scan.nextLine();
			switch(cmd){
				//User chose apply for account
				case 'A':
					applyForAccount();
					LoggingUtil.logTrace("Customer "+ name +" applied for an account");
					System.out.println("Your application has been submited");
					break;
				//User chose apply for joint account	
				case 'J':
					int id=0;
					
					do {
						System.out.println("Enter the account id of the account applying for");
						id = scan.nextInt();
						if(!Bank.bankHasAccountId(id,Bank.getAccounts())) {
							System.out.println("Invalid ID");
						}
					}while(!Bank.bankHasAccountId(id,Bank.getAccounts()));
					
					Account apply = Bank.accountWithId(id,Bank.getAccounts());
					this.applyForJointAccount(apply);
					LoggingUtil.logTrace("Customer "+ name +" applied for jointAccount with id "+ id );
					System.out.println("Your joint appliaction has been submitted");
					break;
				
				//User chose to select an account	
				case 'S':
					selectAccount(scan);
					break;
				case 'Q':
					loggedOn = false;
					break;
			
				default:
					System.out.println("Invalid command");
			}		
		}
	}
	
	private void selectAccount(Scanner scan) {
		boolean acc =true;
		char cmd;
		
		System.out.println("Enter the id of the account you wish to select :");
		
		int id = scan.nextInt();
		boolean exists = false;
		Account a1 = null;
		for(Account a: accounts) {
			if(a.getID()==id) {
				exists = true;
				a1=a;
			}
		}
		
		if(exists) {
			//Once account is found it is printed
			System.out.println("Account "+ id +" selected");
			System.out.println(a1);
			//Then the user is asked what they want to do with the account
			
			
			while(acc) {
				System.out.println("Enter D to deposit, W to withdraw, T to transfer, or Q to go back");
				cmd = scan.next().charAt(0);
				scan.nextLine();
				
				switch(cmd) {
					case 'D':
						double deposit = Account.scanDeposit(a1, scan);
						deposit(deposit, a1);
						System.out.println("Succefully deposited. New balance is :"+ String.format("%.2f",a1.getBalance()));
						break;
						
					case 'W':
						double withdraw= Account.scanWithdraw(a1,scan);
						withdraw(withdraw,a1);				
						System.out.println("Succefully withdrew. New balance is :"+String.format("%.2f",a1.getBalance()));
						break;
						
					case 'T':
						Pair<Double,Account> transfer = Account.scanTransfer(a1, scan);
						transfer(transfer.getKey(), a1, transfer.getValue());
						System.out.println("Succefully transfered. New balance is :"+ String.format("%.2f",a1.getBalance()));
						break;
					
					case 'Q':		
						acc = false;
						break;
					default:
						System.out.println("Invalid Command");
				}
			}
			
		}else {
			System.out.println("Invalid id");
			LoggingUtil.logWarn("User attempted to access invalid id " +id);
		}
	}
	
	
	
	
	
}
