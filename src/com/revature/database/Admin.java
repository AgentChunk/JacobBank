package com.revature.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.revature.io.LoggingUtil;

import javafx.util.Pair;

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
	
	
	public Admin(String name, String  password) {
		super(name,password);
	}
	
	public Admin(int id, String name, String password) {
		super(id,name,password);
	}
	
	//Use this to create a new Admin
	//returns a new Admin and adds it to the bank
	public static Admin createAdmin(String name,String password) {
		Admin admin = new Admin(Bank.newUserId(0),name,password);
		Bank.getAdmins().add(admin);
		return admin;
	}
	
	//Throws exception if the id is already being used in the bank. 
	public static Admin createAdmin(int id, String name, String password) throws IllegalArgumentException {
		if(!Bank.validUserId(id)) throw new IllegalArgumentException();
		Admin admin = new Admin(Bank.newUserId(id),name,password);
		Bank.getAdmins().add(admin);
		return admin;
	}
	
	
	public void cancelAccount(Account account) {
		Bank.getAccounts().remove(account);
		List<Customer> customers = account.getOwners();
		for(Customer c: customers){
			c.getAccounts().remove(account);
		}
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
		LoggingUtil.logTrace("Admin "+getName()+" deposited "+ amount+" to account "+acc.getID());
	}
	
	public void deposit(double amount, Account acc) throws IllegalArgumentException {
		acc.deposit(amount);
		LoggingUtil.logTrace("Admin "+getName()+" deposited "+ amount +" to account "+ acc.getID());
	}
	
	public void transfer(double amount, Account a1, Account a2) throws IllegalArgumentException {
		a1.transfer(amount,a2);
		LoggingUtil.logTrace("Admin "+getName()+" transfered "+ amount+" from account "+ a1.getID()+" to account "+a2.getID());
	}
	
	@Override
	public String toString() {
		return "Admin [customers=" + this.getCustomers() + ", name=" + this.getName() +", password=" +this.getPassword()+ "]";
	}
	
	//Check if an admin with the name password combo exists
	public boolean validLogin(String name, String password) {
		//go from the bank list and check if the password username combo exists
		for(Admin a:Bank.getAdmins()) {
			if(a.getName().equals(name) && a.getPassword().equals(password)) {
				return true;
			}
			
		}
		return false;
	}
	
	
	//returns the customer with the name password combo
	public Admin login(String name, String password) {
		
		//go through the bank and find the customer with name and password combo
		for(Admin a:Bank.getAdmins()) {
			if(a.getName().equals(name) && a.getPassword().equals(password)) {
				return a;
			}
		}
		
		return null;
	}
	
	//Runs the UI for when the user logs on
	public void runLoggedOn(Scanner scan) {
		Map<Character,Runnable> commands = new HashMap<Character,Runnable>();
		char cmd;
		boolean loggedOn =true;
		//Fill the map with runnable commands
		commands.put('V', ()-> viewCustomers());
		commands.put('A', ()-> viewAllAccounts());
		commands.put('P', ()-> accessAccountApplications(scan));
		
		LoggingUtil.logTrace("Admin "+ this.getName()+ " logged on");
		System.out.println("Hello "+ this.getName() );
		
		while(loggedOn) {
			System.out.println("Press V to view your customers, A to access all accounts, P to access account applications, or Q to logout");
			cmd = scan.next().charAt(0);
			scan.nextLine();
			if("VAPJQ".indexOf(cmd)<0) {
	        	System.out.println("Invalid command");
	        }
	        else if(cmd== 'Q') {
	        	loggedOn = false;
	        }else if(cmd =='A') {
	        	commands.get(cmd).run();
	        	selectAccount(scan);
	        }else
	        	commands.get(cmd).run();
		}
		
	}


	public void viewAllAccounts() {
		System.out.println("Outputing all bank accounts...");
		for(Account a:Bank.getAccounts()) {
			System.out.println(a.toString());
		}	
	}
	
	public void selectAccount(Scanner scan) {
	
		boolean acc =true;
		char cmd;
		int id;
		do {
			System.out.println("Enter the id of the account you wish to select or 0 to go back :");
			id = scan.nextInt();
			if(!Bank.bankHasAccountId(id,Bank.getAccounts()) && id!=0) {
				System.out.println("Invalid account id");
			}
		}while(!Bank.bankHasAccountId(id,Bank.getAccounts()) && id!=0);
		if(id!=0) {
			Account account = Bank.accountWithId(id,Bank.getAccounts());
			
			System.out.println("Account "+ id +" selected");
			System.out.println(account);
			
			
			while(acc) {
				System.out.println("Enter D to deposit, W to withdraw, T to transfer, C to cancel acccount, or Q to go back");
				cmd = scan.next().charAt(0);
				scan.nextLine();
				
				switch(cmd) {
					case 'D':
						double deposit = Account.scanDeposit(account, scan);
						deposit(deposit, account);
						System.out.println("Succefully deposited. New balance is :"+String.format("%.2f",account.getBalance()));
						break;
						
					case 'W':
						double withdraw= Account.scanWithdraw(account,scan);
						withdraw(withdraw,account);				
						System.out.println("Succefully withdrew. New balance is :"+String.format("%.2f",account.getBalance()));
						break;
						
					case 'T':
						Pair<Double,Account> transfer = Account.scanTransfer(account, scan);
						transfer(transfer.getKey(), account, transfer.getValue());
						System.out.println("Succefully withdrew. New balance is :"+ String.format("%.2f",account.getBalance()));
						break;
					
					case 'Q':		
						acc = false;
						break;
					case 'C':
						runCancelAccount(account,scan);
						acc = false;
						break;
					default:
						System.out.println("Invalid Command");
				}
				
			}
		}	
	}
	
	public void runCancelAccount(Account account,Scanner scan) {
		cancelAccount(account);
		System.out.println("Account canceled");
		LoggingUtil.logTrace("Account "+ account.getID() +" terminated " );
	}
}
