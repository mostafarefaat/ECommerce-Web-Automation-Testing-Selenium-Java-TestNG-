package Pages;

import AbstractComponents.Abstract;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends Abstract {

    WebDriver driver;

    public LandingPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //PageFactory Design
    @FindBy(id = "userEmail")
    WebElement userEmailField;

    @FindBy(id = "userPassword")
    WebElement userPasswordField;

    @FindBy(id = "login")
    WebElement loginButton;

    @FindBy(css = "div[role='alert']")
    WebElement errorMsg;


    public void goTo(){
        driver.get("https://rahulshettyacademy.com/client");
    }

    private void setUserName(String userName){
        userEmailField.sendKeys(userName);
    }

    private void setUserPassword(String userPassword){
        userPasswordField.sendKeys(userPassword);
    }

    private void clickLogin(){
        loginButton.click();
    }

    public ProductCataloguePage loginApplication(String userName, String userPassword){
        setUserName(userName);
        setUserPassword(userPassword);
        clickLogin();
        return new ProductCataloguePage(driver);
    }

    public String getErrorMsg(){
        waitForWebElementToAppear(errorMsg);
        return errorMsg.getText();
    }
}
