package com.learn.testng;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReturnsControllerTest extends AbstractTestNGSpringContextTests  {

    private static ExtentReports extent;
    private static ExtentTest test;
    private String baseUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("src/test/resources/reports/returns-extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @BeforeClass
    public void beforeClass() {
        test = extent.createTest("Walmart Returns Flow Test");
        baseUrl = "http://localhost:" + port + "/api/returns";
    }

    @Test(priority = 1)
    public void testReturnEligibility() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(baseUrl + "/eligibility/ELIGIBLE123", String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        test.pass("Eligibility check passed: " + response.getBody());
    }

    @Test(priority = 2)
    public void testReturnReasons() {
        ResponseEntity<List> response =
                restTemplate.getForEntity(baseUrl + "/reasons", List.class);
        Assert.assertTrue(response.getBody().contains("Damaged"));
        test.pass("Return reasons retrieved successfully: " + response.getBody());
    }

    @Test(priority = 3)
    public void testDispositionPath() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(baseUrl + "/disposition/ITEM123", String.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        test.pass("Disposition retrieved successfully: " + response.getBody());
    }
    @Test(priority = 4)
    public void testCreateReturn() {
        Map<String, Object> request = new HashMap<>();
        request.put("orderId", "ELIGIBLE123");
        request.put("reason", "Damaged");

        ResponseEntity<String> response =
                restTemplate.postForEntity(baseUrl + "/create", request, String.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        test.pass("Return created successfully: " + response.getBody());
    }

    @Test(priority = 5)
    public void testRefundComputation() {
        ResponseEntity<Double> response =
                restTemplate.getForEntity(baseUrl + "/refund/ELIGIBLE123", Double.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue(response.getBody() > 0);
        test.pass("Refund computed successfully: " + response.getBody());
    }



    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}
