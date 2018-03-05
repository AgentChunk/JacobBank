package com.revature.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private static Map<Account,Customer> applications = new HashMap<Account,Customer>();
	private static Map<Account,Customer> jointApplications = new HashMap<Account,Customer>();
	private static Set<Customer> customers = new HashSet<Customer>();
	private static Set<Employee> employees = new HashSet<Employee>();
	private static Set<Admin> admins = new HashSet<Admin>();
	
	
	public static void addAccount(Account toAdd) {
		accounts.add(toAdd);
	}
	public static void addApplication(Account toAdd, Customer cust) {
		applications.put(toAdd, cust);
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
	
	public static Map<Account,Customer> getApplications(){
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
	public static void setApplications(Map<Account, Customer> applications) {
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
	
	
	
	
}
