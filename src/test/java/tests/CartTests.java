package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CataloguePage;
import pages.ProductPage;
import pages.CartPage;
import io.qameta.allure.*;
import java.time.Duration;

public class CartTests extends Base {

    @Test(description = "1. Check adding a product to the cart from the product page ")
    @Feature("Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a product can be added to the cart from the product page.")

    public void testAddItemFromProductPage() {
        webDriver.get("https://auchan.ua/ua/?gad_source=1&gclid=EAIaIQobChMIvrXzuNTqhgMVZaWDBx193QrsEAAYASAAEgIDWvD_BwE");

        CataloguePage cataloguePage = new CataloguePage(webDriver);
        ProductPage productPage = new ProductPage(webDriver);
        CartPage cartPage = new CartPage(webDriver);

        cataloguePage.selectProductGroup();
        cataloguePage.selectProductSubgroup();
        productPage.scrollToProduct();
        productPage.productPage();
        productPage.addToCart();
        cartPage.headerCartButton();

        Assert.assertTrue(cartPage.isCartWindowDisplayed(), "The cart window is displayed.");
        Assert.assertTrue(cartPage.isOrderProductDisplayed(), "The product has been added to the cart.");
    }

    @Test(description = "2.Check if the number of products in bubble is displayed near the 'Cart' button")
    @Feature("Cart Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the cart bubble displays the correct number of products.")

    public void bubble() {
        webDriver.get("https://auchan.ua/ua/?gad_source=1&gclid=EAIaIQobChMIvrXzuNTqhgMVZaWDBx193QrsEAAYASAAEgIDWvD_BwE");

        CataloguePage cataloguePage = new CataloguePage(webDriver);
        ProductPage productPage = new ProductPage(webDriver);
        CartPage cartPage = new CartPage(webDriver);

        cataloguePage.selectProductGroup();
        cataloguePage.selectProductSubgroup();
        productPage.scrollToProduct();
        productPage.productPage();
        productPage.addToCart();

        Assert.assertTrue(cartPage.isCartBubbleDisplayed(), "Cart bubble is displayed.");
    }

    @Test(description = "3. Check the possibility to increase the number of product units in the cart")
    @Feature("Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the user can increase the number of units of a product in the cart.")

    public void testIncrementCounter() {
        webDriver.get("https://auchan.ua/ua/?gad_source=1&gclid=EAIaIQobChMIvrXzuNTqhgMVZaWDBx193QrsEAAYASAAEgIDWvD_BwE");

        CataloguePage cataloguePage = new CataloguePage(webDriver);
        ProductPage productPage = new ProductPage(webDriver);
        CartPage cartPage = new CartPage(webDriver);

        cataloguePage.selectProductGroup();
        cataloguePage.selectProductSubgroup();
        productPage.scrollToProduct();
        productPage.productPage();
        productPage.addToCart();
        cartPage.headerCartButton();

        By inputLocator = By.xpath("(//input[@class='counter_counter_value__9qibd'])[2]");
        WebElement inputElement = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(inputLocator));

        String initialValue = inputElement.getAttribute("value");

