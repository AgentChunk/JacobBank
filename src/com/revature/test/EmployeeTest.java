package com.revature.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.database.Account;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.database.Employee;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import org.junit.rules.ExpectedException;

class EmployeeTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	

	
	
	
	@Test
	void testProcessRequest() {
		Employee james = new Employee();
		Customer joe = new Customer();
		Account toAdd = joe.applyForAccount();
		
		james.processRequest(true, toAdd);
		assertTrue(toAdd.isApproved());
		assertTrue(joe.getAccounts().contains(toAdd));
		
	}
	
	@Test
	void testProcessRequestDenied() {
		Employee james = new Employee();
		Customer joe = new Customer();
		Account toAdd = joe.applyForAccount();
				
		james.processRequest(false, toAdd);
		assertFalse(Bank.getApplications().containsKey(toAdd));
		assertFalse(Bank.getAccounts().contains(toAdd));
	}
	
	@Test
	void testProcessJointRequest(){
		Employee james = new Employee();
		Customer joe = new Customer();
		Customer jointRequester = new Customer();
		Account joint = joe.applyForAccount();
		james.processRequest(true, joint);
		jointRequester.applyForJointAccount(joint);
		james.processJointRequest(true, joint);
		
		assertTrue(jointRequester.getAccounts().contains(joint));
		assertFalse(Bank.getJointApplications().containsKey(joint));
	}
	
	@Test
	void testProcessJointRequestDenied() {
		Employee james = new Employee();
		Customer joe = new Customer();
		Customer jointRequester = new Customer();
		Account joint = joe.applyForAccount();
		james.processRequest(true, joint);
		jointRequester.applyForJointAccount(joint);
		james.processJointRequest(false, joint);
		
		assertFalse(jointRequester.getAccounts().contains(joint));
		assertFalse(Bank.getJointApplications().containsKey(joint));
	}
	
	@Test
	void testPJRNoApplication() {
		Employee james = new Employee();
		Customer joe = new Customer();
		
		Account joint = joe.applyForAccount();
		james.processRequest(true, joint);
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			james.processJointRequest(true, joint);
		});
	}
	
	@Test
	void testPJRNoSuchAccount() {
		Employee james = new Employee();
		Account joint = new Account();
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			james.processJointRequest(true,joint);
		});
	}
	
	
	
	@Test
	void testIllegalArgumentProcessRequest() {
		Employee james = new Employee();
		Customer joe = new Customer();
		Account toAdd = new Account();
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
		james.processRequest(true, toAdd);});
	}
	
	@Test
	public void printTest() throws Exception {
	      //ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      //System.setOut(new PrintStream(outContent));

	      // After this all System.out.println() statements will come to outContent stream.

	     // So, you can normally call,
	     //System.out.print("HelloWorld"); // I will assume items is already initialized properly.

	     //Now you have to validate the output. Let's say items had 1 element.
	     // With name as FirstElement and number as 1.
	     String expectedOutput  = "HelloWorld"; // Notice the \n for new line.

	     // Do the actual assertion.
	     //assertEquals(expectedOutput, outContent.toString());
	     //System.setOut(System.out);
	}

	@Test
	void testPrintCustomers() throws Exception {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		Employee james = new Employee();
		Customer joe = new Customer("Joe","Pass");
		Customer jointRequester = new Customer("Joint","123");
		Account joint = joe.applyForAccount();
		james.processRequest(true, joint);
		jointRequester.applyForJointAccount(joint);
		james.processJointRequest(true, joint);
		
		james.printCustomers();
		
	    
	    assertEquals("Customer [name=Joint, password=123, accounts=[Account [balance=0.0, approved=true, owners= [Joe Joint ]]]]\r\n" +
	    		"Customer [name=Joe, password=Pass, accounts=[Account [balance=0.0, approved=true, owners= [Joe Joint ]]]]\r\n", outContent.toString());	
	}
}