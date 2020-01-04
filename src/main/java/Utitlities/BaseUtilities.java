package Utitlities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class BaseUtilities {

    public WebDriver driver;
    public Properties properties;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports reports;
    public ExtentTest test, node;
    public static String modulename;
    public ExcelUtils excel;
    public String report_directory;
    private String testcase="t";
    private int shotnumber =1;



    public WebDriver initializeDriver() throws IOException
    {
        String browserName = System.getProperty("browser");
        String mode = System.getProperty("mode");
        System.out.println(browserName);
        System.out.println(properties.getProperty("chrome_driver_path"));
        if(browserName.equalsIgnoreCase("chrome"))
        {
            System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+properties.getProperty("chrome_driver_path"));
            ChromeOptions options = new ChromeOptions();
            if(mode.equalsIgnoreCase("headless"))
            {
                options.addArguments("--headless");
            }
            driver = new ChromeDriver(options);

        }
        else if(browserName.equalsIgnoreCase("firefox"))
        {
            System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+properties.getProperty("firefox_driver_path"));
            FirefoxOptions options = new FirefoxOptions();
            if(mode.equalsIgnoreCase("headless"))
            {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver();

        }
        else
        {
            System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+properties.getProperty("edge_driver_path"));
            driver = new EdgeDriver();

        }

        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;

    }


    @BeforeTest
    public void setConfigs() throws IOException
    {
        setProperties();
        setReportConfig();

    }



    @AfterMethod
    public void publishReports(ITestResult result) throws IOException
    {

        if (result.getStatus() == ITestResult.FAILURE||node.getStatus().toString().equalsIgnoreCase("FAIL"))
        {
            test.log(Status.FAIL, result.getName()+" Test case is Failed", snapMedia(captureScreenShot(result.getName())));
            test.log(Status.FAIL, result.getThrowable()+" Test case is Failed", snapMedia(captureScreenShot(result.getName())));
            node.log(Status.FAIL,"Step Failed", snapMedia(captureScreenShot(result.getName())));
        }
        else if(result.getStatus() == ITestResult.SKIP)
        {
            test.log(Status.SKIP, result.getName()+" Test case is Skipped.");
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            System.out.println(node.getStatus().toString());
            test.log(Status.PASS, result.getName()+" Test case is Passed", snapMedia(captureScreenShot(result.getName())));
            node.log(Status.PASS,"Step Passed", snapMedia(captureScreenShot(result.getName())));

        }



    }



    @AfterSuite
    public void flushReports()
    {

        driver = null;
        reports.flush();
        copyFile();
        System.out.println("after suite executed");
    }

    public void setStepStatus() throws IOException
    {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement e = stack[2];
        String testCaseName = e.getMethodName();


        if (node.getStatus().toString().equalsIgnoreCase("FAIL"))
        {
            node.log(Status.FAIL,"Step Failed", snapMedia(captureScreenShot(testCaseName)));
        }
        else if(node.getStatus().toString().equalsIgnoreCase("PASS"))
        {
            node.log(Status.PASS,"Step Passed", snapMedia(captureScreenShot(testCaseName)));
        }
    }

    public void startTestCase(String testcasename, String filename) throws IOException
    {
        test = reports.createTest(testcasename);
        excel = new ExcelUtils();
        excel.setFilePath(System.getProperty("user.dir")+"\\data\\"+filename+".xlsx");
    }

    public void copyFile()
    {
        Path sourceFile = Paths.get(System.getProperty("user.dir")+"\\target\\surefire-reports\\ExtentReport.html");
        Path targetFile = Paths.get(report_directory+"\\summary.html");
        try
        {
            Files.copy(sourceFile,targetFile, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setModuleName(String name)
    {
        modulename = name;

    }

    public void setProperties() throws IOException
    {
        properties = new Properties();
        FileInputStream project_config_file = new FileInputStream(System.getProperty("user.dir")+"\\src\\project_config.properties");
        properties.load(project_config_file);
    }

    public void closeBrowser()
    {
        driver.close();
    }

    public void takeScreenshot(String testCaseName) throws IOException
    {
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("D:\\WorkSpace\\ScreenShots\\"+testCaseName+"screenshot.png"));
    }

    public String captureScreenShot(String testCaseName) throws IOException
    {
        String folderpath;
        if(testcase.equalsIgnoreCase(testCaseName)) {

            folderpath = createReportDirectory() + "\\" + testcase + "_screenshot" + shotnumber + ".png ";
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(folderpath);
            FileUtils.copyFile(src, dest);
        }
        else
        {
            testcase = testCaseName;
            shotnumber=1;
            folderpath = createReportDirectory() + "\\" + testcase + "_screenshot" + shotnumber + ".png ";
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(folderpath);
            FileUtils.copyFile(src, dest);
        }
        shotnumber++;
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        return folderpath;
    }

    public String retrieveTimeZone()
    {
        Calendar now = Calendar.getInstance();
        TimeZone timeZone = now.getTimeZone();
        System.out.println(timeZone.getDisplayName());
        return timeZone.getDisplayName();
    }

    public MediaEntityModelProvider snapMedia(String filepath) throws IOException
    {
        MediaEntityModelProvider model = MediaEntityBuilder.createScreenCaptureFromPath(filepath).build();
        return model;
    }


    public String getDateTimeStamp()
    {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH");
        df.setTimeZone(TimeZone.getTimeZone(retrieveTimeZone()));
        return df.format(date);
    }

    public String createReportDirectory()
    {
        String folder_path = properties.getProperty("report_path")+"\\"+modulename+"\\Execution_"+getDateTimeStamp();
        Path path = Paths.get(folder_path);

        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(folder_path);
        return folder_path;
    }

    public String createReportFile()
    {
        String local_path = System.getProperty("user.dir")+"\\target\\surefire-reports\\ExtentReport.html";
        report_directory = createReportDirectory();

        Path path = Paths.get(local_path);
        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (Exception e) {
                try
                {
                    Files.delete(path);
                    Files.createFile(path);
                }
                catch(Exception el)
                {
                    el.printStackTrace();
                }
            }
        }
        return local_path;
    }

    public void setReportConfig()
    {
        htmlReporter = new ExtentHtmlReporter(createReportFile());
        htmlReporter.config().setDocumentTitle(modulename);
        htmlReporter.config().setDocumentTitle("Functional Test Results");
        htmlReporter.config().setTheme(Theme.DARK);

        SystemDetails s = new SystemDetails();
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        reports.setSystemInfo("Host Name",s.getSystemName());
        reports.setSystemInfo("Operating System", System.getProperty("os.name"));
        reports.setSystemInfo("Username", s.getUserName());
        reports.setSystemInfo("Browser", properties.getProperty("browser").toUpperCase());
    }



}