        cartPage.addOneMoreProductInCart();

        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.not(ExpectedConditions.attributeToBe(inputLocator, "value", initialValue)));

        String newValue = inputElement.getAttribute("value");

        Assert.assertEquals(Integer.parseInt(newValue), Integer.parseInt(initialValue) + 1);
    }

    @Test(description = "4.Check the possibility to reduce the number of product units in the cart", dependsOnMethods = "testIncrementCounter")
    @Feature("Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the user can decrease the number of units of a product in the cart.")

    public void testDecrementCounter() {
        CartPage cartPage = new CartPage(webDriver);

        By numberOfProducts = By.xpath("(//input[@class='counter_counter_value__9qibd'])[2]");
        WebElement quantityInput = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(numberOfProducts));

        String updatedValueAfterAdd = quantityInput.getAttribute("value");

        cartPage.removeProduct();

        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.not(ExpectedConditions.attributeToBe(numberOfProducts, "value", updatedValueAfterAdd)));

        String updatedValueAfterRemove = quantityInput.getAttribute("value");

        Assert.assertEquals(Integer.parseInt(updatedValueAfterRemove), Integer.parseInt(updatedValueAfterAdd) - 1);
    }

    @Test(description = "5. Checking that the total sum is correctly displayed")
    @Feature("Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)

    public void checkProductPriceInCatalogueAndCart() {
        webDriver.get("https://auchan.ua/ua/?gad_source=1&gclid=EAIaIQobChMIvrXzuNTqhgMVZaWDBx193QrsEAAYASAAEgIDWvD_BwE");

        CataloguePage cataloguePage = new CataloguePage(webDriver);
        ProductPage productPage = new ProductPage(webDriver);
        CartPage cartPage = new CartPage(webDriver);

        cataloguePage.selectProductGroup();
        cataloguePage.selectProductSubgroup();
        productPage.scrollToProduct();
        productPage.productPage();
        String productPriceInCatalogue = productPage.getProductPrice();
        System.out.println("Product price in catalogue: " + productPriceInCatalogue);

        productPage.addToCart();

        cartPage.headerCartButton();

        By cartProductPriceLocator = By.xpath("(//strong[@class='cart-content_total_wrap_value__1eHhv'])[2]");
        WebElement cartProductPriceElement = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(cartProductPriceLocator));

        String productPriceInCart = cartProductPriceElement.getText().replaceAll("\\s+", "");
        System.out.println("Product price in cart: " + productPriceInCart);

        Assert.assertEquals(productPriceInCart, productPriceInCatalogue);
    }

    @Test(description = "6. Removing products from the Cart")
    @Feature("Cart Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that products can be successfully removed from the cart.")

    public void removingProductFromCart() {
        webDriver.get("https://auchan.ua/ua/?gad_source=1&gclid=EAIaIQobChMIvrXzuNTqhgMVZaWDBx193QrsEAAYASAAEgIDWvD_BwE");

        CataloguePage cataloguePage = new CataloguePage(webDriver);
        ProductPage productPage = new ProductPage(webDriver);
        CartPage cartPage = new CartPage(webDriver);

        cataloguePage.selectProductGroup();
        cataloguePage.selectProductSubgroup();
        productPage.scrollToProduct();
        productPage.productPage();
        productPage.addToCart();
        cartPage.headerCartButton();
        cartPage.removeProductFromCart();


        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement emptyCartLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-modal_empty_text__Foc65")));

        boolean isCartEmpty = emptyCartLocator.isDisplayed();

        System.out.println("Is cart empty: " + isCartEmpty);
        Assert.assertTrue(isCartEmpty, "The cart should be empty.");
    }

    @Test(description = "7.  Verify product name in cart matches product name on product page")
    @Feature("Cart Functionality")
    @Severity(SeverityLevel.NORMAL)

    public void verifyProductNameInCart() {
        webDriver.get("https://auchan.ua/ua/?gad_source=1&gclid=EAIaIQobChMIvrXzuNTqhgMVZaWDBx193QrsEAAYASAAEgIDWvD_BwE");

        CataloguePage cataloguePage = new CataloguePage(webDriver);
        ProductPage productPage = new ProductPage(webDriver);
        CartPage cartPage = new CartPage(webDriver);

        cataloguePage.selectProductGroup();
        cataloguePage.selectProductSubgroup();

        productPage.scrollToProduct();
        productPage.productPage();

        String productNameOnPage = productPage.getProductName();

        productPage.addToCart();
        cartPage.headerCartButton();

        String productNameInCart = cartPage.getProductNameInCart();

        System.out.println("Product name on page: " + productNameOnPage);
        System.out.println("Product name in cart: " + productNameInCart);

        Assert.assertEquals(productNameInCart, productNameOnPage, "The product name in the cart matches the product name on the product page.");
    }

}
