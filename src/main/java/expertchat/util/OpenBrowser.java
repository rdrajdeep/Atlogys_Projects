package expertchat.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpenBrowser {

    private WebDriver webPage;

    private void setupChrome(){

        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
        webPage =new ChromeDriver();
        webPage.manage().window().maximize();
    }

    private void expertChatLogin(String url){

        webPage.get(url);
        webPage.findElement(By.name("email")).sendKeys("kishor+expert31@atlogys.com");
        webPage.findElement(By.name("password")).sendKeys("jyoti1032");
        webPage.findElement(By.cssSelector(".md-raised.md-primary.btn-large")).click();
        WebDriverWait wait=new WebDriverWait(webPage, 10);
        By locatorBackButton=By.cssSelector(".md-primary.md-hue-1.backButton.md-icon-button");
        wait.until(ExpectedConditions.elementToBeClickable(locatorBackButton));

    }

    private void sleep(){

        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){

        }
    }

    public String fbLogin(String url){

        webPage.findElement(By.name("email")).sendKeys("ram@atlogys.com");
        webPage.findElement(By.id("pass")).sendKeys("atlogys@123");
        webPage.findElement(By.id("loginbutton")).click();
        String cUrl=webPage.getCurrentUrl();
        webPage.quit();
        return cUrl;
    }

     public String instaLogin(WebDriver webPage, String url)  {

         webPage.navigate().to(url);
         webPage.findElement(By.id("id_username")).sendKeys("ram@atlogys.com");
         webPage.findElement(By.id("id_password")).sendKeys("atlogys@123");
         webPage.findElement(By.cssSelector("input.button-green")).click();
         return webPage.getCurrentUrl();
     }

     public static void main(String [] args)  {

         OpenBrowser ob=new OpenBrowser();
         ob.setupChrome();
         ob. expertChatLogin("http://web.qa.experchat.com/#/login");

    }
}
