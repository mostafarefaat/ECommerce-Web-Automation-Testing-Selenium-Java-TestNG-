package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.Pages.LandingPage;
import org.Pages.ProductCataloguePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v139.network.Network;
import org.openqa.selenium.devtools.v139.network.model.ConnectionType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class SubmitOrderTest {

    static ChromeDriver driver;

    public static WebDriver init(){
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
        return driver;
    }

    public static void main(String[] args){

        init();

        String productName = "ZARA COAT 3";

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.loginApplication("SaA@gmail.com","Sa@123456");

        ProductCataloguePage cataloguePage = new ProductCataloguePage(driver);
        cataloguePage.addProductToCart(productName);
        cataloguePage.clickOnCart();



/*
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));

        boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));

        Assert.assertTrue(match);

        driver.findElement(By.xpath("//button[text()='Checkout']")).click();

        Actions action = new Actions(driver);

        action.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")),"Egypt").build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ta-results")));

        driver.findElement(By.xpath("//section/button/span[text()=' Egypt']")).click();

        driver.findElement(By.className("action__submit")).click();

        String confirmMsg = driver.findElement(By.className("hero-primary")).getText();
        Assert.assertEquals(confirmMsg,"THANKYOU FOR THE ORDER");

*/



    }

}
