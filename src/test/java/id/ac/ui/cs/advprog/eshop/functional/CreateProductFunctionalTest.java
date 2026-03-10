package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(ChromeDriver driver) throws Exception {
        baseUrl = String.format("%s:%d/product", testBaseUrl, serverPort);

        // Input data
        String createProductUrl = String.format("%s/create", baseUrl);
        driver.get(createProductUrl);

        String productName = "Six sevenn";
        String productQuantity = "67";

        WebElement productNameField = driver.findElement(By.id("nameInput"));
        productNameField.clear();
        productNameField.sendKeys(productName);

        WebElement productQuantityField = driver.findElement(By.id("quantityInput"));
        productQuantityField.clear();
        productQuantityField.sendKeys(productQuantity);

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();
    }

    @AfterEach
    void clearTest(ChromeDriver driver){
        String productListUrl = String.format("%s/list", baseUrl);
        driver.get(productListUrl);

        List<WebElement> listDeleteProductButton = driver.findElements(By.id("deleteProductButton"));
        for (WebElement deleteProductButton : listDeleteProductButton) {
            deleteProductButton.click();

            Alert confirmButton = driver.switchTo().alert();
            confirmButton.accept();
        }
    }

    @Test
    void testCreateProductAndCheckInList(ChromeDriver driver) throws Exception {
        String productName = "Six sevenn";
        String productQuantity = "67";

        // View list
        String productListUrl = String.format("%s/list", baseUrl);
        driver.get(productListUrl);

        List<WebElement> productRow = driver.findElements(By.tagName("td"));
        String foundProductName = productRow.get(0).getText();
        String foundProductQuantity = productRow.get(1).getText();

        assertEquals(productName, foundProductName);
        assertEquals(productQuantity, foundProductQuantity);
    }

    @Test
    void testCreateAndEdit(ChromeDriver driver) throws Exception {
        String productListUrl = String.format("%s/list", baseUrl);
        driver.get(productListUrl);

        String updatedProductName = "Seven sixx";
        String updatedProductQuantity = "76";

        WebElement editProductButton = driver.findElement(By.id("editProductButton"));
        editProductButton.click();

        WebElement productNameField = driver.findElement(By.id("nameInput"));
        productNameField.clear();
        productNameField.sendKeys(updatedProductName);

        WebElement productQuantityField = driver.findElement(By.id("quantityInput"));
        productQuantityField.clear();
        productQuantityField.sendKeys(updatedProductQuantity);

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        // View list
        List<WebElement> productRow = driver.findElements(By.tagName("td"));
        String foundProductName = productRow.get(0).getText();
        String foundProductQuantity = productRow.get(1).getText();

        assertEquals(updatedProductName, foundProductName);
        assertEquals(updatedProductQuantity, foundProductQuantity);
    }

    @Test
    void testCreateAndDelete(ChromeDriver driver) throws Exception {
        String productListUrl = String.format("%s/list", baseUrl);
        driver.get(productListUrl);

        WebElement deleteProductButton = driver.findElement(By.id("deleteProductButton"));
        deleteProductButton.click();

        Alert confirmButton = driver.switchTo().alert();
        confirmButton.accept();

        List<WebElement> productRow = driver.findElements(By.tagName("td"));
        assertTrue(productRow.isEmpty());
    }
}
