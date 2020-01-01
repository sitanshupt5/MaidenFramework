package Utitlities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseUtilities {

    public static WebDriver driver;
    public Properties properties;

    public WebDriver initializeDriver() throws IOException
    {
        properties = new Properties();
        FileInputStream project_config_file = new FileInputStream("D:\\WorkSpace\\MaidenFramework\\src\\project_config.properties");
        properties.load(project_config_file);
        String browserName = properties.getProperty("browser");
        System.out.println(browserName);
        System.out.println(properties.getProperty("chrome_driver_path"));
        if(browserName.equalsIgnoreCase("chrome"))
        {
            System.setProperty("webdriver.chrome.driver",properties.getProperty("chrome_driver_path"));
            driver = new ChromeDriver();

        }
        else if(browserName.equalsIgnoreCase("firefox"))
        {
            System.setProperty("webdriver.gecko.driver",properties.getProperty("firefox_driver_path"));
            driver = new FirefoxDriver();

        }
        else
        {
            System.setProperty("webdriver.edge.driver","D:\\WorkSpace\\MicrosoftWebDriver.exe");
            driver = new EdgeDriver();

        }

        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;

    }

    public void takeScreenshot(String testCaseName) throws IOException
    {
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("D:\\WorkSpace\\ScreenShots\\"+testCaseName+"screenshot.png"));
    }
}
