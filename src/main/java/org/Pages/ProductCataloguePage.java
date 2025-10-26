package org.Pages;

import AbstractComponents.Abstract;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCataloguePage extends Abstract {

    WebDriver driver;

    public ProductCataloguePage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    By productsBy = By.cssSelector(".mb-3");
    By loginConfirmMsg = By.cssSelector("[aria-label*='Login Successfully']");
    By addToCart = By.xpath("//button[2]");
    By toastMsg = By.id("toast-container");
    By spinner = By.cssSelector("ngx-spinner.ng-tns-c31-1");

    //PageFactory Design
    @FindBy(css = ".mb-3")
    List<WebElement> catalogueProducts;


    public List<WebElement> getProductsList(){
        waitForElementToAppear(loginConfirmMsg);
        waitForElementToAppear(productsBy);
        return catalogueProducts;
    }

    public WebElement getProductByName(String productName){
        return getProductsList().stream().
                filter(prod->prod.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
    }

    public void addProductToCart(String productName){

        getProductByName(productName).findElement(addToCart).click();
        waitForElementToAppear(toastMsg);
        waitForElementToDisappear(spinner);

    }
}
