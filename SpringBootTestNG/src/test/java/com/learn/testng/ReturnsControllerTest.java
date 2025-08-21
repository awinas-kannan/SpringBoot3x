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
public class ReturnsControllerTest extends AbstractTestNGSpringContextTests {

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
        reporter.config().setReportName("Walmart Returns Automation Report");
        reporter.config().setDocumentTitle("Returns Testing");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Awinas Kannan");
        extent.setSystemInfo("Environment", "QA");
    }

    @BeforeClass
    public void beforeClass() {
        test = extent.createTest("Walmart Returns Flow Test");
        baseUrl = "http://localhost:" + port + "/api/returns";
    }

    @Test
    public void testCheckEligibility() {
        String response = restTemplate.getForObject(baseUrl + "/eligibility/ORD123", String.class);
        test.info("Response: " + response);
        Assert.assertEquals(response, "Eligible for return");
        test.pass("Eligibility check passed");
    }

    @Test
    public void testGetReturnReasons() {
        String[] reasons = restTemplate.getForObject(baseUrl + "/reasons", String[].class);
        test.info("Reasons: " + String.join(", ", reasons));
        Assert.assertTrue(reasons.length > 0);
        test.pass("Fetched return reasons successfully");
    }

    @Test
    public void testGetDisposition() {
        String disposition = restTemplate.getForObject(baseUrl + "/disposition/ITEM200", String.class);
        test.info("Disposition: " + disposition);
        Assert.assertEquals(disposition, "Resell in Store");
        test.pass("Disposition path verified");
    }

    @Test
    public void testCreateReturn() {
        Map<String, Object> request = new HashMap<>();
        request.put("orderId", "ORD123");
        request.put("reason", "Damaged Item");

        String response = restTemplate.postForObject(baseUrl + "/create", request, String.class);
        test.info("Response: " + response);
        Assert.assertTrue(response.contains("ORD123"));
        test.pass("Return creation successful");
    }

    @Test
    public void testComputeRefund() {
        Double refund = restTemplate.getForObject(baseUrl + "/refund/ORD456", Double.class);
        test.info("Refund: " + refund);
        Assert.assertEquals(refund, 225.0);
        test.pass("Refund computed correctly");
    }


    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}
