package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPage {

    public WebDriver driver;
    public WebDriverWait wait;

    public LandingPage(WebDriver driver)
    {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//button[text() = 'NO THANKS']")
    WebElement closePopup;

    @FindBy(xpath = "\\a[text()='Home']")
    WebElement homeButton;

    @FindBy(xpath = "\\a[text()='Courses']")
    WebElement coursesButton;

    @FindBy(xpath = "\\a[text()='Videos']")
    WebElement videosButton;

    @FindBy(xpath = "\\a[text()='Interview Guide']")
    WebElement guideButton;

    @FindBy(xpath = "\\a[text()='Practice']")
    WebElement practiceButton;

    @FindBy(xpath = "\\a[text()='Blog']")
    WebElement blogButton;

    @FindBy(xpath = "\\a[text()='About']")
    WebElement aboutButton;

    @FindBy(xpath = "\\a[text()='Contact']")
    WebElement contactButton;

    @FindBy(xpath = "//span[text() = 'Login']/parent::a")
    WebElement loginButton;

    public WebElement ClosePopup()
    {
        return wait.until(ExpectedConditions.visibilityOf(closePopup));
    }
    public WebElement HomeButton()
    {
        return homeButton;
    }
    public WebElement CoursesButton()
    {
        return coursesButton;
    }
    public WebElement VideosButton()
    {
        return videosButton;
    }
    public WebElement GuideButton()
    {
        return guideButton;
    }
    public WebElement PracticeButton()
    {
        return practiceButton;
    }
    public WebElement BlogButton()
    {
        return blogButton;
    }
    public WebElement AboutButton()
    {
        return aboutButton;
    }
    public WebElement ContactButton()
    {
        return contactButton;
    }
    public WebElement LoginButton()
    {
        return loginButton;
    }
}
