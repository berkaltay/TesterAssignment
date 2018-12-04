package frontend.testclass;

import framework.base.TestBase;
import framework.utilities.Constants;
import frontend.pageobjects.HomePage;
import frontend.pageobjects.LoginPage;
import frontend.pageobjects.SignUpPage;
import frontend.pageobjects.WelcomePage;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class WaesFrontEndTest extends TestBase {

    @Test(description = "Front End scenario for Login")
    public void testWaesFrontEndLogin() throws Exception {

        //*************PAGE INSTANTIATIONS*************

        WelcomePage welcomePage = new WelcomePage();
        HomePage homePage = new HomePage();
        LoginPage loginPage = new LoginPage();

        //*************TEST METHODS********************

        assertThat("Should verify Elements", homePage, homePage.verifyHomePageObjects());
        homePage.clickLogInSection();
        loginPage.logInWith(Constants.ADMINUSER, Constants.ADMINPASSWORD);
        welcomePage.whoIsLoggedIn();
        welcomePage.switchTab("details");
        welcomePage.switchTab("profile");
        welcomePage.logOut();
    }

    @Test(description = "Front End scenario for Sign Up")
    public void testWaesFrontEndSignIp() throws Exception {

        //*************PAGE INSTANTIATIONS*************

        HomePage homePage = new HomePage();
        SignUpPage signUpPage = new SignUpPage();

        //*************TEST METHODS********************

        assertThat("Should verify Elements", homePage, homePage.verifyHomePageObjects());
        homePage.clickSignUpSection();
        signUpPage.fillFormForNewUser();

    }
}
