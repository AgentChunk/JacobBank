package com.revature.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.revature.dao.BankDaoImp;

/**
 * 
 * @author Jacob Lathrop
 * The Bank holds all Accounts, Customers, Employees, and Admins
 * 
 *
 */


public class Bank implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4458500791896096728L;
	private static Set<Account> accounts = new HashSet<Account>();
	private static Map<Account,Customer> jointApplications = new HashMap<Account,Customer>();
	private static List<Application> applications = new ArrayList<Application>();
	private static Set<Customer> customers = new HashSet<Customer>();
	private static Set<Employee> employees = new HashSet<Employee>();
	private static Set<Admin> admins = new HashSet<Admin>();
	private static int userId;
	private static int accId;
	
	public static int newAccId(int i) {
		if(i>accId) {
			accId=i;
		}else {
			accId++;
		}
		return accId;
	}
	
	public static boolean validAccId(int i) {
		return i>accId;
	}
	
	public static void resestAccId() {
		accId=0;
	}
	
	public static int newUserId(int i) {
		if(i>userId) {
			userId=i;
		}else {
			userId++;
		}
		return userId;
	}
	
	public static boolean validUserId(int i) {
		return i>userId;
	}
	
	public static void resestUserId() {
		userId=0;
	}
	
	public static void addAccount(Account toAdd) {
		accounts.add(toAdd);
	}
	public static void addApplication(Application toAdd) {
		applications.add(toAdd);
	}
	
	public static void addJointApplication(Account toAdd, Customer cust) {
		jointApplications.put(toAdd, cust);
	}
	
	public static void addCustomer(Customer toAdd) {
		customers.add(toAdd);
	}
	
	public static void addEmployee(Employee toAdd) {
		employees.add(toAdd);
	}
	
	public static void addAdmin(Admin toAdd) {
		admins.add(toAdd);
	}
	
	public static void deleteAccount(Account toDel) {
		accounts.remove(toDel);
	}
	
	public static void deleteApplication(Account toDel) {
		applications.remove(toDel);
	}
	
	public static void deleteJointApplication(Account toDel) {
		jointApplications.remove(toDel);
	}
	
	public static void deleteCustomer(Customer toDel) {
		customers.remove(toDel);
	}
	
	public static void deleteEmployee(Employee toDel) {
		employees.remove(toDel);
	}
	
	public static void deleteAdmin(Admin toDel) {
		admins.remove(toDel);
	}

	public static Set<Account> getAccounts() {
		return accounts;
	}
	
	public static List<Application> getApplications(){
		return applications; 
	}
	
	public static Set<Customer> getCustomers() {
		return customers;
	}

	public static Set<Employee> getEmployees() {
		return employees;
	}

	public static Set<Admin> getAdmins() {
		return admins;
	}
	
	public static Map<Account,Customer> getJointApplications(){
		return jointApplications;
	}
	public static void setAccounts(Set<Account> accounts) {
		Bank.accounts = accounts;
	}
	public static void setApplications(List<Application> applications) {
		Bank.applications = applications;
	}
	public static void setJointApplications(Map<Account, Customer> jointApplications) {
		Bank.jointApplications = jointApplications;
	}
	public static void setCustomers(Set<Customer> customers) {
		Bank.customers = customers;
	}
	public static void setEmployees(Set<Employee> employees) {
		Bank.employees = employees;
	}
	public static void setAdmins(Set<Admin> admins) {
		Bank.admins = admins;
	}
	
	public static boolean bankHasAccountId(int id,Set<Account> accounts) {
		for(Account a:accounts) {
			if(a.getID() == id) return true;
		}
		return false;
	}
	
	public static Account accountWithId(int id,Set<Account> accounts) {
		for(Account a:accounts) {
			if(a.getID() ==id) return a;
		}
		return null;
	}
	
	public static Customer customerWithId(int id,Set<Customer> customers) {
		for(Customer c:customers) {
			if(c.getId()==id) return c;
		}
		return null;
	}
	
	//call at start to get the list of all users and sort them into correct 
	//bank sets
	public static void sortUsers() {
		BankDaoImp bd = new BankDaoImp();
		Set<Customer> cust = new HashSet<Customer>();
		Set<Employee> empl = new HashSet<Employee>();
		Set<Admin> adm = new HashSet<Admin>();
		
		List<Login> users = bd.retrieveAllUsers();
		for(Login u:users) {
			if(u instanceof Customer) {
				cust.add((Customer) u);
			}
			else if(u instanceof Admin) {
				adm.add((Admin) u);
			}
			else if(u instanceof Employee) {
				empl.add((Employee) u);
			}
		}
		Bank.setCustomers(cust);
		Bank.setEmployees(empl);
		Bank.setAdmins(adm);
		
	}
	
}
