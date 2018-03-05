package com.revature.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import com.revature.database.Account;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.database.Employee;



class CustomerTest {

	

	@Test
	void testCreateCustomer() {
		Customer test = new Customer("User","Password");
		assertEquals("User",test.getName());
		assertEquals("Password",test.getPassword());
	}
	
	@Test
	void testRequestAccount() {
		Customer test = new Customer("User","Password");
		Account toApprove = test.applyForAccount();

		assertEquals(false,toApprove.isApproved());
		assertTrue(Bank.getApplications().containsKey(toApprove));
		
	}
	
	@Test 
	void testRequestJointAccount() {
		Customer original = new Customer("User","Password");
		Customer jointer = new Customer("User2","Password");
		Account toApprove = original.applyForAccount();
		Employee approver = new Employee();
		approver.processRequest(true, toApprove);
		jointer.applyForJointAccount(toApprove);
		assertTrue(Bank.getJointApplications().containsKey(toApprove));
	}
	
	@Test
	void testInvalidJointAccountRequest() {
		Customer tester = new Customer("User","Password");
		Account unapproved = new Account();
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
		tester.applyForJointAccount(unapproved);
		});
	}
	
	@Test
	void testCustomerDeposit() {
		Customer test = new Customer("User","Password");
		Account approved = new Account();
		approved.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(approved);
		test.setAccounts(accounts);
		test.deposit(10.0, 0);
		assertEquals(10,approved.getBalance());
	}
	
	@Test
	void testCustomerDepositUnapproved() {
		Customer test = new Customer("User","Password");
		Account unapproved = new Account();
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(unapproved);
		test.setAccounts(accounts);
		test.deposit(10.0, 0);
		assertEquals(0,unapproved.getBalance());
			
	}
	
	void testCustomerDepositInvalid() {
		Customer test = new Customer("User","Password");
		Account approved = new Account();
		approved.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(approved);
		test.setAccounts(accounts);
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
		test.deposit(-10.0, 0);
		});
	}
	
	@Test
	void testCustomerWithdraw() {
		Customer test = new Customer("User","Password");
		Account approved = new Account();
		approved.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(approved);
		test.setAccounts(accounts);
		approved.deposit(20.0);
		test.withdraw(10, 0);
		assertEquals(10,approved.getBalance());
	}
	
	@Test
	void testCustomerWithdrawUnapproved() {
		Customer test = new Customer("User","Password");
		Account unapproved = new Account();
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(unapproved);
		test.setAccounts(accounts);
		unapproved.deposit(10.0);
		test.withdraw(10, 0);
		assertEquals(10,unapproved.getBalance());
	}
	
	@Test
	void testCustomerWithdrawInvalid() {
		Customer test = new Customer("User","Password");
		Account approved = new Account();
		approved.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(approved);
		test.setAccounts(accounts);
		test.deposit(10.0, 0);
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
		test.withdraw(20.0, 0);
		});
	}
	
	@Test
	void testCustomerTransfer() {
		Customer test = new Customer("User","Password");
		Account a1 = new Account();
		Account a2 = new Account();
		a1.setApproved(true);
		a2.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(a1);
		accounts.add(a2);
		test.setAccounts(accounts);
		a1.deposit(20.0);
		a2.deposit(10.0);
		test.transfer(10, a1 ,a2);
		assertEquals(20,a2.getBalance());
		assertEquals(10,a1.getBalance());
	}
	
	@Test
	void testCustomerTransferOneApproved() {
		Customer test = new Customer("User","Password");
		Account a1 = new Account();
		Account a2 = new Account();
		a1.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(a1);
		accounts.add(a2);
		test.setAccounts(accounts);
		a1.deposit(20.0);
		a2.deposit(10.0);
		test.transfer(10, a1 ,a2);
		assertEquals(10,a2.getBalance());
		assertEquals(20,a1.getBalance());
	}
	
	@Test
	void testCustomerTransferUnapproved() {
		Customer test = new Customer("User","Password");
		Account a1 = new Account();
		Account a2 = new Account();
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(a1);
		accounts.add(a2);
		test.setAccounts(accounts);
		a1.deposit(20.0);
		a2.deposit(10.0);
		test.transfer(10, a1 ,a2);
		assertEquals(10,a2.getBalance());
		assertEquals(20,a1.getBalance());
	}
	
	@Test
	void testCustomerTransferInvalid() {
		Customer test = new Customer("User","Password");
		Account a1 = new Account();
		Account a2 = new Account();
		a1.setApproved(true);
		a2.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(a1);
		accounts.add(a2);
		test.setAccounts(accounts);
		a1.deposit(20.0);
		a2.deposit(10.0);
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			test.transfer(30, a1,a2);
		});
		
	}
	
	
	
	@Test
	void testCustomerToSring() {
		Customer test = new Customer("User","Password");
		assertEquals(test.toString(),"Customer [name=User, password=Password, accounts=[]]");
	}
	
	@Test
	void testCustomerWithAccountToSring() {
		Customer test = new Customer("User","Password");
		Account account = new Account();
		test.getAccounts().add(account);
		account.getOwners().add(test);
		assertEquals(test.toString(),"Customer [name=User, password=Password, accounts=[Account [balance=0.0, approved=false, owners= [User ]]]]");
	}

}
