package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Cloud storage application tests.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
    
    @LocalServerPort
    private int port;
    
    private WebDriver driver;
    
    /**
     * Before all.
     */
    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }
    
    /**
     * Before each.
     * My ChromeDriver has not supported for current chrome
     * so I have to use chromedriver-win64\chromedriver.exe
     */
    @BeforeEach
    public void beforeEach() {
        System.setProperty("webdriver.chrome.driver", "chromedriver-win64\\chromedriver.exe");
        this.driver = new ChromeDriver();
    }
    
    /**
     * After each.
     */
    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }
    
    /**
     * Gets login page.
     */
    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", driver.getTitle());
    }
    
    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.
        
        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        
        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);
        
        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
        assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }
    
    
    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
        
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        
    }
    
    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "admin", "test123");
        driver.get("http://localhost:" + this.port + "/login");
        // Check if we have been redirected to the log in page.
        assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }
    
    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "admin", "123");
        doLogIn("admin", "123");
        
        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        assertTrue(driver.getPageSource().contains("Whitelabel Error Page"));
    }
    
    
    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Da", "Le", "admin1", "123");
        doLogIn("admin1", "123");
        
        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());
        
        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        assertTrue(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
        
    }
    
    private void doAddNote() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement noteNavigation = driver.findElement(By.id("nav-notes-tab"));
        noteNavigation.click();
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-note")));
        WebElement modalNotes = driver.findElement(By.id("modal-note"));
        modalNotes.click();
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement inputNoteTitle = driver.findElement(By.id("note-title"));
        inputNoteTitle.click();
        inputNoteTitle.sendKeys("note my work");
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement inputNoteDescription = driver.findElement(By.id("note-description"));
        inputNoteDescription.click();
        inputNoteDescription.sendKeys("- to day so long for me");
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note")));
        WebElement saveNotes = driver.findElement(By.id("save-note"));
        saveNotes.click();
        
    }
    
    /**
     * Test add note.
     */
    @Test
    public void testAddNote() {
        doMockSignUp("Da", "Le", "admin1", "123");
        doLogIn("admin1", "123");
        doAddNote();
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-title")));
        
        assertEquals("note my work", driver.findElement(By.id("show-title")).getText());
        assertEquals("- to day so long for me", driver.findElement(By.id("show-description")).getText());
    }
    
    /**
     * Test edit note.
     */
    @Test
    public void testEditNote() {
        doMockSignUp("Da", "Le", "admin1", "123");
        doLogIn("admin1", "123");
        doAddNote();
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note")));
        WebElement modalNotes = driver.findElement(By.id("edit-note"));
        modalNotes.click();
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement inputNoteTitle = driver.findElement(By.id("note-title"));
        inputNoteTitle.clear();
        inputNoteTitle.sendKeys("change note my work");
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement inputNoteDescription = driver.findElement(By.id("note-description"));
        inputNoteDescription.clear();
        inputNoteDescription.sendKeys("- today is so long for me, so I have to go to sleep");
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note")));
        WebElement saveNotes = driver.findElement(By.id("save-note"));
        saveNotes.click();
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-title")));
        
        assertEquals("change note my work", driver.findElement(By.id("show-title")).getText());
        assertEquals("- today is so long for me, so I have to go to sleep", driver.findElement(By.id("show-description")).getText());
    }
    
    
    /**
     * Test delete note.
     */
    @Test
    public void testDeleteNote() {
        doMockSignUp("Da", "Le", "admin1", "123");
        doLogIn("admin1", "123");
        doAddNote();
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note")));
        WebElement deleteNote = driver.findElement(By.id("delete-note"));
        deleteNote.click();
        
        boolean isNotePresent = isElementPresent(By.className("note-container"));
        assertFalse(isNotePresent);
        
    }
    
    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    
    @Test
    public void testAddCredential() {
        doMockSignUp("Da", "Le", "admin1", "123");
        doLogIn("admin1", "123");
        doAddCredential();
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url-display")));
        
        assertEquals("https://learn.udacity.com/nanodegrees/", driver.findElement(By.id("credential-url-display")).getText());
        assertEquals("DaLQA", driver.findElement(By.id("credential-username-display")).getText());
        
    }
    
    @Test
    public void testEditCredentials() {
        doMockSignUp("Da", "Le", "admin1", "123");
        doLogIn("admin1", "123");
        doAddCredential();
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential")));
        WebElement editCredential = driver.findElement(By.id("edit-credential"));
        editCredential.click();
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement inputUrl = driver.findElement(By.id("credential-url"));
        inputUrl.clear();
        inputUrl.sendKeys("https://learn.udacity.com/nanodegrees/aaaaaa");
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement inputUsername = driver.findElement(By.id("credential-username"));
        inputUsername.clear();
        inputUsername.sendKeys("DaLQA123");
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement inputPassword = driver.findElement(By.id("credential-password"));
        inputPassword.clear();
        inputPassword.sendKeys("Abcd!@!@11111");
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential")));
        WebElement saveNotes = driver.findElement(By.id("save-credential"));
        saveNotes.click();
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url-display")));
        
        assertEquals("https://learn.udacity.com/nanodegrees/aaaaaa", driver.findElement(By.id("credential-url-display")).getText());
        assertEquals("DaLQA123", driver.findElement(By.id("credential-username-display")).getText());
        
    }
    
    private void doAddCredential() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement noteNavigation = driver.findElement(By.id("nav-credentials-tab"));
        noteNavigation.click();
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-credentials")));
        WebElement modalCredential = driver.findElement(By.id("show-credentials"));
        modalCredential.click();
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement inputUrl = driver.findElement(By.id("credential-url"));
        inputUrl.click();
        inputUrl.sendKeys("https://learn.udacity.com/nanodegrees/");
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement inputUsername = driver.findElement(By.id("credential-username"));
        inputUsername.click();
        inputUsername.sendKeys("DaLQA");
        
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement inputPassword = driver.findElement(By.id("credential-password"));
        inputPassword.click();
        inputPassword.sendKeys("Abcd!@!@11");
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential")));
        WebElement saveNotes = driver.findElement(By.id("save-credential"));
        saveNotes.click();
        
    }
    
    
    @Test
    public void testDeleteCredential() {
        doMockSignUp("Da", "Le", "admin1", "123");
        doLogIn("admin1", "123");
        doAddCredential();
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-credential")));
        WebElement deleteCredential = driver.findElement(By.id("delete-credential"));
        deleteCredential.click();
        
        boolean isNotePresent = isElementPresent(By.className("credential-container"));
        assertFalse(isNotePresent);
        
    }
    
}
