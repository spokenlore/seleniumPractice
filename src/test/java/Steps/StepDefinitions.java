package Steps;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class StepDefinitions {
    private static Configurations configurations = new Configurations();
    private static Configuration config;
    private static WebDriver driver;
    private static WebDriverWait standardWait;

    private static Configuration importProperties() {
        Configuration config = null;
        try {
            Path path = Paths.get("");
            config = configurations.properties(path.toAbsolutePath() + "/src/test/resources/app.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return config;
    }

    private static void initDriver() {
        Path path = Paths.get("");
        System.setProperty("webdriver.chrome.driver", path.toAbsolutePath() + "/src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        standardWait = new WebDriverWait(driver, 5L);
    }

    private static void closeDriver() {
        driver.close();
    }

    // Test 1 Code

    private static void goToSignupPage() {
        driver.get(config.getString("signup_page.url"));
    }

    private static void clickJoinButton() {
        WebElement joinButton = standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.joinButton"))));
        joinButton.click();
    }

    private static void locateSignupFormFields() {
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.firstName"))));
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.lastName"))));
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.email"))));
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.password"))));
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.checkbox"))));
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.registerButton"))));
    }

    private static void enterFirstName(String firstName) {
        WebElement firstNameInput = standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.firstName"))));
        firstNameInput.sendKeys(firstName);
    }

    private static void enterLastName(String lastName) {
        WebElement lastNameInput = standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.lastName"))));
        lastNameInput.sendKeys(lastName);
    }

    private static void enterEmail(String email) {
        WebElement emailInput = standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.email"))));
        emailInput.sendKeys(email);
    }

    private static void enterPassword(String password) {
        WebElement passwordInput = standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.password"))));
        passwordInput.sendKeys(password);
    }

    private static void clickCheckbox() {
        WebElement checkbox = standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.checkbox"))));
        checkbox.click();
    }

    private static void clickRegisterButton() {
        WebElement registerButton = standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.registerButton"))));
        registerButton.click();
    }

    private static void seeConfirmationPage() {
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("signup_page.confirmationPage"))));
    }

    // Test 2 Code

    private static void goToLoginPage() {
        driver.get(config.getString("login_page.url"));
    }

    private static void enterLoginUsername(String username) {
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("login_page.username"))))
                .sendKeys(username);
    }

    private static void enterLoginPassword(String password) {
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("login_page.password"))))
                .sendKeys(password);
    }

    private static void clickLoginButton() {
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("login_page.loginButton"))))
                .click();
    }

    private static void clickFindTalentButton() {
        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("login_page.findTalent"))))
                .click();
    }

    private static void searchFor(String searchCriteria) {
        WebElement searchBox = standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("talent_page.searchBox"))));
        searchBox.sendKeys(searchCriteria);
        searchBox.sendKeys(Keys.RETURN);
    }

    private static void confirmResultsContain(String expectedText) {
//         to wait for search results to finish loading
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        standardWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getString("talent_page.result"))));

        List<WebElement> resultList = driver.findElements(By.xpath(config.getString("talent_page.result")));
        System.out.println("Length of Results array = " + resultList.size());

        for (WebElement result : resultList) {
            assertTrue(result.findElement(By.xpath(config.getString("talent_page.resultName")))
                    .getText().toLowerCase().contains(expectedText.toLowerCase())
                    || result.findElement(By.xpath(config.getString("talent_page.resultAddress")))
                    .getText().toLowerCase().contains(expectedText.toLowerCase()));
        }
    }

    @Test
    private static void test1() {
        try {
            config = importProperties();
            initDriver();
            goToSignupPage();
            clickJoinButton();
            locateSignupFormFields();
            enterFirstName("Samson");
            enterLastName("Fung");
            enterEmail("asdfjaosidfj@abcsd.com");
            // For some reason abc12345!@# doesn't even work even though it appears to fulfill the password requirements
            enterPassword("abc12345!@#ABC");
            clickCheckbox();
            clickRegisterButton();
            seeConfirmationPage();
            closeDriver();
        } catch (Exception e) {
            e.printStackTrace();
            closeDriver();
        }
    }

    @Test
    private static void test2() {
        try {
            config = importProperties();
            initDriver();
            goToLoginPage();
            enterLoginUsername("qa+candidatetest@workmarket.com");
            enterLoginPassword("candidate123");
            clickLoginButton();
            clickFindTalentButton();
            searchFor("test");
            confirmResultsContain("test");
        } catch (Exception e) {
            e.printStackTrace();
            closeDriver();
        }
    }

    public static void main(String... args) {
        try {
            test1();
            closeDriver();
        } catch (Exception e){
            e.printStackTrace();
            closeDriver();
        }
        try {
            test2();
            closeDriver();
        } catch (Exception e){
            e.printStackTrace();
            closeDriver();
        }
    }
}
