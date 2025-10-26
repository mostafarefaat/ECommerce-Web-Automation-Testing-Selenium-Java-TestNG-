package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.Pages.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v139.network.Network;
import org.openqa.selenium.devtools.v139.network.model.ConnectionType;
import org.testng.Assert;


import java.time.Duration;
import java.util.Optional;

public class SubmitOrderTest {

    static ChromeDriver driver;

    public static void init(){
        WebDriverManager.chromedriver().setup();

        //Set Chrome Options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

         driver = new ChromeDriver(options);

        //Access DevTools
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Network.emulateNetworkConditions(
                false,                           // Not Offline
                500,                                    //  Latency in ms
                25000,                                  // Download Throughput (Bytes/sec)
                15000,                                  // Upload Throughput (Bytes/sec)
                Optional.of(ConnectionType.CELLULAR2G), // Correct Enum
                Optional.empty(),                       // Packet Loss
                Optional.empty(),                       // PacketQueueLength
                Optional.empty()                        // PacketReordering

        ));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static void main(String[] args){

        init();

        String productName = "ZARA COAT 3";

        //Landing Page
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCataloguePage cataloguePage = landingPage.loginApplication("SaA@gmail.com","Sa@123456");

        //Catalogue Page
        cataloguePage.addProductToCart(productName);
        CartPage cartPage = cataloguePage.goToCartPage();

        //Cart Page
        boolean match = cartPage.checkProductPresence(productName);
        Assert.assertTrue(match);
        CheckOutPage checkOutPage = cartPage.clickOnCheckOut();
        checkOutPage.selectEgyptCountry("Egypt");
        ConfirmationPage confirmationPage = checkOutPage.clickOnPlaceOrder();

        //Confirmation Page
        String confirmMsg = confirmationPage.getConfirmationText();
        Assert.assertEquals(confirmMsg,"THANKYOU FOR THE ORDER.");

    }

}
