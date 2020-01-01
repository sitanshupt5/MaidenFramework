package Automation;

import Utitlities.BaseUtilities;
import Utitlities.DataProviderMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.LandingPage;
import pageObjects.LoginPage;

import java.io.IOException;

public class Module2 extends BaseUtilities {
    public static Logger log = LogManager.getLogger(Module1.class.getName());
    private LoginPage loginPage;

    @BeforeClass
    public void launchBrowser() throws IOException
    {

        driver = initializeDriver();
        driver.manage().window().maximize();
    }


    @Test
    public void TestCase_03_ErrorLogin() throws IOException
    {
        startTestCase("TestCase_03_ErrorLogin", "Module1");
        //Step1
        node = test.createNode("Open URL");
        driver.get(properties.getProperty("url"));
        LandingPage landingPage = new LandingPage(driver);
        Assert.assertTrue(landingPage.ClosePopup().isDisplayed());
        setStepStatus();
        //Step2
        node = test.createNode("Close Popup").pass("Popup overlay should be removed");
        landingPage.ClosePopup().click();
        log.trace("Popup modal is clicked.");
        setStepStatus();
        //Step3
        node = test.createNode("Open Login page").pass("Login fields should be displayed");
        landingPage.LoginButton().click();
        log.trace("Login Page is now opened.");
        loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.Username().isDisplayed());
        log.trace("Login Page is now opened.");
        setStepStatus();
        //Step4
        node = test.createNode("Enter login credentials").pass("Login credentials should be populated");
        loginPage.Username().sendKeys(excel.singleIteration("TestCase_03_ErrorLogin", "Username"));
        loginPage.Password().sendKeys(excel.singleIteration("TestCase_03_ErrorLogin", "Password"));
        log.trace("Login credentials entered.");
        setStepStatus();
        //Step5
        node = test.createNode("Login error validation").pass("Valid error message should be displayed.");
        loginPage.Submit().click();
        Assert.assertEquals(loginPage.loginError().getText(),"Invalid email or password");
        log.error("Error message mismatch");
        log.trace("Test case executed successfully");

    }

    @Test(dataProvider = "TestCase_04")
    public void TestCase_04_ErrorLogin(String username, String password) throws IOException
    {
        startTestCase("TestCase_04", "Module1");
        //Step1
        node = test.createNode("Enter login credentials").pass("Login credentials should be populated");
        loginPage.Username().sendKeys(username);
        loginPage.Password().sendKeys(password);
        log.trace("Login Data entered successfully");
        setStepStatus();
        node = test.createNode("Login error validation").pass("Valid error message should be displayed.");
        loginPage.Submit().click();
        Assert.assertEquals(loginPage.loginError().getText(),"Invalid email or password.");
        log.error("Error message mismatch");
        log.trace("Test case executed successfully");
        excel.closeFile();

    }

    @AfterClass
    public void terminateTest()
    {
        closeBrowser();
    }


    @DataProvider(name = "TestCase_04")
    public Object[][] data() throws IOException
    {
        DataProviderMapper d = new DataProviderMapper();
        String filepath = "D:\\WorkSpace\\MaidenFramework\\data\\Module1.xlsx";
        String sheetname = "TestCase_03_ErrorLogin";
        Object[][] data = d.dataMapper(filepath,sheetname);
        return data;
    }
}
