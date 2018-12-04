package frontend.pageobjects;

import framework.base.BasePageMethods;
import org.openqa.selenium.By;

public class LoginPage extends BasePageMethods {

    //*********Web Elements*********
    By userName = By.cssSelector("#username_input");
    By passWord = By.cssSelector("#password_input");
    By loginButton = By.cssSelector("#login_button");

    //*********Page Methods*********

    public void logInWith(String username, String password) throws InterruptedException {

        sendKeysToElement(userName, username);
        sendKeysToElement(passWord, password);
        clickWebElement(loginButton);

    }

}
