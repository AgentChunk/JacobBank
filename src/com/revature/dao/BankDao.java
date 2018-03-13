package com.revature.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.revature.database.Account;
import com.revature.database.Application;
import com.revature.database.Customer;
import com.revature.database.Login;

public interface BankDao {
	
	//user methods
	public Login retrieveUserById(int id);
	
	public List<Login> retrieveAllUsers();
	
	public void updateUser(Login user);
	
	public void deleteUser(int id);
	
	public int createUserPreparedStmt(Login user);
	
	//account methods
	public Account retrieveAccountById(int id);
	
	public List<Account> retrieveAllAccounts();
	
	public void updateAccount(Account acc);
	
	public void deleteAccount(int id);
	
	public int createAccountPreparedStmt(Account acc);
	
	//applications methods
	public List<Application> retrieveApplications();
	
	public void deleteApplication(Application app);
	
	public void createAppPreparedStmt(Application app);
	
	//Account_User functions
	public void getAccountCustomerLink();
	
	public void createAccountUserRow(int accId,int userId);
	
	//Use Account procedures
	public void deposit(int id, double add);
	
	public void withdraw(int id,double sub);
	
	public void transfer(int a1, int a2, double transfer);
	
}
