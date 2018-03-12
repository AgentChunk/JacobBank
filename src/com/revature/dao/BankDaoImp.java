package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.revature.database.Account;
import com.revature.database.Admin;
import com.revature.database.Application;
import com.revature.database.Bank;
import com.revature.database.Customer;
import com.revature.database.Employee;
import com.revature.database.Login;
import com.revature.database.LoginFactory;
import com.revature.io.LoggingUtil;
import com.revature.util.ConnectionFactory;

public class BankDaoImp implements BankDao {


	@Override
	public Login retrieveUserById(int id) {
		LoginFactory factory = new LoginFactory();
		Login user=null;
		int userid=id;
		String name="";
		String password="";
		int type=0;
		
		String sql = "SELECT * FROM \"USER\" WHERE USERID = ?";
		
		try {
			PreparedStatement ps = 
					ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				
				userid= rs.getInt(1);
				name=(rs.getString(2));
				password=(rs.getString(3));
				type = rs.getInt(4);
				
			}
			LoggingUtil.logDebug(userid + name +password + type);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(type==1) {
			user = factory.getLogin("Customer",userid, name, password);
			LoggingUtil.logDebug("Retrieving Customer");
		}else if(type==2){
			user = factory.getLogin("Employee",userid,name,password);
			LoggingUtil.logDebug("Retrieving Employee");
		}else if(type==3) {
			user = factory.getLogin("Admin",userid,name,password);
			LoggingUtil.logDebug("Retrieving Admin");
		}
		
		return user;
	}

