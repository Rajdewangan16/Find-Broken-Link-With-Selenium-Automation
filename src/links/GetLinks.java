package links;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetLinks {

	private WebDriver driver = new ChromeDriver();
	SoftAssert soft = new SoftAssert();
	
	@BeforeTest
	public void initializeDriver() {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://phptravels.com/demo/");
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void findBrokenLinks() {
		
		List<WebElement> links = driver.findElements(By.tagName("a"));
		
		links.forEach(s -> { String url = s.getAttribute("href");
		
			try {
				HttpURLConnection con = (HttpURLConnection) new URI(url).toURL().openConnection();
				con.setRequestMethod("HEAD");
				con.connect();
				
				int responseCode = con.getResponseCode();
				System.out.println(responseCode);
				soft.assertTrue(responseCode < 400, "The Link is Broken " + url + " with status code "
						 + responseCode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		});
		
		soft.assertAll();
	}
	
}
