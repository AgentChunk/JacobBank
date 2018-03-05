package com.revature.driver;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.database.Account;
import com.revature.database.Admin;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.database.Employee;
import com.revature.io.LoggingUtil;
import com.revature.io.Serializer;

public class Driver {
	
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		Map<Character,Runnable> commands = new HashMap<Character,Runnable>();
		boolean run = true;
		Serializer serialize = new Serializer();
	
		LoggingUtil.logTrace("Reading in file");
		serialize.writeIn("database.txt");
		
		commands.put('C', () -> runCustomer(scan));
		commands.put('E', () -> runEmployee());
		commands.put('A', () -> runAdmin());
		commands.put('Q', () -> quit());
		
		/*
		Customer Chris = Customer.createCustomer("Nick","pass");
		Customer Jack = Customer.createCustomer("Jack","1234");
		Customer Sam = Customer.createCustomer("Sam","passcode");
		Employee Nick = Employee.createEmployee("Nick","1234");
		Admin Jake = Admin.createAdmin("Jake","54321");
		Account a1 =Chris.applyForAccount();
		Account a2 =Jack.applyForAccount();
		Account a3 =Sam.applyForAccount();
		Nick.processRequest(true, a1);
		Nick.processRequest(true, a2);
		Jake.processRequest(true, a3);
		*/
		
		while (run){
			System.out.println("Chose if you are a customer, employee, or admin");
			System.out.println("Input C for customer, E for employee, or A for admin, or Q to quit");
			
	
	        // Invoke some command
	        char cmd = scan.next().charAt(0);
	        if("CEAQ".indexOf(cmd)<0) {
	        	System.out.println("Invalid command");
	        }
	        else if(cmd== 'Q') {
	        	run = quit();
	        }else
	        	commands.get(cmd).run();
		
		}
		
		Admin tester = new Admin();
		
		
		
		for(Account a:Bank.getAccounts()) {
			LoggingUtil.logTrace(a.toString());
		}
		for(Customer c:Bank.getCustomers()) {
			LoggingUtil.logTrace(c.toString());
		}
		for(Employee e:Bank.getEmployees()) {
			LoggingUtil.logTrace(e.toString());
		}
		for(Admin a:Bank.getAdmins()) {
			LoggingUtil.logTrace(a.toString());
		}
		
