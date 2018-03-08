package com.revature.database;

import java.util.Scanner;

public interface Login {
	
	//Checks if the Login of with name and password exists
	public boolean validLogin(String name, String password);
	//Logs into the account with the name and password
	public Login login(String name, String password);
	//runs the UI for when the user logs on
	public void runLoggedOn(Scanner scan);
	
}
