package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CataloguePage extends BasePage {

    private By productGroupLocator = By.xpath("//a[@href='/ua/produkty-pitanija/']");
    private By productSubgroupLocator = By.xpath("//a[@href='/ua/bakaleya/']");

    public CataloguePage(WebDriver webDriver) {

        super(webDriver);
    }

    public void selectProductGroup() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productGroupLocator));
        WebElement productGroupElement = wait.until(ExpectedConditions.elementToBeClickable(productGroupLocator));
        productGroupElement.click();
    }

    public void selectProductSubgroup() {
        WebElement productSubgroup = wait.until(ExpectedConditions.elementToBeClickable(productSubgroupLocator));
        productSubgroup.click();
    }
}
