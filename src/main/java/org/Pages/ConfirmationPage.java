package org.Pages;

import AbstractComponents.Abstract;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage extends Abstract {

    WebDriver driver;

    public ConfirmationPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //PageFactory Design
    @FindBy(className = "hero-primary")
    WebElement confirmMsg;

    public String getConfirmationText(){
       return confirmMsg.getText();
    }

}
