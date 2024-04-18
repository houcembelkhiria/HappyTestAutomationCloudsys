package cloudsyCreatesAffaire;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testNG {
    ChromeDriver driver = null;

    @BeforeTest
    public void preConfigure() {
        System.out.println("Test started !!!");
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
        driver.findElement(By.id("P10001_USERNAME")).sendKeys("ERP");
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
        WebElement createBtn = driver.findElement(By.id("B11386816663854057"));
        WebDriverWait webdwaitCreateBtn = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement clickableElement1 = webdwaitCreateBtn.until(ExpectedConditions.elementToBeClickable(createBtn));
        clickableElement1.click();
        
        //driver.findElement(By.id("B11386816663854057")).click();
        
        WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'a-Button--popupLOV')]"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
        WebDriverWait webdwait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement clickableElement = webdwait2.until(ExpectedConditions.elementToBeClickable(element));
        clickableElement.click();
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();
        while(iterator.hasNext()) {
            String childWindow = iterator.next();
            driver.switchTo().window(childWindow);
            System.out.println(driver.getTitle()+" opened");
            if (driver.getTitle().equals("Search Dialog")) {            	
            	driver.findElement(By.id("SEARCH")).sendKeys("stn");
            	WebElement searchButton = driver.findElement(By.xpath("//input[@type='button' and @value='Search']"));
            	searchButton.click();
                WebElement linkElement = driver.findElement(By.xpath("//a[contains(text(), 'Les Travaux du Nord (STN)')]"));
            	linkElement.click();          	
                break;
            }
        }           
        Set<String> windowHandles2 = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (driver.switchTo().window(windowHandle).getTitle().equals("Mise à jour information affaires")) {
                break;
            }
        }
        driver.findElement(By.id("P117_DESCRIPTION_BESOIN")).sendKeys("test automation");
        driver.findElement(By.id("B151988927049331746")).click();
        driver.findElement(By.id("B153509924327533811")).click();
        System.out.println("createAffaire ended !!!");
    	
    }
    
    @Test
    public void testSearchAffaire() {
        System.out.println("searchAffaire started ");
        driver.findElement(By.id("B151989358334331746")).click();
        driver.findElement(By.id("R11368507481813501_search_field")).sendKeys("Les Travaux du Nord (STN)");
        driver.findElement(By.id("R11368507481813501_search_button")).click();
        System.out.println("searchAffaire ended");
    }
    
    
    
    @AfterTest
    public void tearDown() {
        System.out.println("Test ended  !!!");
        //driver.quit();
    }
}
