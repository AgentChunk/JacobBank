package com.revature.test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.revature.database.Account;
import com.revature.database.Admin;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.database.Employee;

class BankTest {

	

	@Test
	void testAddAccount() {
		Account toAdd = new Account();
		Bank.addAccount(toAdd);
		assertTrue(Bank.getAccounts().contains(toAdd));
	}
	
	@Test
	void testDeleteAccount() {
		Account toAdd = new Account();
		Bank.addAccount(toAdd);
		Bank.deleteAccount(toAdd);
		assertFalse(Bank.getAccounts().contains(toAdd));
	}
	
	@Test
	void testAddApplication() {
		Account toAdd = new Account();
		Customer requester = new Customer();
		Bank.addApplication(toAdd, requester);
		assertTrue(Bank.getApplications().containsKey(toAdd));
	}

	
	@Test
	void testRemoveApplication() {
		Account toAdd = new Account();
		Customer requester = new Customer();
		Bank.addApplication(toAdd, requester);
		Bank.deleteApplication(toAdd);
		assertFalse(Bank.getApplications().containsKey(toAdd));
	}
	
	@Test
	void testAddJointApplication() {
		Account toAdd = new Account();
		Customer requester = new Customer();
		Bank.addJointApplication(toAdd, requester);
		assertTrue(Bank.getJointApplications().containsKey(toAdd));
	}
	
	@Test
	void testRemoveJointApplication() {
		Account toAdd = new Account();
		Customer requester = new Customer();
		Bank.addJointApplication(toAdd, requester);
		Bank.deleteJointApplication(toAdd);
		assertFalse(Bank.getJointApplications().containsKey(toAdd));
	}
	
	@Test
	void testAddCustomer(){
		Customer toAdd = new Customer();
		Bank.addCustomer(toAdd);
		assertTrue(Bank.getCustomers().contains(toAdd));
	}
	
	@Test
	void testDeleteCustomer() {
		Customer toAdd = new Customer();
		Bank.addCustomer(toAdd);
		Bank.deleteCustomer(toAdd);
		assertFalse(Bank.getCustomers().contains(toAdd));
	}
	
	@Test
	void testAddEmployee() {
		Employee toAdd = new Employee();
		Bank.addEmployee(toAdd);
		assertTrue(Bank.getEmployees().contains(toAdd));
		
	}
	
	@Test
	void testDeleteEmployee() {
		Employee toAdd = new Employee();
		Bank.addEmployee(toAdd);
		Bank.deleteEmployee(toAdd);
		assertFalse(Bank.getEmployees().contains(toAdd));
	}
	
	@Test
	void testAddAdmin() {
		Admin toAdd = new Admin();
		Bank.addAdmin(toAdd);
		assertTrue(Bank.getAdmins().contains(toAdd));
		
	}
	
	@Test
	void testDeleteAdmim() {
		Admin toAdd = new Admin();
		Bank.addAdmin(toAdd);
		Bank.deleteAdmin(toAdd);
		assertFalse(Bank.getAdmins().contains(toAdd));
	}
}
