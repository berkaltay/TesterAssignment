package frontend.pageobjects;

import framework.base.BasePageMethods;
import framework.extentFactory.ReportFactory;
import framework.utilities.Constants;
import org.openqa.selenium.By;
import org.testng.Assert;

public class SignUpPage extends BasePageMethods {

    //*********Web Elements*********
    By userName = By.cssSelector("#username_input");
    By passWord = By.cssSelector("#password_input");
    By name = By.cssSelector("#name_input");
    By email = By.cssSelector("#email_input");
    By day = By.id("day_select");
    By month = By.id("month_select");
    By year = By.id("year_select");
    By submitButton = By.cssSelector("#submit_button");
    By mainHeading = By.cssSelector("h1:contains(Your Profile)");

    //*********Page Methods*********

    public void fillFormForNewUser() {
        ReportFactory.getChildTest().info("Filling form...");
        sendKeysToElement(userName, Constants.USER);
        sendKeysToElement(passWord, Constants.PASSWORD);
        sendKeysToElement(name, Constants.NAME);
        sendKeysToElement(email, Constants.EMAIL);
        sendKeysToElementWithoutCleanInside(day, "1");
        sendKeysToElementWithoutCleanInside(month, "January");
        sendKeysToElementWithoutCleanInside(year, "2000");
        clickWebElement(submitButton);
        Assert.assertNotNull(mainHeading);
        ReportFactory.getChildTest().pass("Filled form with success!");
    }

}
