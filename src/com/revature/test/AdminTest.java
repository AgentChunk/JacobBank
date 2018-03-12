package com.revature.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.database.Account;
import com.revature.database.Admin;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.io.LoggingUtil;

public class AdminTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@After
	public void clear() {
		Bank.getAccounts().clear();
		Bank.getCustomers().clear();
		Bank.getEmployees().clear();
		Bank.getAdmins().clear();
		Bank.getApplications().clear();
		Bank.getJointApplications().clear();
	}
	
	@Test
	public void testLogin() {
		Admin admin =Admin.createAdmin("Name", "password");
		assertEquals(admin,admin.login("Name", "password"));
	}
	
	/*
	@Test
	public void testGetAdminNull() {
		Admin admin = 
		assertEquals(null,Admin.getEmployee("", ""));
	}
	*/
	
	@Test
	public void testValidLogin() {
		Admin admin =Admin.createAdmin("Name", "password");
		assertTrue(admin.validLogin("Name", "password"));
	}
//	@Test
//	public void testInvalidLogin() {
//		assertFalse(Admin.validLogin("Name", "password"));
//	}
	
	
	@Test
	public void testCreateAdmin() {
		Admin test = Admin.createAdmin("Name", "password");
		assertTrue(Bank.getAdmins().contains(test));
	}
	
	@Test
	public void testToString() {
		Admin test = new Admin();
		LoggingUtil.logDebug(test.toString());
		assertEquals(test.toString(),"Admin [customers=[], name=, password=]");
	}
	
	@Test
	public void testWithdraw() {
		Account test = new Account(10);
		Admin admin = new Admin();
		admin.withdraw(5, test);
		assertEquals(5,test.getBalance(),0);
		
	}
	
	@Test
	public void testInvalidWithdraw() {
		Account test = new Account(20.0);
		Admin admin = new Admin();
		
		expectedException.expect(IllegalArgumentException.class);
		admin.withdraw(30.0, test);
		
		
		
	}
	
	@Test
	public void testDeposit() {
		Account test = new Account();
		Admin admin = new Admin();
		admin.deposit(10, test);
		assertEquals(10,test.getBalance(),0);
	}
	
	@Test
	public void testInvalidDepost() {
		Account test = new Account();
		Admin admin = new Admin();
		expectedException.expect(IllegalArgumentException.class);
		admin.deposit(-10, test);
		
	}
	
	@Test
	public void testTransfer() {
		Account a1 = new Account(20);
		Account a2 = new Account(30);
		Admin admin = new Admin();
		admin.transfer(10, a1, a2);
		assertEquals(10,a1.getBalance(),0);
		assertEquals(40,a2.getBalance(),0);
	}
	
	@Test
	public void testInvalidTransfer() {
		Account a1 = new Account(20);
		Account a2 = new Account(30);
		Admin admin = new Admin();
		expectedException.expect(IllegalArgumentException.class);
			admin.transfer(30, a1, a2);
		
	}
	
	@Test
	public void testCancelAccount() {
		Account a1 = new Account();
		Bank.getAccounts().add(a1);
		Admin admin = new Admin();
		admin.cancelAccount(a1);
		assertFalse(Bank.getAccounts().contains(a1));
	}
	
	@Test
	public void printAccounts() {
		Admin admin = new Admin();
		Customer c1 = new Customer("Joe","123");
		Customer c2 = new Customer("Bob","Pass");
		Account a1 = c1.applyForAccount();
		admin.processRequest(true, a1);
		Account a2 = c2.applyForAccount();
		admin.processRequest(true, a2);
		
		admin.printAccounts();
	}
	
	@Test
	public void printCustomers() {
		Admin admin = new Admin();
		Customer c1 = new Customer("Joe","123");
		Customer c2 = new Customer("Bob","Pass");
		Bank.getCustomers().add(c1);
		Bank.getCustomers().add(c2);
		Account a1 = c1.applyForAccount();
		admin.processRequest(true, a1);
		Account a2 = c2.applyForAccount();
		admin.processRequest(true, a2);
		
		admin.printCustomers();
	}

}
