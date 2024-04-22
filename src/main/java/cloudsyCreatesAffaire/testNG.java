package cloudsyCreatesAffaire;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(CustomTestListener.class)
public class testNG {

    // Utility method to generate a random string
    static String RequiredString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder s = new StringBuilder(n);
        int y;
        for (y = 0; y < n; y++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            s.append(AlphaNumericString.charAt(index));
        }
        return s.toString();
    }

    int upper = 1000;
    int lower = 100;
    int randomNumber = (int) (Math.random() * (upper - lower)) + lower;

    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String randomVerification = formattedDate + " " + randomNumber + " " + RequiredString(6) + " " + "testCreationAffaireAutomatique";
    ChromeDriver driver = null;

    @BeforeTest
    public void preConfigure() {
        System.out.println("Test started !!!");
        System.out.println("random string is : " + randomVerification);
        WebDriverManager.chromedriver().driverVersion("123").setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testOpenCloudsys() {
        System.out.println("openCloudsys started !!!");
        driver.get("http://92.205.22.177:8080/apex/f?p=111:10001:::NO:RP,10001::");
        System.out.println("openCloudsys ended !!!");

        WebDriverWait webdwait = new WebDriverWait(driver, Duration.ofSeconds(15));
        webdwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("P10001_USERNAME")));
        System.out.println("login started !!!");
        //driver.findElement(By.id("P10001_USERNAME")).sendKeys("ERP");
        WebElement usernameField = driver.findElement(By.id("P10001_USERNAME"));
        usernameField.sendKeys("ERP");

        Assert.assertTrue(usernameField.getAttribute("value").equals("ERP"), "The input field should contain the value 'ERP'.");

        driver.findElement(By.id("P10001_PASSWORD")).sendKeys("4444");
        driver.findElement(By.id("P101_LOGIN")).click();
        System.out.println("login ended !!!");

    }

    @Test
    public void testOpenCommerciale() {
        System.out.println("openCommerciale started !!!");
        WebElement secondToggleElement = driver.findElement(By.xpath("(//span[@class='a-TreeView-toggle'])[2]"));
        secondToggleElement.click();
        driver.findElement(By.id("t_TreeNav_15")).click();
        System.out.println("openCommerciale ended !!!");

    }

    @Test
    public void testCreateAffaire() {
        System.out.println("createAffaiere started !!!");

        // Click on the button to create Affaire
        WebDriverWait waitCreateAffaireBtn = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btnCreateAffaire = waitCreateAffaireBtn.until(ExpectedConditions.elementToBeClickable(By.id("B11386816663854057")));
        btnCreateAffaire.click();

        // Click on the element in the pop-up window
        WebDriverWait waitElementInPopup = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = waitElementInPopup.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'a-Button--popupLOV')]")));
        element.click();

        // Switch to the pop-up window
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();
        while (iterator.hasNext()) {
            String childWindow = iterator.next();
            driver.switchTo().window(childWindow);
            System.out.println(driver.getTitle() + " opened");
            if (driver.getTitle().equals("Search Dialog")) {
                // Search for an element
                driver.findElement(By.id("SEARCH")).sendKeys("stn");
                WebElement searchButton = driver.findElement(By.xpath("//input[@type='button' and @value='Search']"));
                searchButton.click();
                // Click on a link
                WebElement linkElement = driver.findElement(By.xpath("//a[contains(text(), 'Les Travaux du Nord (STN)')]"));
                linkElement.click();
                break;
            }
        }

        // Switch back to the main window
        Set<String> windowHandles2 = driver.getWindowHandles();
        for (String windowHandle : windowHandles2) {
            if (driver.switchTo().window(windowHandle).getTitle().equals("Mise à jour information affaires")) {
                break;
            }
        }

        // Enter some data and perform clicks
        driver.findElement(By.id("P117_DESCRIPTION_BESOIN")).sendKeys(randomVerification);
        driver.findElement(By.id("B151988927049331746")).click();
        driver.findElement(By.id("B153509924327533811")).click();

        System.out.println("createAffaire ended !!!");
    }


    @Test
    public void testSearchAffaire() {
        System.out.println("searchAffaire started ");
        //driver.navigate().refresh();
        driver.findElement(By.id("B151989358334331746")).click();
        driver.findElement(By.id("R11368507481813501_search_field")).sendKeys(randomVerification);
        driver.findElement(By.id("R11368507481813501_search_button")).click();
        
        
        /*
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       
        WebElement tdElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='11368620578813503']//td[contains(text(), '" +randomVerification+"')]")));

        Assert.assertTrue(tdElement.isDisplayed(), "Le champ dans le tableau doit être visible pour verifier la creation de l affaires.");
 */
        // Retry mechanism parameters
        int maxRetries = 3;
        int retryCount = 0;
        boolean isElementDisplayed = false;

        // Retry loop
        while (retryCount < maxRetries && !isElementDisplayed) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Wait for the presence of the table cell element, ignoring StaleElementReferenceException
            WebElement tdElement = wait.ignoring(StaleElementReferenceException.class)
                                       .until(driver -> {
                                           WebElement element = driver.findElement(By.xpath("//table[@id='11368620578813503']//td[contains(text(), '" + randomVerification + "')]"));
                                           if (element.isDisplayed()) {
                                               return element;
                                           } else {
                                               return null; // Return null if element is not displayed
                                           }
                                       });

            // Check if the element is displayed
            if (tdElement != null) {
                isElementDisplayed = true;
                System.out.println("Element is displayed.");
            } else {
                System.out.println("StaleElementReferenceException occurred. Retrying...");
                retryCount++;
            }
        }

        // Assert if the element is displayed after retrying
        Assert.assertTrue(isElementDisplayed, "Element should be displayed after multiple retries.");

        System.out.println("searchAffaire ended");
    }

    @AfterTest
    public void tearDown() {
        System.out.println("Test ended  !!!");
        // driver.quit();
    }
}
