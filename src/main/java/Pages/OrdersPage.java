package Pages;

import AbstractComponents.Abstract;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrdersPage extends Abstract {
    WebDriver driver;

    public OrdersPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //PageFactory Design
    @FindBy(css = " tr td:nth-child(3)")
    List<WebElement> orderNames;


    public boolean checkOrderPresence(String productName){
        return orderNames.stream().
                anyMatch(cartProduct-> cartProduct.getText().
                        equalsIgnoreCase(productName));
    }






}
