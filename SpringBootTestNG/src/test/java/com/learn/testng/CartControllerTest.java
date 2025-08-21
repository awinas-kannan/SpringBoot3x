package com.learn.testng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartControllerTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeClass
    public void beforeClass() {
        baseUrl = "http://localhost:" + port + "/api/cart";
        System.out.println(">> @BeforeClass: Tests starting on " + baseUrl);
    }

    @BeforeMethod
    public void setup() {
        // Reset cart before each test
        System.out.println(">> BeforeMethod: Reset cart before each test ");
        restTemplate.delete(baseUrl + "/remove/ITEM1001");
        restTemplate.delete(baseUrl + "/remove/ITEM1002");
        restTemplate.delete(baseUrl + "/remove/ITEM1003");
    }

    @AfterMethod
    public void tearDown() {
        ResponseEntity<Integer> resp = restTemplate.getForEntity(baseUrl + "/total", Integer.class);
        System.out.println(">> @AfterMethod: Cart total items = " + resp.getBody());
    }

    @DataProvider(name = "cartData")
    public Object[][] cartData() {
        System.out.println(">> @DataProvider: cartData");
        return new Object[][]{
                {"ITEM1001", 2, 2},
                {"ITEM1002", 3, 3},
                {"ITEM1003", 5, 5}
        };
    }

    @Test(dataProvider = "cartData")
    public void testAddAndVerify(String productId, int qty, int expectedCount) {
        System.out.println(">> @Test: testAddAndVerify");
        restTemplate.postForEntity(baseUrl + "/add?productId=" + productId + "&qty=" + qty, null, String.class);

        ResponseEntity<Integer> response =
                restTemplate.getForEntity(baseUrl + "/count/" + productId, Integer.class);

        Assert.assertEquals(response.getBody().intValue(), expectedCount);
    }

//    @Test
    public void testScenario_AddRemoveVerify() {
        // Add items
        restTemplate.postForEntity(baseUrl + "/add?productId=ITEM1001&qty=2", null, String.class);
        restTemplate.postForEntity(baseUrl + "/add?productId=ITEM1002&qty=3", null, String.class);

        // Verify total = 5
        ResponseEntity<Integer> totalResp = restTemplate.getForEntity(baseUrl + "/total", Integer.class);
        Assert.assertEquals(totalResp.getBody().intValue(), 5);

        // Remove ITEM1001
        restTemplate.delete(baseUrl + "/remove/ITEM1001");

        // Verify ITEM1001 count = 0
        ResponseEntity<Integer> countResp = restTemplate.getForEntity(baseUrl + "/count/ITEM1001", Integer.class);
        Assert.assertEquals(countResp.getBody().intValue(), 0);

        // Verify total = 3
        ResponseEntity<Integer> totalAfter = restTemplate.getForEntity(baseUrl + "/total", Integer.class);
        Assert.assertEquals(totalAfter.getBody().intValue(), 3);
    }
}
