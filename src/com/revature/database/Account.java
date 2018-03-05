package com.revature.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//an account holds money
public class Account implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5869631646022235354L;
	
	private static int numberOfAccounts=0;
	private int id;
	private double balance;
	private boolean approved;
	private List<Customer> owners;
	
	public Account(){
		balance =0;
		approved =false;
		owners = new ArrayList<Customer>();
		id=0;
	}
	
	public Account(double money) {
		balance = money;
		approved=false;
		owners = new ArrayList<Customer>();
		id=0;
	}
	
	public int getId() {
		return id;
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
		String string = "Account [id="+id +", balance=" + balance + ", approved=" + approved + ", owners= [";
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
		if(approved) {
			numberOfAccounts++;
			id=numberOfAccounts;
		}
	}
	public static void updateCount() {
		numberOfAccounts = Bank.getAccounts().size();
	}
	
	
}
