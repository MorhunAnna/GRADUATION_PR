package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage{

    private By product = By.xpath("//a[@href='/ua/tunec-cap-africa-v-rastitel-nom-masle-160-g-919358/']");
    private By addToCartButton = By.xpath("//button[contains(@title, 'Додати до кошика')]");
    private By productPrice = By.xpath("//div[@class='productDetails_price_actual__12u8E']");
    private By productName = By.xpath("//h1[@class='product_title__2g5xV']");

    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void productPage() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(product));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
    }

    public void addToCart() {
        WebElement addToCartElement = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", addToCartElement);
    }

    public void scrollToProduct() {
        WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(product));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(productElement).perform();
    }

    public String getProductPrice() {
        WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
        return productElement.getText().replaceAll("\\s+", "");
    }

    public String getProductName(){
        WebElement productNameElement = webDriver.findElement(productName);
        return productNameElement.getText();
    }

}