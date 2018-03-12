package com.revature.database;

public class LoginFactory {
	
		//use getLogin method to get dummy object of type Login
	   public Login getLogin(String loginType){
	      if(loginType == null){
	         return null;
	      }		
	      if(loginType.equalsIgnoreCase("CUSTOMER")){
	         return new Customer();
	         
	      } else if(loginType.equalsIgnoreCase("EMPLOYEE")){
	         return new Employee();
	         
	      } else if(loginType.equalsIgnoreCase("ADMIN")){
	         return new Admin();
	      }
	      
	      return null;
	   }

	   //use getLogin method to get object of type Login
	   public Login getLogin(String loginType, String user, String password){
	      if(loginType == null){
	         return null;
	      }		
	      if(loginType.equalsIgnoreCase("CUSTOMER")){
	         return Customer.createCustomer(user, password);
	         
	      } else if(loginType.equalsIgnoreCase("EMPLOYEE")){
	         return Employee.createEmployee(user, password);
	         
	      } else if(loginType.equalsIgnoreCase("ADMIN")){
	         return Admin.createAdmin(user, password);
	      }
	      
	      return null;
	   }
	   
	 //use getLogin method to get object of type Login
	   public Login getLogin(String loginType, int id, String user, String password){
	      if(loginType == null){
	         return null;
	      }		
	      if(loginType.equalsIgnoreCase("CUSTOMER")){
	         return Customer.createCustomer(id,user, password);
	         
	      } else if(loginType.equalsIgnoreCase("EMPLOYEE")){
	         return Employee.createEmployee(id,user, password);
	         
	      } else if(loginType.equalsIgnoreCase("ADMIN")){
	         return Admin.createAdmin(id,user, password);
	      }
	      
	      return null;
	   }
}
