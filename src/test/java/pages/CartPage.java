package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CartPage extends BasePage {

    private By headerCartButtonLocator = By.xpath("//button[@class='cart-toggle__btn']");
    private By cartWindowLocator = By.xpath("//div[@class='cart-modal_root__XEE_Z']");
    private By cartBubbleLocator = By.xpath("//span[contains(@class, 'cart-toggle__btn')]");
    private By orderProductLocator = By.xpath("//div[contains(@class,'cart-modal_list__3m_l4')]");
    private By plusButton = By.xpath("(//button[@class='counter_counter_button__2ekdX'])[4]");
    private By minusButton = By.xpath("(//button[@class='counter_counter_button__2ekdX'])[3]");
    private By trashCanButton = By.xpath("(//button[@class='order-product_order_product__remove_btn__2wdX9'])[2]");
    private By productNameCart = By.xpath("//a[contains(text(), 'Тунець Cap Africa в олії, 160 г')]");

    public CartPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void headerCartButton() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement headerCartButtonEl = wait.until(ExpectedConditions.elementToBeClickable(headerCartButtonLocator));
        headerCartButtonEl.click();
    }

    public boolean isCartWindowDisplayed() {
        WebElement cartWindow = wait.until(ExpectedConditions.visibilityOfElementLocated(cartWindowLocator));
        return cartWindow.isDisplayed();
    }

    public boolean isCartBubbleDisplayed() {
        WebElement cartBubble = wait.until(ExpectedConditions.visibilityOfElementLocated(cartBubbleLocator));
        return cartBubble.isDisplayed();
    }

    public boolean isOrderProductDisplayed() {
        WebElement orderProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(orderProductLocator));
        return orderProduct.isDisplayed();
    }

    public void addOneMoreProductInCart() {
        WebElement plus = wait.until(ExpectedConditions.visibilityOfElementLocated(plusButton));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", plus);
    }

    public void removeProduct() {
        WebElement minus = wait.until(ExpectedConditions.visibilityOfElementLocated(minusButton));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", minus);
    }

    public void removeProductFromCart() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement trashCan = wait.until(ExpectedConditions.elementToBeClickable(trashCanButton));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", trashCan);
    }

    public String getProductNameInCart(){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement productNameElementCart = wait.until(ExpectedConditions.visibilityOfElementLocated(productNameCart));
        return productNameElementCart.getText();
    }
}
