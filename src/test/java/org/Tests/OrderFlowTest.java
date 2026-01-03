package org.Tests;

import Pages.*;

import org.TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class OrderFlowTest extends BaseTest {


    @Test (dataProvider = "getData")
    public void shouldSubmitOrderSuccessfully_whenValidProductIsAdded(String email, String password, String productName, String countryName) throws IOException {


        ProductCataloguePage cataloguePage = landingPage.loginApplication(email,password);

        //Catalogue Page
        cataloguePage.addProductToCart(productName);
        CartPage cartPage = cataloguePage.goToCartPage();

        //Cart Page
        boolean match = cartPage.checkProductPresence(productName);
        Assert.assertTrue(match);

        //CheckOut Page
        CheckOutPage checkOutPage = cartPage.clickOnCheckOut();
        checkOutPage.selectEgyptCountry(countryName);
        ConfirmationPage confirmationPage = checkOutPage.clickOnPlaceOrder();

        //Confirmation Page
        String confirmMsg = confirmationPage.getConfirmationText();
        Assert.assertEquals(confirmMsg,"THANKYOU FOR THE ORDER.");

    }

    @Test ( dataProvider = "getData",dependsOnMethods = {"shouldSubmitOrderSuccessfully_whenValidProductIsAdded"})
    public void shouldVerifyOrderExistsInOrderHistory_afterSuccessfulSubmission(String email, String password,String productName, String countryName){
        ProductCataloguePage cataloguePage = landingPage.loginApplication(email,password);
        OrdersPage ordersPage = cataloguePage.goToOrdersPage();
        Assert.assertTrue(ordersPage.checkOrderPresence(productName));
    }

    @DataProvider
    public Object[][] getData(){
        return new Object[][] { {"SaA@gmail.com","Sa@123456","ZARA COAT 3","Egypt"},
                {"Test1@gmail.cc","Sa@123456","ADIDAS ORIGINAL","Egypt"} };
    }

}
