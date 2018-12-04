package frontend.pageobjects;

import framework.base.BasePageMethods;
import framework.extentFactory.ReportFactory;
import org.openqa.selenium.By;

public class WelcomePage extends BasePageMethods {

    String username = null;
    By logOutButton = By.xpath("//a[contains(text(),'log out')]");
    By profileSection = By.cssSelector("#profile_link");
    By detailsSection = By.cssSelector("#details_link");
    By loggedInUserInformation = By.xpath("//div[@id='status']");
    By loggedInAsAdminText = By.xpath("//div[@id='status' and ./p[contains(.,'admin')]]");
    By loggedInAsDevText = By.xpath("//div[@id='status' and ./p[contains(.,'dev')]]");
    By loggedInAsTesterText = By.xpath("//div[@id='status' and ./p[contains(.,'test')]]");

    public void returnLoggedInUser() {

        if (isElementPresent(loggedInAsAdminText,10))
            ReportFactory.getChildTest().info("Logged in as Admin");
        else if (isElementPresent(loggedInAsDevText,10))
            ReportFactory.getChildTest().info("Logged in as Dev");
        else if (isElementPresent(loggedInAsTesterText,10))
            ReportFactory.getChildTest().info("Logged in as Tester");
        else {
            ReportFactory.getChildTest().warning("Not ready for a new user!!");
        }
    }

    public void whoIsLoggedIn() {

        if(driver.findElement(loggedInUserInformation).getText().contains("admin"))
            ReportFactory.getChildTest().info("Logged in as Admin");
        else if(driver.findElement(loggedInUserInformation).getText().contains("dev"))
            ReportFactory.getChildTest().info("Logged in as Dev");
        else if(driver.findElement(loggedInUserInformation).getText().contains("test"))
            ReportFactory.getChildTest().info("Logged in as Tester");
        else {
            ReportFactory.getChildTest().warning("Not ready for a new user!!");
        }
    }

    public void logOut() {
        clickWebElement(logOutButton);
        ReportFactory.getChildTest().info("User logged self out");
    }

    public void switchTab(String title) {

            switch (title){
                case "details":
                    clickWebElement(detailsSection);
                    break;
                case "profile":
                    clickWebElement(profileSection);
                    break;
            }

        }
    }


