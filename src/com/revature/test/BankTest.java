package com.revature.test;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.revature.database.Account;
import com.revature.database.Admin;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.database.Employee;

public class BankTest {

	

	@Test
	public void testAddAccount() {
		Account toAdd = new Account();
		Bank.addAccount(toAdd);
		assertTrue(Bank.getAccounts().contains(toAdd));
	}
	
	@Test
	public void testDeleteAccount() {
		Account toAdd = new Account();
		Bank.addAccount(toAdd);
		Bank.deleteAccount(toAdd);
		assertFalse(Bank.getAccounts().contains(toAdd));
	}
	
	@Test
	public void testAddApplication() {
		Account toAdd = new Account();
		Customer requester = new Customer();
		Bank.addApplication(toAdd, requester);
		assertTrue(Bank.getApplications().containsKey(toAdd));
	}

	
	@Test
	public void testRemoveApplication() {
		Account toAdd = new Account();
		Customer requester = new Customer();
		Bank.addApplication(toAdd, requester);
		Bank.deleteApplication(toAdd);
		assertFalse(Bank.getApplications().containsKey(toAdd));
	}
	
	@Test
	public void testAddJointApplication() {
		Account toAdd = new Account();
		Customer requester = new Customer();
		Bank.addJointApplication(toAdd, requester);
		assertTrue(Bank.getJointApplications().containsKey(toAdd));
	}
	
	@Test
	public void testRemoveJointApplication() {
		Account toAdd = new Account();
		Customer requester = new Customer();
		Bank.addJointApplication(toAdd, requester);
		Bank.deleteJointApplication(toAdd);
		assertFalse(Bank.getJointApplications().containsKey(toAdd));
	}
	
	@Test
	public void testAddCustomer(){
		Customer toAdd = new Customer();
		Bank.addCustomer(toAdd);
		assertTrue(Bank.getCustomers().contains(toAdd));
	}
	
	@Test
	public void testDeleteCustomer() {
		Customer toAdd = new Customer();
		Bank.addCustomer(toAdd);
		Bank.deleteCustomer(toAdd);
		assertFalse(Bank.getCustomers().contains(toAdd));
	}
	
	@Test
	public void testAddEmployee() {
		Employee toAdd = new Employee();
		Bank.addEmployee(toAdd);
		assertTrue(Bank.getEmployees().contains(toAdd));
		
	}
	
	@Test
	public void testDeleteEmployee() {
		Employee toAdd = new Employee();
		Bank.addEmployee(toAdd);
		Bank.deleteEmployee(toAdd);
		assertFalse(Bank.getEmployees().contains(toAdd));
	}
	
	@Test
	public void testAddAdmin() {
		Admin toAdd = new Admin();
		Bank.addAdmin(toAdd);
		assertTrue(Bank.getAdmins().contains(toAdd));
		
	}
	
	@Test
	public void testDeleteAdmim() {
		Admin toAdd = new Admin();
		Bank.addAdmin(toAdd);
		Bank.deleteAdmin(toAdd);
		assertFalse(Bank.getAdmins().contains(toAdd));
	}
	
	@Test
	public void testSetAccounts() {
		Set<Account> accounts = new HashSet<Account>();
		Account test = new Account();
		accounts.add(test);
		Bank.setAccounts(accounts);
		assertTrue(Bank.getAccounts().equals(accounts));
	}
	
	@Test
	public void testSetApplications() {
		Map<Account,Customer> applications = new HashMap<Account,Customer>();
		Account test = new Account();
		Customer customer = new Customer();
		applications.put(test, customer);
		Bank.setApplications(applications);
		assertTrue(Bank.getApplications().equals(applications));
	}
	
	@Test
	public void testSetJointApplications() {
		Map<Account,Customer> applications = new HashMap<Account,Customer>();
		Bank.setJointApplications(applications);
		assertTrue(Bank.getJointApplications().equals(applications));
	}
	
	@Test
	public void testSetCustomers() {
		Set<Customer> customers = new HashSet<Customer>();
		Bank.setCustomers(customers);
		assertTrue(Bank.getCustomers().equals(customers));
		
	}
	
	@Test
	public void testSetEmployees() {
		Set<Employee> employees = new HashSet<Employee>();
		Bank.setEmployees(employees);
		assertTrue(Bank.getEmployees().equals(employees));
	}
	
	@Test
	public void testSetAdmins() {
		Set<Admin> admins = new HashSet<Admin>();
		Bank.setAdmins(admins);
		assertTrue(Bank.getAdmins().equals(admins));
	}
	
	@Test
	public void testSortUsers() {
		Bank.sortUsers();
		assertEquals(3,Bank.getCustomers().size());
		assertEquals(2,Bank.getEmployees().size());
		assertEquals(1,Bank.getAdmins().size());
	}
	
}
