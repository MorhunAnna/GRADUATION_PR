package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;

public class CheckoutPage extends BasePage {

    private By checkoutButton = By.xpath("(//a[@class='PrimaryButton_primaryButton__3eQ1J PrimaryButton_sm__1awpW PrimaryButton_red__28TdX cart-content_total_button__2SAmk' and @href='/ua/checkout/'])[2]");
    private By addRelatedProductButton = By.xpath("(//button[contains(@class, 'checkout-area_product_buttons__buy__hw5qs')]) [1]");
    private By orderTotalSum = By.xpath("//strong[@class='orderContent_footer__details__total_value__1bTcp orderContent_total__2UaZB']");
    private By priceOfRelatedProduct =By.xpath("//div[contains(@class, 'checkout-area_product_price')]/strong");
    private By addRelatedProductButtonSecond = By.xpath("(//button[contains(@class, 'checkout-area_product_buttons__buy__hw5qs')]) [2]");

    public CheckoutPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickCheckoutButton() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        WebElement checkoutPage = wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
        checkoutPage.click();
    }

    public void scrollToRelatedProduct() {
        WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(addRelatedProductButtonSecond));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(productElement).perform();
    }

    public void addRelatedProd() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        WebElement relatedProd = wait.until(ExpectedConditions.visibilityOfElementLocated(addRelatedProductButton));
        relatedProd.click();
    }

    public String getRelatedProductPrice() {
        WebElement relatedProductPriceElement = webDriver.findElement(priceOfRelatedProduct);
        return relatedProductPriceElement.getText();
    }

    public String getTotalOrderSum() {
        WebElement totalSumElement = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderTotalSum));
        return totalSumElement.getText();
    }
}
