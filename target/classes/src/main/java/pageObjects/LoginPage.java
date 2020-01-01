package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public WebDriver driver;

    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id= 'user_email']")
    WebElement username;

    @FindBy(xpath = "//input[@id= 'user_password']")
    WebElement password;

    @FindBy(xpath = "//input[@value= 'Log In']")
    WebElement submit;

    @FindBy(xpath = "//div[@class = 'alert alert-danger']")
    WebElement loginerror;

    public WebElement Username()
    {
        return username;
    }
    public WebElement Password()
    {
        return password;
    }
    public WebElement Submit()
    {
        return submit;
    }
    public WebElement loginError() {return loginerror;}
}
