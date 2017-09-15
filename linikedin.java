package com.myProject;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.io.File;
import java.util.List;

public class MyProgram {
    static WebDriver driver;

    public static void sendMessage() throws InterruptedException {
        String companyName;
        String profileName = driver.findElement(By.xpath("//h1[contains(@class, 'pv-top-card-section__name')]")).getText();

        try {
            companyName = driver.findElement(By.xpath("//h3[contains(@class, 'pv-top-card-section__company')]")).getText();
        }catch(Exception e){
            companyName = "your organization";
        }

        if(companyName.matches(".*\\bfreelance\\b.*") || companyName.matches(".*\\bFreelance\\b.*") || companyName.matches(".*\\bFreelancer\\b.*") || companyName.matches(".*\\bfreelancer\\b.*") || companyName.matches(".*\\bself\\b.*")){
            companyName = "job";
        }

        String[] splitedName = profileName.split("\\s+");
        String firstName = splitedName[0];

        String capPersonName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);

        driver.findElement(By.xpath("//button[contains(@class, 'message-anywhere-button')]")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//textarea[contains(@name, 'message')]")).sendKeys(
                "Hi "+capPersonName+","
                        + "\n\nThank you for accepting my request. :)"
                        + "\n\nSincerely,\nRajendra Arora");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//button[contains(@class, 'msg-messaging-form__send-button')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[contains(@data-control-name, 'overlay.close_conversation_window')]")).click();
    }

    public static void main(String[] args) throws InterruptedException, AWTException {

        ChromeOptions chromeOptions = new ChromeOptions();
        File chromeDriver = new File("C://chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
//		chromeOptions.addArguments("--headless");
//		chromeOptions.addArguments("--disable-gpu");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.linkedin.com/");
        driver.findElement(By.xpath("//input[contains(@class, 'login-email')]")).sendKeys("your linkedin email");
        driver.findElement(By.xpath("//input[contains(@class, 'login-password')]")).sendKeys("your linkedin password");
        driver.findElement(By.xpath("//input[contains(@id, 'login-submit')]")).click();
        Thread.sleep(5000);

        driver.get("https://www.linkedin.com/mynetwork/invite-connect/connections/");

        Thread.sleep(8000);

        List<WebElement> allCards = driver.findElements(By.xpath("//li[contains(@class, 'connection-card')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        int counter = 57;


        for (WebElement allChilds : allCards){
            counter++;

            try{
                /*Scroll to view element*/
                WebElement element = driver.findElement(By.xpath("//li[contains(@class, 'connection-card')]["+(counter-1)+"]"));
                js.executeScript("arguments[0].scrollIntoView()", element);
                Thread.sleep(2000);
            }catch(Exception e){
                System.out.println("No need to scroll.");
            }

            driver.findElement(By.xpath("//li[contains(@class, 'connection-card')]["+counter+"]")).click();

            Thread.sleep(3000);

            /*Sending message.*/
            sendMessage();

            Thread.sleep(5000);
            System.out.println("Counter: "+counter);

            /*Go back to previous connection page.*/
            js.executeScript("window.history.go(-1)");
            Thread.sleep(5000);

            System.out.println("done");
        }

        Thread.sleep(10000);
        driver.quit();
    }
}

