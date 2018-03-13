package com.revature.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.BankDaoImp;
import com.revature.database.Account;
import com.revature.database.Bank;
import com.revature.database.Login;
import com.revature.io.LoggingUtil;

public class BankDaoImpTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		Bank.getAccounts().clear();
		Bank.getCustomers().clear();
		Bank.getEmployees().clear();
		Bank.getAdmins().clear();
		Bank.getApplications().clear();
		Bank.getJointApplications().clear();
		Bank.resestUserId();
		Bank.resestAccId();
	}

	@Test
	public void testRetrieveUserById() {
		BankDaoImp bd = new BankDaoImp();
		Login user = bd.retrieveUserById(1);
		assertEquals("Jack",user.getName());	
	}
	
	@Test 
	public void testRetrieveAllUsers() {
		BankDaoImp bd = new BankDaoImp();
		List<Login> list = bd.retrieveAllUsers();
		assertEquals(6,list.size());
		
	}
	
	@Test
	public void testRetrieveAccountById() {
		BankDaoImp bd = new BankDaoImp();
		Account acc = bd.retrieveAccountById(1);
		assertEquals(1,acc.getID());
	}
	
	@Test
	public void testRetrieveAllAccounts() {
		BankDaoImp bd = new BankDaoImp();
		Set<Account> list = bd.retrieveAllAccounts();
		assertEquals(5,list.size());
	}
	
	@Test
	public void testCreateAccountPreparedStmt() {
		BankDaoImp bd = new BankDaoImp();
		Account acc = new Account();
		int test = bd.createAccountPreparedStmt(acc);
		LoggingUtil.logDebug(""+test);
	}
	

}
