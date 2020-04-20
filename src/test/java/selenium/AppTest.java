package selenium;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppTest {
    WebDriver driver;

    @Before
    public void BeforeTest() throws InterruptedException {
        String path = "C:/Users/noorb/OneDrive/Desktop/chromedriver_win32/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        driver = new ChromeDriver();
        // Thread.sleep(3000);
    }

    @After
    public void AfterTest() {
        System.out.println("Closing ChromeDiver...");
        driver.close();
    }

    // click all buttons
    @Test
    public void testClickButtons() throws InterruptedException {
        driver.get("http://localhost:8080/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // driver.findElement(By.id("button")).click();
        // get all the buttons
        java.util.List<WebElement> buttons = driver.findElements(By.tagName("a"));

        // click all buttons in a web page
        for (int i = 0; i < buttons.size(); i++) {
            System.out.println("*** Navigating to" + " " + buttons.get(i).getAttribute("href"));
            if (buttons.get(i).getAttribute("href") == null)
                continue;
            boolean staleElementLoaded = true;
            // the loop checks whether the elements is properly loaded
            while (staleElementLoaded) {
                try {
                    // navigate to the link
                    buttons.get(i).click();
                    Thread.sleep(3000);
                    // click the back button in browser
                    driver.navigate().back();
                    buttons = driver.findElements(By.tagName("a"));
                    staleElementLoaded = false;
                } catch (StaleElementReferenceException e) {
                    staleElementLoaded = true;
                }
            }
        }
    };

    // Sucessful log in to Rainbow using guest account, send message through chatbox
    // and successful log
    // out
    @Test
    public void testLoginSuccess() throws InterruptedException {
        driver.get("http://localhost:8080/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.id("button")).click();

        // wait for page to load
        Thread.sleep(3000);

        // //get username field
        WebElement username = driver.findElement(By.name("firstname"));
        System.out.println(username);
        // send the username into the field
        username.sendKeys("FirstName");

        // get lastname field
        WebElement lastName = driver.findElement(By.name("lastname"));

        // send password into the field
        lastName.sendKeys("lastName");

        // wait for selenium to key in everything
        Thread.sleep(3000);
        // login
        WebElement LoginButton = driver.findElement(By.name("tochatbotbtn"));
        LoginButton.click();

        // wait for new page to load
        Thread.sleep(30000);
        // check the new page
        String url = driver.getCurrentUrl();
        assertEquals("http://localhost:8082/chatbot", url);

        // click chatbox
        WebElement ChatBox = driver.findElement(By.name("msgbox"));
        ChatBox.sendKeys("hello");
        Thread.sleep(300);

        // click send button
        WebElement EnterButton = driver.findElement(By.name("msgbutton"));
        EnterButton.click();
        Thread.sleep(3000);

        Thread.sleep(3000);
        // click logout button
        WebElement LogoutButton = driver.findElement(By.name("toHomebutton"));
        LogoutButton.click();
        System.out.println("Clicked logout button");
        Thread.sleep(3000);
        url = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/feedback", url);
    }

    // Failed login to Rainbow
    @Test
    public void testLoginFail() throws InterruptedException {
        driver.get("http://localhost:8080/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.id("button")).click();

        // wait for page to load
        Thread.sleep(3000);

        // //get username field
        WebElement username = driver.findElement(By.name("firstname"));
        System.out.println(username);

        // send the username into the field
        username.sendKeys("firstName");

        // wait for selenium to key in everything
        Thread.sleep(3000);
        // login
        WebElement LoginButton = driver.findElement(By.name("tochatbotbtn"));
        LoginButton.click();

        // wait for new page to load
        Thread.sleep(30000);

        // check that the page is still the current page
        String url = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/guest", url);
    }

    // test Audio call works
    @Test
    public void testAudioCall() throws InterruptedException {
        driver.get("http://localhost:8080/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.id("button")).click();

        // wait for page to load
        Thread.sleep(10000);

        // //get username field
        WebElement username = driver.findElement(By.name("firstname"));
        System.out.println(username);
        // send the username into the field
        username.sendKeys("FirstName");

        // get lastname field
        WebElement lastName = driver.findElement(By.name("lastname"));

        // send password into the field
        lastName.sendKeys("lastName");

        // wait for selenium to key in everything
        Thread.sleep(3000);
        // login
        WebElement LoginButton = driver.findElement(By.name("tochatbotbtn"));
        LoginButton.click();

        // wait for new page to load
        Thread.sleep(30000);
        // check the new page
        String url = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/chatbot", url);

        // click chatbox and type #support
        WebElement ChatBox = driver.findElement(By.name("msgbox"));
        ChatBox.sendKeys("#support");
        Thread.sleep(300);

        // click send button
        WebElement EnterButton = driver.findElement(By.name("msgbutton"));
        EnterButton.click();
        Thread.sleep(8000);

        // type Investment
        ChatBox.sendKeys("Investment");
        Thread.sleep(800);

        // click send button
        EnterButton.click();
        Thread.sleep(4000);

        // type yes
        ChatBox.sendKeys("Yes");
        Thread.sleep(300);

        // click send button
        EnterButton.click();
        Thread.sleep(800);

        // type hello
        ChatBox.sendKeys("hello");
        Thread.sleep(300);

        // click send button
        EnterButton.click();
        Thread.sleep(1000);

        // click call button
        WebElement call = driver.findElement(By.name("toCall"));
        call.click();
        Thread.sleep(5000);

        // click audio call
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String clickAudio = "document.querySelector(\"[class='swal-button swal-button--audio']\")" + ".click()";
        js.executeScript(clickAudio);
        Thread.sleep(10000);

        // click exit button
        WebElement exitButton = driver.findElement(By.name("endCall"));
        exitButton.click();

        System.out.println("Clicked exit button");
        Thread.sleep(3000);

        url = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/feedback", url);
    }

    // test Video call works
    @Test
    public void testVideoCall() throws InterruptedException {
        driver.get("http://localhost:8080/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.id("button")).click();

        // wait for page to load
        Thread.sleep(10000);

        // //get username field
        WebElement username = driver.findElement(By.name("firstname"));
        System.out.println(username);
        // send first name into the field
        username.sendKeys("Noor");

        // get lastname field
        WebElement lastName = driver.findElement(By.name("lastname"));

        // send last name into the field
        lastName.sendKeys("Khan");

        // wait for selenium to key in everything
        Thread.sleep(3000);
        // login
        WebElement LoginButton = driver.findElement(By.name("tochatbotbtn"));
        LoginButton.click();

        // wait for new page to load
        Thread.sleep(30000);
        // check the new page
        String url = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/chatbot", url);

        // click chatbox and type #support
        WebElement ChatBox = driver.findElement(By.name("msgbox"));
        ChatBox.sendKeys("#support");
        Thread.sleep(300);

        // click send button
        WebElement EnterButton = driver.findElement(By.name("msgbutton"));
        EnterButton.click();
        Thread.sleep(8000);

        // type Investment
        ChatBox.sendKeys("Banking");
        Thread.sleep(800);

        // click send button
        EnterButton.click();
        Thread.sleep(4000);

        // type yes
        ChatBox.sendKeys("Yes");
        Thread.sleep(300);

        // click send button
        EnterButton.click();
        Thread.sleep(800);

        // type hello
        ChatBox.sendKeys("hello");
        Thread.sleep(300);

        // click send button
        EnterButton.click();
        Thread.sleep(1000);

        // click call button
        WebElement call = driver.findElement(By.name("toCall"));
        call.click();
        Thread.sleep(5000);

        // click audio call
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String clickVideo = "document.querySelector(\"[class='swal-button swal-button--video']\")" + ".click()";
        js.executeScript(clickVideo);
        Thread.sleep(10000);

        // click logout button
        WebElement exitButton = driver.findElement(By.name("endCall"));
        exitButton.click();

        System.out.println("Clicked exit button");
        Thread.sleep(3000);

        url = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/feedback", url);
    }

    // test router links
    @Test
    public void testRouterLinks() throws InterruptedException {
        driver.get("http://localhost:8080/feedback");
        // maximize the browser window
        driver.manage().window().maximize();
        // check the new page
        String url = driver.getCurrentUrl();
        // check that it goes to home page instead
        assertEquals("http://localhost:8080/", url);
        Thread.sleep(3000);

        driver.get("http://localhost:8080/guest");
        // maximize the browser window
        driver.manage().window().maximize();
        // check the new page
        String url1 = driver.getCurrentUrl();
        // check that it goes to home page instead
        assertEquals("http://localhost:8080/", url1);
        Thread.sleep(3000);

        driver.get("http://localhost:8080/chatbot");
        // maximize the browser window
        driver.manage().window().maximize();
        // check the new page
        String url2 = driver.getCurrentUrl();
        // check that it goes to home page instead
        assertEquals("http://localhost:8080/", url2);
        Thread.sleep(3000);

        driver.get("http://localhost:8080/call");
        // maximize the browser window
        driver.manage().window().maximize();
        // check the new page
        String url3 = driver.getCurrentUrl();
        // check that it goes to home page instead
        assertEquals("http://localhost:8080/", url3);
        Thread.sleep(3000);

        driver.get("http://localhost:8080/callAudio");
        // maximize the browser window
        driver.manage().window().maximize();
        // check the new page
        String url4 = driver.getCurrentUrl();
        // check that it goes to home page instead
        assertEquals("http://localhost:8080/", url4);
        Thread.sleep(3000);
    }

    // Test for spamming message box
    @Test
    public void SpamMessage() throws InterruptedException {
        driver.get("http://localhost:8080/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.id("button")).click();

        // wait for page to load
        Thread.sleep(8000);

        // //get username field
        WebElement username = driver.findElement(By.name("firstname"));
        System.out.println(username);
        // send first name into the field
        username.sendKeys("Noor");

        // get lastname field
        WebElement lastName = driver.findElement(By.name("lastname"));

        // send last name into the field
        lastName.sendKeys("Khan");

        // wait for selenium to key in everything
        Thread.sleep(3000);
        // login
        WebElement LoginButton = driver.findElement(By.name("tochatbotbtn"));
        LoginButton.click();

        // wait for new page to load
        Thread.sleep(30000);

        WebElement ChatBox = driver.findElement(By.name("msgbox"));
        WebElement EnterButton = driver.findElement(By.name("msgbutton"));
        // type message
        for (int i = 0; i < 42; i++) {
            ChatBox.sendKeys("hello");
            Thread.sleep(300);
            // click send button
            EnterButton.click();
        }

        Thread.sleep(3000);

        // check the new page (that user is logged out by forced)
        String url = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/", url);
    }
};
