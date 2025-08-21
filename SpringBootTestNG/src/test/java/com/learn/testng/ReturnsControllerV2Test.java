package com.learn.testng;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReturnsControllerV2Test extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate;
    private String baseUrl;

    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void beforeClass() {
        restTemplate = new RestTemplate();
        test = extent.createTest("Walmart Returns Flow Test");
        baseUrl = "http://localhost:" + port + "/api/v2/returns";
    }

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("src/test/resources/reports/returns-extent-report-v2.html");
        reporter.config().setReportName("Walmart Returns Automation Report");
        reporter.config().setDocumentTitle("Returns Testing");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Awinas Kannan");
        extent.setSystemInfo("Environment", "QA");
    }

    @AfterClass
    public void tearDown() {
        extent.flush();
    }

    @DataProvider(name = "returnItems")
    public Object[][] returnItems() {
        return new Object[][]{
                {"ORDER1001", "ITEM123", "Damaged"},
                {"ORDER1002", "ITEM456", "Not Needed"},
                {"ORDER1003", "ITEM789", "Other"}
        };
    }

    @Test(dataProvider = "returnItems")
    public void testFullReturnFlow(String orderId, String itemId, String reason) {
        test = extent.createTest("Return Flow Test for Item: " + itemId);

        // 1. Eligibility
        Map eligibility = restTemplate.getForObject(baseUrl + "/eligibility?orderId=" + orderId + "&itemId=" + itemId, Map.class);
        test.info("Eligibility Response: " + eligibility);
        Assert.assertNotNull(eligibility);
        Assert.assertTrue(eligibility.containsKey("eligible"));

        // 2. Reasons
        List reasons = restTemplate.getForObject(baseUrl + "/reasons?itemId=" + itemId, List.class);
        test.info("Reasons: " + reasons);
        Assert.assertTrue(reasons.size() > 0);

        // 3. Disposition
        Map disposition = restTemplate.getForObject(baseUrl + "/disposition?itemId=" + itemId, Map.class);
        test.info("Disposition: " + disposition);
        Assert.assertNotNull(disposition.get("disposition"));

        // 4. Create Return
        Map createReturn = restTemplate.postForObject(baseUrl + "/create?orderId=" + orderId + "&itemId=" + itemId + "&reason=" + reason, null, Map.class);
        test.info("Create Return: " + createReturn);
        Assert.assertNotNull(createReturn.get("returnId"));

        String returnId = (String) createReturn.get("returnId");

        // 5. Refund
        Map refund = restTemplate.postForObject(baseUrl + "/refund?orderId=" + orderId + "&itemId=" + itemId, null, Map.class);
        test.info("Refund: " + refund);
        Assert.assertTrue( (Integer) refund.get("refundAmount") > 0);

        // 6. Receipt
        Map receipt = restTemplate.getForObject(baseUrl + "/receipt?returnId=" + returnId, Map.class);
        test.info("Receipt: " + receipt);
        Assert.assertNotNull(receipt.get("receiptNumber"));

        // 7. Cancel Return
        Map cancel = restTemplate.postForObject(baseUrl + "/cancel?returnId=" + returnId, null, Map.class);
        test.info("Cancel Return: " + cancel);
        Assert.assertEquals(cancel.get("status"), "Cancelled");
    }
}