	@Override
	public List<Login> retrieveAllUsers() {
		LoginFactory factory = new LoginFactory();
		List<Login> list = new ArrayList<Login>();
		Login user=null;
		int userid=0;
		String name="";
		String password="";
		int type=0;
		
		String sql = "SELECT * FROM \"USER\"";
		
		try {
			PreparedStatement ps = 
					ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				userid= rs.getInt(1);
				name=(rs.getString(2));
				password=(rs.getString(3));
				type = rs.getInt(4);
				if(type==1) {
					user = factory.getLogin("Customer",userid, name, password);
					list.add(user);
					LoggingUtil.logDebug("Customer found");
				}else if(type==2){
					user = factory.getLogin("Employee",userid,name,password);
					list.add(user);
					LoggingUtil.logDebug("Employee found");
				}else if(type==3) {
					user = factory.getLogin("Admin",userid,name,password);
					list.add(user);
					LoggingUtil.logDebug("Admin found");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void updateUser(Login user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub	
		String sql = "DELETE FROM \"USER\" WHERE USERID =?";
		
		
		try {
			Connection conn = ConnectionFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			Savepoint s = conn.setSavepoint("updateSavepoint");
			
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			conn.rollback(s);
			
			conn.commit();
			
			conn.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void createUserPreparedStmt(Login user) {
		String sql = "INSERT INTO \"USER\" (USERNAME, USERPASSWORD, USERTYPE) VALUES(?, ?, ?)";
		
		try {
			
			Connection conn = ConnectionFactory.getInstance().getConnection();
			
			conn.setAutoCommit(false);
			
			Savepoint s = conn.setSavepoint("myFirstSavepoint");
			
			String type;
			
		
			if(user instanceof Employee) {
				type = "2";		
			} else if (user instanceof Admin) {
				type = "3";	
			} else  {
				type = "1";
			}
			
			
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, type);
			ps.executeUpdate();
			
			conn.rollback(s);
			
			conn.commit();
			
			conn.setAutoCommit(true);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Account retrieveAccountById(int id) {
		Account account = new Account();
		
		String sql = "SELECT * FROM ACCOUNTS WHERE ACCOUNT_ID = ?";
		
		try {
			PreparedStatement ps = 
					ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(Bank.validAccId(rs.getInt(1))) {
					account.setID(Bank.newAccId(rs.getInt(1)));
				} else {
					account.setID(Bank.newAccId(0));
				}
				account.setBalance(rs.getDouble(2));
				account.setApproved(rs.getBoolean(3));			
			}
			
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return account;
	}

	@Override
	public Set<Account> retrieveAllAccounts() {
		Set<Account> list = new HashSet<Account>();
		
		String sql = "SELECT * FROM ACCOUNTS";
		
		try {
			PreparedStatement ps = 
					ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Account temp = new Account();
				if(Bank.validAccId(rs.getInt(1))) {
					temp.setID(Bank.newAccId(rs.getInt(1)));
				} else {
					temp.setID(Bank.newAccId(0));
				}
				temp.setBalance(rs.getDouble(2));
				temp.setApproved(rs.getBoolean(3));
				list.add(temp);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void updateAccount(Account acc) {
		String sql = "UPDATE ACCOUNTS SET BALANCE =?, APPROVED =? WHERE ACCOUNT_ID =?";
		
		
		try {
			Connection conn = ConnectionFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			Savepoint s = conn.setSavepoint("updateSavepoint");
			
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ps.setDouble(1, acc.getBalance());
			ps.setBoolean(2, acc.isApproved());
			ps.setInt(3, acc.getID());
			
			conn.rollback(s);
			
			conn.commit();
			
			conn.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void deleteAccount(int id) {
		String sql = "DELETE FROM ACCOUNTS WHERE ACCOUNT_ID =?";
		
		
		try {
			Connection conn = ConnectionFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			Savepoint s = conn.setSavepoint("updateSavepoint");
			
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.executeUpdate();
			
			conn.rollback(s);
			
			conn.commit();
			
			conn.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void createAccountPreparedStmt(Account acc) {
		String sql = "INSERT INTO ACCOUNT (ACCOUNT_ID, BALANCE, APPROVED) VALUES(?, ?, ?)";
		
		try {
			
			Connection conn = ConnectionFactory.getInstance().getConnection();
			
			conn.setAutoCommit(false);
			
			Savepoint s = conn.setSavepoint("myFirstSavepoint");
			
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ps.setInt(1, acc.getID());
			ps.setDouble(2, acc.getBalance());
			ps.setBoolean(3, acc.isApproved());
			ps.executeUpdate();
			
			conn.rollback(s);
			
			conn.commit();
			
			conn.setAutoCommit(true);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public List<Application> retrieveApplications() {
		List<Application> apps = new ArrayList<Application>();
		String sql = "SELECT * FROM APPLICATION";
		
		try {
			PreparedStatement ps = 
					ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id;
				Account acc = new Account();
				Customer cust = new Customer();
				Application app;
				id =rs.getInt(1);
				acc=Bank.accountWithId(id, Bank.getAccounts());
				id =rs.getInt(2);
				cust = Bank.customerWithId(id, Bank.getCustomers());
				app = new Application(acc,cust);
				apps.add(app);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return apps;
	}
	
	public void deleteApplication(Application app) {
		String sql = "DELETE FROM APPLICATION WHERE ACCOUNT_ID=?, USER_ID=?";
		int accId = app.getAccount().getID();
		int userId = app.getCustomer().getId();
		try {
			Connection conn = ConnectionFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			Savepoint s = conn.setSavepoint("updateSavepoint");
			
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ps.setInt(1,accId);
			ps.setInt(2, userId);
			ps.executeUpdate();
			
			conn.rollback(s);
			
			conn.commit();
			
			conn.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createAppPreparedStmt(Application app){
		String sql = "INSERT INTO APPLICATION (ACCOUNT_ID, USER_ID) VALUES(?, ?)";
		int accId =app.getAccount().getID();
		int userId = app.getCustomer().getId();
		try {
			
			Connection conn = ConnectionFactory.getInstance().getConnection();
			
			conn.setAutoCommit(false);
			
			Savepoint s = conn.setSavepoint("myFirstSavepoint");
			
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			
			ps.setInt(1, accId);
			ps.setInt(2, userId);
		
			ps.executeUpdate();
			
			conn.rollback(s);
			
			conn.commit();
			
			conn.setAutoCommit(true);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void getAccountCustomerLink() {
		String sql = "SELECT * FROM ACCOUNT_USER";
		
		try {
			PreparedStatement ps = 
					ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int accId;
				int userId;
				
				Account acc = new Account();
				Customer cust = new Customer();
				
				accId =rs.getInt(1);
				userId =rs.getInt(2);
				acc=Bank.accountWithId(accId, Bank.getAccounts());
				cust = Bank.customerWithId(userId, Bank.getCustomers());
				acc.getOwners().add(cust);
				cust.getAccounts().add(acc);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createAccountUserRow(int accId,int userId) {
		String sql = "INSERT INTO ACCOUNT_USER VALUES(?,?)";
		try {
			
			Connection conn = ConnectionFactory.getInstance().getConnection();
			
			conn.setAutoCommit(false);
			
			Savepoint s = conn.setSavepoint("myFirstSavepoint");
			
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ps.setInt(1, accId);
			ps.setInt(2, userId);
		
			ps.executeUpdate();
			
			conn.rollback(s);
			
			conn.commit();
			
			conn.setAutoCommit(true);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
