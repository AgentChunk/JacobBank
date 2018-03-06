package com.revature.test;



import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.database.Account;
import com.revature.database.Customer;
import com.revature.io.LoggingUtil;

public class AccountTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	

	@Test
	public void testSetBalance() {
		Account test = new Account();
		test.setBalance(20);
		assertEquals(20,test.getBalance(),0);
	}
	
	@Test
	public void testSetOwnders() {
		Account test = new Account();
		Customer owner = new Customer();
		List<Customer> owners = new ArrayList<Customer>();
		owners.add(owner);
		test.setOwners(owners);
		assertTrue(test.getOwners().contains(owner));
		
	}
	
	@Test
	public void testSetApproved() {
		Account test = new Account();
		test.setApproved(true);
		assertTrue(test.isApproved());
	}

	@Test
	public void testDeposit() {
		Account test = new Account();
		test.deposit(20.00);
		
		assertEquals(20.00,test.getBalance(),0);
	}
	
	@Test
	public void testDepositNegative(){
		
		Account test = new Account();
		expectedException.expect(IllegalArgumentException.class);
		
		test.deposit(-1.0);
		
	}
	
	@Test
	public void testWithdraw() {
		Account test = new Account(20.0);
		test.withdraw(5.0);
		assertEquals(15.0,test.getBalance(),0);
	}
	
	@Test
	public void testWithdrawNegative() {
	
		Account test = new Account(30.0);
		expectedException.expect(IllegalArgumentException.class);
		test.withdraw(-10.0);
	}
	
	@Test
	public void testOverWithdraw() {
		
		Account test = new Account(30.0);
		expectedException.expect(IllegalArgumentException.class);
		
		test.withdraw(40.0);
	}
	
	@Test
	public void testTransfer() {
		Account test = new Account(30);
		Account test1 = new Account(0);
		test.transfer(10, test1);
		assertEquals(10,test1.getBalance(),0);
		assertEquals(20,test.getBalance(),0);
	}
	
	@Test
	public void testTransferNegative() {
		
		Account test = new Account(30);
		Account test1 = new Account(0);
		
		expectedException.expect(IllegalArgumentException.class);
		test.transfer(-10, test1);
	}
	
	@Test
	public void testTransferOverdraw() {
		
		Account test = new Account(30);
		Account test1 = new Account(0);
		expectedException.expect(IllegalArgumentException.class);
		test.transfer(40, test1);
	}
	
	@Test
	public void testToString() {
		Account test = new Account();
		LoggingUtil.logDebug(test.toString());
		assertEquals(test.toString().substring(56),"balance=0.0, approved=false, owners= []]");
	}
	
	@Test
	public void testToStringWithCustomer(){
		Account test = new Account();
		test.getOwners().add(new Customer("User","Password"));
		LoggingUtil.logDebug(test.toString());
		assertEquals(test.toString().substring(56),"balance=0.0, approved=false, owners= [User ]]");
	}
	

}
