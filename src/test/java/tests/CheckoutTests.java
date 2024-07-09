package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CataloguePage;
import pages.CheckoutPage;
import pages.ProductPage;

import java.time.Duration;

public class CheckoutTests extends Base {

    @Test(description = "8. Redirection to the Checkout page")
    @Feature("Checkout Process")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify redirection to the Checkout page from the cart")

    public void redirectionToCheckoutPage() {
        webDriver.get("https://auchan.ua/ua/?gad_source=1&gclid=EAIaIQobChMIvrXzuNTqhgMVZaWDBx193QrsEAAYASAAEgIDWvD_BwE");

        CataloguePage cataloguePage = new CataloguePage(webDriver);
        ProductPage productPage = new ProductPage(webDriver);
        CartPage cartPage = new CartPage(webDriver);
        CheckoutPage checkoutPage = new CheckoutPage(webDriver);

        cataloguePage.selectProductGroup();
        cataloguePage.selectProductSubgroup();
        productPage.scrollToProduct();
        productPage.productPage();
        productPage.addToCart();
        cartPage.headerCartButton();

        checkoutPage.clickCheckoutButton();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement checkoutPageLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Оформлення замовлення')]")));
        boolean isCheckoutPageDisplayed = checkoutPageLocator.isDisplayed();

        Assert.assertTrue(isCheckoutPageDisplayed, "The checkout page is displayed.");
    }

    @Test(description = "9. Adding products on the checkout page", dependsOnMethods = "redirectionToCheckoutPage")
    @Feature("Checkout Process")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify the addition of related products on the checkout page")

    public void addingRelatedGoods() {
        CheckoutPage checkoutPage = new CheckoutPage(webDriver);

        checkoutPage.scrollToRelatedProduct();
        checkoutPage.addRelatedProd();

        WebElement relatedProductLocator = webDriver.findElement(By.xpath("(//div[@class='order-product_order_product__body__1-hTc']) [2]"));
        boolean isRelatedProductDisplayed = relatedProductLocator.isDisplayed();

        Assert.assertTrue(isRelatedProductDisplayed, "The related product is added to the order.");
    }

    @Test(description = "10. Adding products on the checkout page", dependsOnMethods ="redirectionToCheckoutPage")
    @Feature("Checkout Process")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify the total order sum after adding a related product on the checkout page")

    public void checkTotalSumAfterAddingProduct() {
        CheckoutPage checkoutPage = new CheckoutPage(webDriver);

        String totalSumBefore = checkoutPage.getTotalOrderSum();
        System.out.println("Total sum before adding related product: " + totalSumBefore);

        String relatedProductPriceStr = checkoutPage.getRelatedProductPrice();
        double relatedProductPrice = Double.parseDouble(relatedProductPriceStr.replaceAll("[^0-9,]", "").replace(",", "."));
        System.out.println("Related product price: " + relatedProductPrice);

        checkoutPage.scrollToRelatedProduct();
        checkoutPage.addRelatedProd();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//strong[@class='orderContent_footer__details__total_value__1bTcp orderContent_total__2UaZB']"), totalSumBefore)));

        String totalSumAfter = checkoutPage.getTotalOrderSum();
        System.out.println("Total sum after adding related product: " + totalSumAfter);

        double sumBefore = Double.parseDouble(totalSumBefore.replaceAll("[^0-9,]", "").replace(",", "."));
        double sumAfter = Double.parseDouble(totalSumAfter.replaceAll("[^0-9,]", "").replace(",", "."));

        double expectedSumAfter = sumBefore + relatedProductPrice;
        Assert.assertEquals(sumAfter, expectedSumAfter, 0.1, "The total order sum should increase correctly after adding a related product.");
    }
}
