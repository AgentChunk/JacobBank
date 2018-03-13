package com.revature.database;

import java.io.Serializable;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.revature.io.LoggingUtil;

//an account holds money
public class Account implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5869631646022235354L;
	
	private int accId;
	private double balance;
	private boolean approved;
	private List<Customer> owners;
	
	public Account(){
		balance =0;
		approved =false;
		owners = new ArrayList<Customer>();
	}
	
	public Account(double money) {
		balance = money;
		approved=false;
		owners = new ArrayList<Customer>();
		
	}
	
	public Account(int id) {
		balance=0;
		approved =false;
		owners= new ArrayList<Customer>();
		accId = id;
	}
	
	public int getID() {
		return accId;
	}
	
	public void setID(int id) {
		accId=id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	public List<Customer> getOwners() {
		return owners;
	}

	public void setOwners(List<Customer> owners) {
		this.owners = owners;
	}

	//adds to the balance of the account
	public void deposit(double add) throws IllegalArgumentException {
		if(add<0) {
			throw new IllegalArgumentException();
		}
		balance +=add;
	}
	
	public void withdraw(double subtract) throws IllegalArgumentException {
		if(subtract<0 || subtract>balance) {
			throw new IllegalArgumentException();
		} 
		balance -=subtract;
	}
	
	public void transfer(double transfer, Account recipient) throws IllegalArgumentException {
		if(transfer<0 || transfer>balance) {
			throw new IllegalArgumentException();
		}
		balance -=transfer;
		recipient.deposit(transfer);
		
	}

	@Override
	public String toString() {
		String string = "Account [accID="+accId +", balance=" + balance + ", approved=" + approved + ", owners= [";
		for(Customer o: owners) {
			String temp = o.getName()+ " ";
			string = string.concat(temp);
		}
		string = string.concat("]]");
		return string;
	}

	public boolean isApproved() {
		return approved;
	}

	//only when account is approved give the account it's id
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	//gets amount to deposit
	public static double scanDeposit(Account acc, Scanner scan) {
		double deposit=-1;
		do {
			System.out.println("Enter amount to withdraw :");
			
			if(scan.hasNextDouble()) {
				deposit = scan.nextDouble();
				if(deposit<0) {
					System.out.println("Invalid amount");
				}
			}else {
				System.out.println("Invalid input");
				scan.next();
			}
		}while(deposit<0);
		
		return deposit;
	}
	
	public static double scanWithdraw(Account acc, Scanner scan) {
		double withdraw =-1;
		do {
			
			
			System.out.println("Enter amount to withdraw :");
			
			if(scan.hasNextDouble()) {
				withdraw = scan.nextDouble();
				if(withdraw<0 || withdraw>acc.getBalance()) {
					System.out.println("Invalid amount");
				}
			}else {
				System.out.println("Invalid input");
				scan.next();
			}
				
		}while(withdraw<0 || withdraw>acc.getBalance());
		
		return withdraw;
	}
	
	public static Pair<Double,Account> scanTransfer(Account acc, Scanner scan){
		int id=0;
		double transfer=-1;
		do {
			System.out.println("Enter amount to transfer :");
			if(scan.hasNextDouble()) {
				transfer = scan.nextDouble();
				if(transfer <0 || transfer >acc.getBalance()) {
					System.out.println("Invalid amount");
				}
			}else {
				System.out.println("Invalid input");
				scan.next();
			}
			
		}while(transfer<0 || transfer>acc.getBalance());
		do {
			System.out.println("Enter id of account to transfer too :");
			if(scan.hasNextInt()) {
				id = scan.nextInt();
				if(!Bank.bankHasAccountId(id,Bank.getAccounts())) {
					System.out.println("Invalid account id");
				}
			}else {
				System.out.println("Invalid input");
				scan.next();
			}
		}while(!Bank.bankHasAccountId(id,Bank.getAccounts()));
		Account acc1 = Bank.accountWithId(id, Bank.getAccounts());
		return new Pair<Double, Account>(transfer,acc1);
	}
}
