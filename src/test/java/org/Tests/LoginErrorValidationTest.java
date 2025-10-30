package org.Tests;

import org.TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginErrorValidationTest extends BaseTest {

    @Test
    public void testInvalidEmail_ShowsErrorMessage() throws IOException{

        landingPage.loginApplication("Wrong@gmail.com","Sa@123456");
        Assert.assertEquals(landingPage.getErrorMsg(),"Incorrect email or password.");
    }

    @Test
    public void testInvalidPassword_ShowsErrorMessage() throws IOException {

        landingPage.loginApplication("SaA@gmail.com","Sa@12345678");
        Assert.assertEquals(landingPage.getErrorMsg(),"Incorrect email or password.");
    }
}
