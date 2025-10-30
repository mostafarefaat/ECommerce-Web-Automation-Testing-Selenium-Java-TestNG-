package org.Tests;

import Pages.*;

import org.TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class SubmitOrderTest extends BaseTest {

    @Test
    public void submitOrder() throws IOException {

        String productName = "ZARA COAT 3";
        LandingPage landingPage = launchApplication();
        ProductCataloguePage cataloguePage = landingPage.loginApplication("SaA@gmail.com","Sa@123456");

        //Catalogue Page
        cataloguePage.addProductToCart(productName);
        CartPage cartPage = cataloguePage.goToCartPage();

        //Cart Page
        boolean match = cartPage.checkProductPresence(productName);
        Assert.assertTrue(match);

        //CheckOut Page
        CheckOutPage checkOutPage = cartPage.clickOnCheckOut();
        checkOutPage.selectEgyptCountry("Egypt");
        ConfirmationPage confirmationPage = checkOutPage.clickOnPlaceOrder();

        //Confirmation Page
        String confirmMsg = confirmationPage.getConfirmationText();
        Assert.assertEquals(confirmMsg,"THANKYOU FOR THE ORDER.");

    }

}
