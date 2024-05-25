package helpers;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Properties {

    public static TestsProperties testProperties = ConfigFactory.create(TestsProperties.class);


}