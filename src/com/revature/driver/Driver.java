package com.revature.driver;

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
		
		/*
		LoggingUtil.logTrace("Start");
		LoggingUtil.logTrace("Adding some base info to bank");
		
		Customer joel = new Customer("Joel", "12345");
		Customer sam = new Customer("Sam", "passcode");
		Customer chris = new Customer("Chris", "hello");
		Bank.addCustomer(joel);
		Bank.addCustomer(sam);
		Bank.addCustomer(chris);
		
		Employee nick = new Employee("Nick");
		Bank.addEmployee(nick);
		
		Admin jake = new Admin("Jake");
		Bank.addAdmin(jake);
		
		Account a1 =joel.applyForAccount();
		Account a2 =sam.applyForAccount();
		Account a3 =chris.applyForAccount();
		
		nick.processRequest(true, a1);
		nick.processRequest(true, a2);
		jake.processRequest(true, a3);
		
		LoggingUtil.logTrace("Testing Serialization");
		*/
		Serializer serialize = new Serializer();
		//serialize.writeOut("database.txt");
		LoggingUtil.logTrace("Testing reading in file");
		serialize.writeIn("database.txt");
		
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
	
}
