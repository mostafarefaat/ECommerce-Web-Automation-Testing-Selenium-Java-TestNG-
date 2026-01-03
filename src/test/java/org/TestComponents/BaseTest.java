package org.TestComponents;

import Pages.LandingPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v139.network.Network;
import org.openqa.selenium.devtools.v139.network.model.ConnectionType;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BaseTest {

    private WebDriver driver;
    public LandingPage landingPage;

    private WebDriver initializeDriver() throws IOException {

        //Load Properties File and Extract browser Name
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//Resources//GlobalData.properties");
        properties.load(fis);
        String browser = properties.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome") ){

            WebDriverManager.chromedriver().setup();
            //Set Chrome Options if needed
            ChromeOptions options = new ChromeOptions();

            driver = new ChromeDriver(options);

            //Access DevTools
            DevTools devTools = ((ChromeDriver) driver).getDevTools();
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

        }

        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            driver = new FirefoxDriver(options);
        }

        else if (browser.equalsIgnoreCase("edge")){
            System.out.println("Launching Edge browser...");
            System.out.println("Browser from properties file = " + browser);
            WebDriverManager.edgedriver().setup();

            EdgeOptions options = new EdgeOptions();
            options.addArguments("--remote-allow-origins=*");

            driver = new EdgeDriver(options);
        }

        else {
            throw new RuntimeException("❌ Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;

    }



    protected List<HashMap<String,String>> getJsonDataToMap(String filePath) throws IOException {

        //Read Json to String
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

        // Convert String to HashMap
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });

    }


    @BeforeMethod (alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        //Landing Page
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // closes browser + ends WebDriver session
            System.out.println("Driver closed successfully!");
        }
    }

}
