package com.revature.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.database.Account;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.database.Employee;
import com.revature.io.LoggingUtil;



public class CustomerTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@After
	public void clear() {
		Bank.getCustomers().clear();
		Bank.getAccounts().clear();
	}
	
	@Test
	public void testValidLogin() {
		Customer test = new Customer("Name","Password");
		Bank.getCustomers().add(test);
		assertTrue(test.validLogin("Name", "Password"));
	}
	
	@Test
	public void testInvalidLogin() {
		Customer test = new Customer();
		Bank.getCustomers().add(test);
		for(Customer c:Bank.getCustomers()) {
			LoggingUtil.logDebug(c.toString());
		}
		
		assertFalse(test.validLogin("Name", "Password"));
	}
	
	@Test
	public void testInvaidLogin2() {
		Customer test = new Customer();
		Bank.getCustomers().add(test);
		
		assertFalse(test.validLogin("", "password"));
	}
	
	@Test
	public void testGetCustomer() {
		Customer test = Customer.createCustomer("Name","Password");
		assertEquals(test,test.login("Name", "Password"));
	}
	
//	@Test 
//	public void testGetCustomerNull() {
//		assertEquals(null,Customer.getCustomer("Name", "Password"));
//	}
	
//	@Test
//	public void testGetCustomerNull2() {
//		Customer.createCustomer("", "");
//		assertEquals(null,Customer.getCustomer("", "password"));
//		assertEquals(null,Customer.getCustomer("name", ""));
//	}
	
	
	@Test
	public void testCustomerSetters() {
		Customer test = new Customer();
		test.setName("Name");
		test.setPassword("Password");
		assertEquals("Name",test.getName());
		assertEquals("Password",test.getPassword());
	}
	
	
	
	@Test
	public void testNewCustomer(){
		Customer test = new Customer();
		assertEquals("",test.getName());
		assertEquals("",test.getPassword());
		
	}
	
	@Test
	public void testCreateCustomer() {
		Customer test = Customer.createCustomer("User","Password");
		assertEquals("User",test.getName());
		assertEquals("Password",test.getPassword());
	}
	
	@Test
	public void testRequestAccount() {
		Customer test = new Customer("User","Password");
		Account toApprove = test.applyForAccount();

		assertEquals(false,toApprove.isApproved());
		assertTrue(Bank.getApplications().containsKey(toApprove));
		
	}
	
	@Test 
	public void testRequestJointAccount() {
		Customer original = new Customer("User","Password");
		Customer jointer = new Customer("User2","Password");
		Account toApprove = original.applyForAccount();
		Employee approver = new Employee();
		approver.processRequest(true, toApprove);
		jointer.applyForJointAccount(toApprove);
		assertTrue(Bank.getJointApplications().containsKey(toApprove));
	}
	
	@Test
	public void testInvalidJointAccountRequest() {
		Customer tester = new Customer("User","Password");
		Account unapproved = new Account();
		
		expectedException.expect(IllegalArgumentException.class);
		tester.applyForJointAccount(unapproved);
	
	}
	
	@Test
	public void testCustomerDeposit() {
		Customer test = new Customer("User","Password");
		Account approved = new Account();
		approved.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(approved);
		test.setAccounts(accounts);
		test.deposit(10.0, approved);
		assertEquals(10,approved.getBalance(),0);
	}
	
	@Test
	public void testCustomerDepositUnapproved() {
		Customer test = new Customer("User","Password");
		Account unapproved = new Account();
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(unapproved);
		test.setAccounts(accounts);
		test.deposit(10.0, unapproved);
		assertEquals(0,unapproved.getBalance(),0);
			
	}
	
	@Test
	public void testCustomerDepostNoAccount() {
		Customer test = new Customer();
		Account account = new Account();
		account.setApproved(true);
		test.deposit(10, account);
		assertEquals(0,account.getBalance(),0);
	}
	
	public void testCustomerDepositInvalid() {
		Customer test = new Customer("User","Password");
		Account approved = new Account();
		approved.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(approved);
		test.setAccounts(accounts);
		
		expectedException.expect(IllegalArgumentException.class);
		test.deposit(-10.0, approved);
		
	}
	
	@Test
	public void testCustomerWithdraw() {
		Customer test = new Customer("User","Password");
		Account approved = new Account();
		approved.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(approved);
		test.setAccounts(accounts);
		approved.deposit(20.0);
		test.withdraw(10, approved);
		assertEquals(10,approved.getBalance(),0);
	}
	
	@Test
	public void testCustomerWithdrawUnapproved() {
		Customer test = new Customer("User","Password");
		Account unapproved = new Account();
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(unapproved);
		test.setAccounts(accounts);
		unapproved.deposit(10.0);
		test.withdraw(10, unapproved);
		assertEquals(10,unapproved.getBalance(),0);
	}
	
	@Test
	public void testCustomerWithdrawInvalid() {
		Customer test = new Customer("User","Password");
		Account approved = new Account();
		approved.setApproved(true);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(approved);
		test.setAccounts(accounts);
		test.deposit(10.0, approved);
		expectedException.expect(IllegalArgumentException.class);
		test.withdraw(20.0, approved);
		
	}
	
	@Test
	public void testCustomerTransfer() {
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
		assertEquals(20,a2.getBalance(),0);
		assertEquals(10,a1.getBalance(),0);
	}
	
	@Test
	public void testCustomerTransferOneApproved() {
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
		assertEquals(10,a2.getBalance(),0);
		assertEquals(20,a1.getBalance(),0);
	}
	
	@Test
	public void testCustomerTransferUnapproved() {
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
		assertEquals(10,a2.getBalance(),0);
		assertEquals(20,a1.getBalance(),0);
	}
	
	@Test
	public void testCustomerTransferInvalid() {
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
		
		expectedException.expect(IllegalArgumentException.class);
		test.transfer(30, a1,a2);
		
		
	}
	
	
	
	@Test
	public void testCustomerToSring() {
		Customer test = new Customer("User","Password");
		assertEquals(test.toString(),"Customer [name=User, password=Password, accounts=[]]");
	}
	
	@Test
	public void testCustomerWithAccountToSring() {
		Customer test = new Customer("User","Password");
		Account account = new Account();
		test.getAccounts().add(account);
		account.getOwners().add(test);
		
	}

}
