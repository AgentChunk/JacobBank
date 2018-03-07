package com.revature.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountTest.class, AdminTest.class, BankTest.class, CustomerTest.class, EmployeeTest.class })
public class AllTests {

}
