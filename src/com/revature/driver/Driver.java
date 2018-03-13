package com.revature.driver;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;

import com.revature.dao.BankDaoImp;
import com.revature.database.Account;
import com.revature.database.Admin;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.database.Employee;
import com.revature.database.Login;
import com.revature.database.LoginFactory;
import com.revature.io.LoggingUtil;
import com.revature.io.Serializer;
import com.revature.util.ConnectionFactory;

public class Driver {
	
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		Scanner scan = new Scanner(System.in);
		Map<Character,Runnable> commands = new HashMap<Character,Runnable>();
		boolean run = true;
		Serializer serialize = new Serializer();
		BankDaoImp bd = new BankDaoImp();
		
		
	
		//get data from the database
		Bank.sortUsers();
		Bank.setAccounts(bd.retrieveAllAccounts());
		//links accounts with their owners
		bd.getAccountCustomerLink();
		//retrieve the applications
		Bank.setApplications(bd.retrieveApplications());
//		LoggingUtil.logTrace("Reading in file");
//		serialize.writeIn("database.txt");
		
		commands.put('C', () -> runLogin("customer",scan));
		commands.put('E', () -> runLogin("employee",scan));
		commands.put('A', () -> runLogin("admin",scan));
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
	        scan.nextLine();
	        if("CEAQ".indexOf(cmd)<0) {
	        	System.out.println("Invalid command");
	        }
	        else if(cmd== 'Q') {
	        	run = quit();
	        }else
	        	commands.get(cmd).run();
		
		}
		
		//log all accounts, customers, employees, and admins
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
		
	}
	
	private static void runLogin(String type, Scanner scan) {
		System.out.println("Press L to login or R to register a new account: ");
		LoginFactory factory = new LoginFactory();
		Login dummy = factory.getLogin(type);
		char cmd = scan.next().charAt(0);
		scan.nextLine();
		
		//If they log in
		if(cmd =='L') {
			System.out.print("Enter your username : ");
			String user = scan.next();
			scan.nextLine();
			System.out.print("Enter your password : ");
			String pass = scan.next();
			scan.nextLine();
			if(dummy.validLogin(user, pass)) {
				dummy = dummy.login(user, pass);
				dummy.runLoggedOn(scan);
			}else {
				System.out.println("Invalid username or password");
			}
		}
		else if(cmd=='R') {
			
			System.out.print("Enter your customer username : ");
			String user = scan.next();
			scan.nextLine();
			System.out.print("Enter your password : ");
			String pass = scan.next();
			scan.nextLine();
			dummy = factory.getLogin(type,user, pass);
			LoggingUtil.logTrace("New customer created "+ dummy);
			System.out.println("Hello "+user+", your new account has been created");
			dummy.runLoggedOn(scan);
			
		}else {
			System.out.println("Invalid command");
		}
		
	}

	private static boolean quit() {
		return false;
	}
	
	
	
}
