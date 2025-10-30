package org.Tests;

import Pages.*;

import org.TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OrderFlowTest extends BaseTest {

    String productName = "ZARA COAT 3";

    @Test
    public void shouldSubmitOrderSuccessfully_whenValidProductIsAdded() throws IOException {


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

    @Test (dependsOnMethods = {"shouldSubmitOrderSuccessfully_whenValidProductIsAdded"})
    public void shouldVerifyOrderExistsInOrderHistory_afterSuccessfulSubmission(){
        ProductCataloguePage cataloguePage = landingPage.loginApplication("SaA@gmail.com","Sa@123456");
        OrdersPage ordersPage = cataloguePage.goToOrdersPage();
        Assert.assertTrue(ordersPage.checkOrderPresence(productName));
    }

}
