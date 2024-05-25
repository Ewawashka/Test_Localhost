package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static helpers.Properties.testProperties;

public class ProfilePage extends AbstractPage {
    protected By profileButton = By.xpath("/div[@class='navbar-end']//a[contains(text(),'Profile')]");
    protected By logoutButton = By.xpath("//a[@href='/logout']");
    protected By profileTitle = By.xpath("//h1[@class = 'title']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickOnLogoutButton() {
        driver.findElement(logoutButton).click();
        waitElement.until(ExpectedConditions.urlContains(testProperties.localhostUrl()));
        return new HomePage(driver);
    }

    public String getTitleText() {
        return driver.findElement(profileTitle).getText();
    }

    @Override
    protected <T extends AbstractPage> T openPage() {
        return null;
    }
}