		serialize.writeOut("database.txt");
	}
	
	private static void runCustomer(Scanner scan) {
		
		//Customer logs in or registers a new account
		System.out.println("Press L to login or R to register a  new account: ");
		char cmd = scan.next().charAt(0);
		
		//If they log in
		if(cmd =='L') {
			System.out.print("Enter your customer username : ");
			String user = scan.next();
			System.out.print("Enter your password : ");
			String pass = scan.next();
			if(Customer.validLogin(user, pass)) {
				Customer customer = Customer.getCustomer(user, pass);
				runCustomerLoggedOn(customer,scan);
			}else {
				System.out.println("Invalid username or password");
			}
		}
		else if(cmd=='R') {
			
			System.out.print("Enter your customer username : ");
			String user = scan.next();
			System.out.print("Enter your password : ");
			String pass = scan.next();
			Customer customer = Customer.createCustomer(user, pass);
			LoggingUtil.logTrace("New customer created "+ customer);
			System.out.println("Hello "+user+", your new account has been created");
			runCustomerLoggedOn(customer,scan);
			
		}else {
			System.out.println("Invalid command");
		}
		
	}
	
	
	private static void runEmployee() {
		
	}
	
	private static void runAdmin() {
		
	}
	
	private static boolean quit() {
		return false;
	}
	
	//applies for an account
	private static void applyForAccount(Customer cust) {
		cust.applyForAccount();
		LoggingUtil.logTrace("Customer "+ cust.getName() +" applied for an account");
		System.out.println("Your application has been submited");
	}
	
	//applies for a joint account
	private static void applyForJointAccount(Customer cust, Scanner scan) {
		System.out.println("Enter the account id of the account applying for");
		int id = scan.nextInt();
		boolean exists = false;
		for(Account a:Bank.getAccounts()) {
			if(a.getId()==id) {
				exists = true;
				cust.applyForJointAccount(a);
				LoggingUtil.logTrace("Customer "+ cust.getName()+" applied for jointAccount with id "+ id );
				System.out.println("Your joint appliaction has been submitted");
			}
		}
		if(!exists) {
			LoggingUtil.logWarn("Customer "+ cust.getName()+" attempted to apply for jointAccount with invalid id "+ id );
			System.out.println("Invalid account id");
		}
		
	}
	
	private static void selectAccount(Customer cust, Scanner scan) {
		Map<Character,Runnable> commands = new HashMap<Character,Runnable>();
		boolean acc =true;
		char cmd;
		
		
		System.out.println("Enter the id of the account you wish to select :");
		int id = scan.nextInt();
		boolean exists = false;
		Account a1 = null;
		for(Account a: cust.getAccounts()) {
			if(a.getId()==id) {
				exists = true;
				a1=a;
			}
		}
		
		final Account account =a1;
		commands.put('D', ()->runDeposit(cust,account,scan));
		commands.put('W', ()->runWithdraw(cust,account,scan));
		commands.put('T', ()->runTransfer(cust,account,scan));
		commands.put('Q', ()->quit());
		if(exists) {
			//Once account is found it is printed
			System.out.println("Account "+ id +" selected");
			System.out.println(a1);
			//Then the user is asked what they want to do with the account
			
			
			while(acc) {
				System.out.println("Enter D to deposit, W to withdraw, T to transfer, or Q to go back");
				cmd = scan.next().charAt(0);
				if("DWTQ".indexOf(cmd)<0) {
					System.out.println("Invalid Command");
				}else if(cmd=='Q') {
					acc =false;
				}else {
					
					commands.get(cmd).run();
				}
			}
			
		}else {
			System.out.println("Invalid id");
			LoggingUtil.logWarn("User attempted to access invalid id " +id);
		}
		
	}
	
	//after customer has logged on or created a account run this
	private static void runCustomerLoggedOn(Customer customer, Scanner scan) {
		// map of runnable commands
		Map<Character,Runnable> commands = new HashMap<Character,Runnable>();
		char cmd;
		boolean loggedOn =true;
		//Fill the map with runnable commands
		commands.put('A', ()-> applyForAccount(customer));
		commands.put('J', ()-> applyForJointAccount(customer, scan));
		commands.put('S', ()-> selectAccount(customer, scan));
		commands.put('Q', ()-> quit());
		
		LoggingUtil.logTrace("User " + customer.getName() + " logged on");
		String user = customer.getName();
		System.out.println("Hello " + user + ", these are you current accounts and their balances");
		for(int i=0;i<customer.getAccounts().size();i++) {
			System.out.println("Account :"+ customer.getAccounts().get(i).getId()+ " Balance :" +customer.getAccounts().get(i).getBalance());
		}
		while(loggedOn) {
			System.out.println("Press A to apply for an account, J to apply for a joint account, S to select an account, or Q to logout");
			cmd = scan.next().charAt(0);
			if("AJSQ".indexOf(cmd)<0) {
	        	System.out.println("Invalid command");
	        }
	        else if(cmd== 'Q') {
	        	loggedOn = quit();
	        }else
	        	commands.get(cmd).run();
		}	
	}
	//deposits too the customers account
	private static void runDeposit(Customer cust,Account acc, Scanner scan) {
		double deposit;
		do {
			System.out.println("Enter amount to deposit :");
			deposit = scan.nextDouble();
			if(deposit<0) {
				System.out.println("Invalid amount");
			}
		}while(deposit<0);
		cust.deposit(deposit, acc);
		LoggingUtil.logTrace("Customer "+cust.getName()+" deposited "+ deposit+" to account "+acc.getId());
		System.out.println("Succefully deposited. New balance is :"+acc.getBalance());
	}
	
	//withdraws from the customers account
	private static void runWithdraw(Customer cust, Account acc, Scanner scan) {
		double withdraw;
		do {
			System.out.println("Enter amount to withdraw :");
			withdraw = scan.nextDouble();
			if(withdraw<0 || withdraw>acc.getBalance()) {
				System.out.println("Invalid amount");
			}
		}while(withdraw<0 || withdraw>acc.getBalance());
		cust.withdraw(withdraw, acc);
		LoggingUtil.logTrace("Customer "+cust.getName()+" deposited "+ withdraw+" to account "+acc.getId());
		System.out.println("Succefully withdrew. New balance is :"+acc.getBalance());
	}
	
	//transfers from the customers account to the account with the given id
	private static void runTransfer(Customer cust,Account acc, Scanner scan) {
		double transfer;
		int id;
		do {
			System.out.println("Enter amount to transfer :");
			transfer = scan.nextDouble();
			if(transfer<0 || transfer>acc.getBalance()) {
				System.out.println("Invalid amount");
			}
		}while(transfer<0 || transfer>acc.getBalance());
		do {
			System.out.println("Enter id of account to transfer too :");
			id = scan.nextInt();
			if(!bankHasAccountId(id)) {
				System.out.println("Invalid account id");
			}
		}while(!bankHasAccountId(id));
		
		cust.transfer(transfer, acc, accountWithId(id));
		LoggingUtil.logTrace("Customer "+cust.getName()+" transfered "+ transfer+" from account "+ acc.getId()+" to account "+id);
		System.out.println("Succefully withdrew. New balance is :"+acc.getBalance());
	}
	
	private static boolean bankHasAccountId(int id) {
		for(Account a:Bank.getAccounts()) {
			if(a.getId()==id) return true;
		}
		return false;
	}
	
	private static Account accountWithId(int id) {
		for(Account a:Bank.getAccounts()) {
			if(a.getId()==id) return a;
		}
		return null;
	}
	
}