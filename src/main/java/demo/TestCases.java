package demo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;
    WebDriverWait wait;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.INFO);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Connect to the chrome-window running on debugging port
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void testCase01() throws InterruptedException {
        
        System.out.println("Start Test case: testCase01");
        driver.get("https://calendar.google.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Thread.sleep(15000);
        if (wait.until(ExpectedConditions.urlContains("calendar")))
            System.out.println("Successfully navigated to Google Calendar");
        else
            System.out.println("Incorrect URL- Navigation failed");
        System.out.println("end Test case: testCase01");
    }

    public void testCase02() throws InterruptedException {
        System.out.println("Start Test case: testCase02");
         Thread.sleep(3000);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        if (!driver.findElement(By.xpath("//*[@id='gb']//span[text()='Month']")).getText().equals("Month")) {
            driver.findElement(By.xpath("//*[text()='Day']/parent::button")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//ul[contains(@class,'VfPpkd-StrnGf-rymPhb')]//span[text()='Month']"))).click();
        }

        Thread.sleep(3000);
        WebElement dateElement = driver.findElement(By.xpath("//h2[contains(text(),'January 16')]/parent::div"));
        dateElement.click();

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[contains(@class,'kkUTBb') and text()='Task']")).click();

        String taskTitle = "Crio INTV Task Automation";
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[aria-label='Add title and time']")))
                .sendKeys(taskTitle);
        driver.findElement(By.tagName("textarea")).sendKeys("Crio INTV Calendar Task Automation");

        driver.findElement(By.xpath("//span[text()='Save']/parent::button")).click();

        Thread.sleep(3000);
        String actualTaskTitle = driver
                .findElement(By.xpath("//h2[contains(text(),'January 16')]/parent::div//span[@class='yzifAd']"))
                .getText();

        if (taskTitle.equals(actualTaskTitle))
            System.out.println("Task was created successfully");
        else
            System.out.println("Task creation failed.");
        System.out.println("End TestCase02");
    }

    /*
     * Select an existing task, update its description, and verify the successful
     * update.
     */
    public void testCase03() throws InterruptedException {
        System.out.println("Start TestCase 03");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//h2[contains(text(),'January 16')]/parent::div//*[@role='button']")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("[aria-label='Edit task']")).click();
        driver.findElement(By.tagName("textarea")).clear();
        String description = "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application";
        driver.findElement(By.tagName("textarea")).sendKeys(description);
        driver.findElement(By.xpath("//span[text()='Save']/parent::button")).click();

        // Verify
        Thread.sleep(3000);
        driver.findElement(By.xpath("//h2[contains(text(),'January 16')]/parent::div//*[@role='button']")).click();
        Thread.sleep(3000);
        String descriptionActual = driver.findElement(By.xpath("//span[text()='Description:']/parent::div")).getText();

        //System.out.println(descriptionActual.split(":")[1]);
        if (descriptionActual.split(":")[1].equals("\n"+description))
            System.out.println("Task description updated successfully");
        else
            System.out.println("Task description update failed.");
            System.out.println("End TestCase 03");
    }

    public void testCase04(){
        System.out.println("Start TestCase 04");
        String taskTitle = "Crio INTV Task Automation";
        if(driver.findElement(By.id("rAECCd")).getText().equals(taskTitle)){
            driver.findElement(By.cssSelector("[aria-label='Delete task']")).click();
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            if(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".VYTiVb"))).getText().equals("Task deleted")) System.out.println("Task deleted successfully");
            else System.out.println("Task deletion failed");
        }

        else System.out.println("Task does not exists");
        System.out.println("End TestCase 04");
    }

}
