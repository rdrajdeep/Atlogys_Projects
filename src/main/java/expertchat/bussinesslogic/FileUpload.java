package expertchat.bussinesslogic;

// Created by Kishor on 3/1/2017.

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FileUpload {

    private static String json;

    public  void uploadMedia(String mediaPath, String ExpertEmail, String password) {

        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://api.qa.experchat.com/api-auth/login/?next=/v1/expert/usermedia/");
        driver.findElement(By.name("username")).sendKeys("expert_"+ExpertEmail);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("submit-id-submit")).click();
        wait(5000);
        driver.findElement(By.name("media")).sendKeys(mediaPath);
        driver.findElement(By.xpath("//*[@id=\"post-object-form\"]/form/fieldset/div[2]/button")).click();
        String response = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[4]/pre")).getText();
        String jsonData = response.substring(response.indexOf("{"));
        System.out.println(jsonData);
        driver.quit();
        json=new Gson().toJson(jsonData);
    }

    public static String getJson(){
        return json;
    }

    private static void wait(int time ){
        try{
            Thread.sleep(time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
