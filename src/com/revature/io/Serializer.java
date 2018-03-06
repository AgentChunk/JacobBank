package com.revature.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.revature.database.Account;
import com.revature.database.Admin;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.database.Employee;

public class Serializer {
	
	private Map<String,Object> objects;
	
	public void prepareBank(){
		Map<String,Object> bank = new HashMap<String,Object>();
		bank.put("Accounts", Bank.getAccounts());
		bank.put("Applications", Bank.getApplications());
		bank.put("JointApplications", Bank.getJointApplications());
		bank.put("Customers", Bank.getCustomers());
		bank.put("Employees", Bank.getEmployees());
		bank.put("Admins", Bank.getAdmins());
		
		this.objects = bank;
	}
	
	public void writeOut(String filename) {
		prepareBank();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(objects);
			LoggingUtil.logTrace("Done");
			LoggingUtil.logTrace(filename);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeIn(String filename) {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
			
			Map<String,Object> bank =  (Map<String, Object>) ois.readObject();
			
			LoggingUtil.logTrace("Found HashMap");
			objects =bank;
			fillBank();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fillBank() {
		Bank.setAccounts((Set<Account>) objects.get("Accounts"));
		Bank.setApplications( (Map<Account, Customer>) objects.get("Applications"));
		Bank.setJointApplications((Map<Account, Customer>) objects.get("JointApplications"));
		Bank.setCustomers((Set<Customer>) objects.get("Customers"));
		Bank.setEmployees((Set<Employee>) objects.get("Employees"));
		Bank.setAdmins((Set<Admin>) objects.get("Admins"));
	}

}
