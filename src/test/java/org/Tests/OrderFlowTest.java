package org.Tests;

import Pages.*;
import org.TestComponents.BaseTest;
import org.TestComponents.Retry;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OrderFlowTest extends BaseTest {

    @Test (dataProvider = "getData", retryAnalyzer = Retry .class)
    public void shouldSubmitOrderSuccessfully_whenValidProductIsAdded(HashMap<Object,Object> input){

        ProductCataloguePage cataloguePage = landingPage.loginApplication(input.get("email"),input.get("password"));

        //Catalogue Page
        cataloguePage.addProductToCart(input.get("productName"));
        CartPage cartPage = cataloguePage.goToCartPage();

        //Cart Page
        boolean match = cartPage.checkProductPresence(input.get("productName"));
        Assert.assertTrue(match);

        //CheckOut Page
        CheckOutPage checkOutPage = cartPage.clickOnCheckOut();
        checkOutPage.selectEgyptCountry(input.get("country"));
        ConfirmationPage confirmationPage = checkOutPage.clickOnPlaceOrder();

        //Confirmation Page
        String confirmMsg = confirmationPage.getConfirmationText();
        Assert.assertEquals(confirmMsg,"THANKYOU FOR THE ORDER.");

    }

    @Test ( dataProvider = "getData", retryAnalyzer = Retry .class)
    public void shouldVerifyOrderExistsInOrderHistory_afterSuccessfulSubmission(HashMap<Object,Object> input){
        ProductCataloguePage cataloguePage = landingPage.loginApplication(input.get("email"),input.get("password"));
        OrdersPage ordersPage = cataloguePage.goToOrdersPage();
        Assert.assertTrue(ordersPage.checkOrderPresence(input.get("productName")));
    }

    @DataProvider
    public Object[][] getData() throws IOException {

        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+
                "//src//main//java//resources//PurchaseOrder.json");

        return new Object[][] { {data.get(0)},{data.get(1)} };
    }

}
