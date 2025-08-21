package com.learn.testng;

import org.testng.Assert;
import org.testng.annotations.*;

public class AnnotationOrderDemo {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println(">> @BeforeSuite: Setup global resources (DB connection, Spring context init)");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println(">> @BeforeTest: Setup test-level resources (like test data set)");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println(">> @BeforeClass: Run once before all tests in this class");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println(">> @BeforeMethod: Run before each @Test (reset cart, prepare data)");
    }

    @Test
    public void test1() {
        System.out.println("** Running Test 1: Add item");
    }

    @Test
    public void test2() {
        System.out.println("** Running Test 2: Remove item");
    }

    @DataProvider(name = "cartData")
    public Object[][] cartData() {
        System.out.println(">> DataProvider cartData");
        return new Object[][]{
                {"ITEM1001", 2},
                {"ITEM1002", 3},
                {"ITEM1003", 5}
        };
    }

    @Test(dataProvider = "cartData")
    public void testAddItem(String productId, int qty) {
        System.out.println("** Running Test with: " + productId + " qty=" + qty);
        Assert.assertTrue(qty > 0);
    }
    @AfterMethod
    public void afterMethod() {
        System.out.println(">> @AfterMethod: Run after each @Test (log result, cleanup cart)");
    }

    @AfterClass
    public void afterClass() {
        System.out.println(">> @AfterClass: Run once after all tests in this class");
    }

    @AfterTest
    public void afterTest() {
        System.out.println(">> @AfterTest: Run after all test methods in <test> block");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println(">> @AfterSuite: Cleanup global resources (close DB connection)");
    }
}
