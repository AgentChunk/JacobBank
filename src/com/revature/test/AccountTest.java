package com.revature.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.Rule;

import org.junit.rules.ExpectedException;

import com.revature.database.Account;
import com.revature.database.Customer;

class AccountTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	


	@Test
	void testDeposit() {
		Account test = new Account();
		test.deposit(20.00);
		
		assertEquals(20.00,test.getBalance());
	}
	
	@Test
	void testDepositNegative(){
		
		Account test = new Account();
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
		test.deposit(-1.0);});
		
	}
	
	@Test
	void testWithdraw() {
		Account test = new Account(20.0);
		test.withdraw(5.0);
		assertEquals(15.0,test.getBalance());
	}
	
	@Test
	void testWithdrawNegative() {
	
		Account test = new Account(30.0);
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
		test.withdraw(-10.0);});
	}
	
	@Test
	void testOverWithdraw() {
		
		Account test = new Account(30.0);
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
		test.withdraw(40.0);});
	}
	
	@Test
	void testTransfer() {
		Account test = new Account(30);
		Account test1 = new Account(0);
		test.transfer(10, test1);
		assertEquals(10,test1.getBalance());
		assertEquals(20,test.getBalance());
	}
	
	@Test
	void testTransferNegative() {
		
		Account test = new Account(30);
		Account test1 = new Account(0);
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
		test.transfer(-10, test1);});
	}
	
	@Test
	void testTransferOverdraw() {
		
		Account test = new Account(30);
		Account test1 = new Account(0);
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
		test.transfer(40, test1);});
	}
	
	@Test
	void testToString() {
		Account test = new Account();
		assertEquals(test.toString(),"Account [balance=0.0, approved=false, owners= []]");
	}
	
	@Test
	void testToStringWithCustomer(){
		Account test = new Account();
		test.getOwners().add(new Customer("User","Password"));
		assertEquals(test.toString(),"Account [balance=0.0, approved=false, owners= [User ]]");
	}
	

}
