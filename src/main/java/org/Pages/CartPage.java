package org.Pages;

import AbstractComponents.Abstract;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends Abstract {
    WebDriver driver;

    public CartPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    By countryResults = By.className("ta-results");

    //PageFactory Design
    @FindBy(css = ".cartSection h3")
    List<WebElement> cartProducts;

    @FindBy(xpath = "//button[text()='Checkout']")
    WebElement checkOutButton;

    @FindBy(css = "input[placeholder='Select Country']")
    WebElement countryField;

    @FindBy(xpath = "//section/button/span[text()=' Egypt']")
    WebElement egyptValue;

    @FindBy(className = "action__submit")
    WebElement placeOrderButton;

    public boolean checkProductPresence(String productName){
        return cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
    }

    public void clickOnCheckOut(){
        checkOutButton.click();
    }

    public void selectEgyptCountry(){
        Actions action = new Actions(driver);
        action.sendKeys(countryField,"Egypt").build().perform();
        waitForElementToAppear(countryResults);
        egyptValue.click();
    }

    public void clickOnPlaceOrder(){
        placeOrderButton.click();
    }



}
