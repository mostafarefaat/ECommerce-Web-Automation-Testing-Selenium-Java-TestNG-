package Pages;

import AbstractComponents.Abstract;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOutPage extends Abstract {

    WebDriver driver;

    public CheckOutPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    By countryResults = By.className("ta-results");

    @FindBy(css = "input[placeholder='Select Country']")
    WebElement countryField;

    @FindBy(xpath = "//section/button/span[text()=' Egypt']")
    WebElement selectEgypt;

    @FindBy(className = "action__submit")
    WebElement placeOrderButton;

    public void selectEgyptCountry(String country){
        Actions action = new Actions(driver);
        action.sendKeys(countryField,country).build().perform();
        waitForElementToAppear(countryResults);
        selectEgypt.click();
    }

    public ConfirmationPage clickOnPlaceOrder(){
        placeOrderButton.click();
        return new ConfirmationPage(driver);
    }

}
