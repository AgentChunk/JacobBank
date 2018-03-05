package com.revature.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.database.Account;
import com.revature.database.Admin;
import com.revature.database.Bank;
import com.revature.database.Customer;

class AdminTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testWithdraw() {
		Account test = new Account(10);
		Admin admin = new Admin();
		admin.withdraw(5, test);
		assertEquals(5,test.getBalance());
		
	}
	
	@Test
	void testInvalidWithdraw() {
		Account test = new Account(20.0);
		Admin admin = new Admin();
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			admin.withdraw(30.0, test);
		});
		
		
	}
	
	@Test
	void testDeposit() {
		Account test = new Account();
		Admin admin = new Admin();
		admin.deposit(10, test);
		assertEquals(10,test.getBalance());
	}
	
	@Test
	void testInvalidDepost() {
		Account test = new Account();
		Admin admin = new Admin();
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			admin.deposit(-10, test);
		});
	}
	
	@Test
	void testTransfer() {
		Account a1 = new Account(20);
		Account a2 = new Account(30);
		Admin admin = new Admin();
		admin.transfer(10, a1, a2);
		assertEquals(10,a1.getBalance());
		assertEquals(40,a2.getBalance());
	}
	
	@Test
	void testInvalidTransfer() {
		Account a1 = new Account(20);
		Account a2 = new Account(30);
		Admin admin = new Admin();
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			admin.transfer(30, a1, a2);
		});
	}
	
	@Test
	void testCancelAccount() {
		Account a1 = new Account();
		Bank.getAccounts().add(a1);
		Admin admin = new Admin();
		admin.cancelAccount(a1);
		assertFalse(Bank.getAccounts().contains(a1));
	}
	
	@Test
	void printAccounts() {
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
	void printCustomers() {
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
