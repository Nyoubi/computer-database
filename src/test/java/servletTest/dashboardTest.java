package servletTest;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class dashboardTest {
	private static ChromeDriverService ChromeDriverService;
	private static WebDriver webDriver;

	private static String DASHBOARD = "http://localhost:8080/computer-database/dashboard";
	private static final String ADD_COMPUTER = "http://localhost:8080/computer-database/addComputer";

	
	private static String tabComputerId = "results";
	@BeforeAll
	public static void createDriver() {
		ChromeDriverService = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("/usr/lib/chromium-browser/chromedriver"))
				.usingAnyFreePort()
				.build();
		try {
			ChromeDriverService.start();
		} catch (IOException e) {
			e.printStackTrace();
			fail("service couldn't start");
		}
		webDriver = new RemoteWebDriver(ChromeDriverService.getUrl(),
		DesiredCapabilities.chrome());
	}

	@AfterAll
	public static void quitDriver() {
		ChromeDriverService.stop();
		webDriver.quit();
	}
	
	@Test
	public void testListComputer() {
		webDriver.get(DASHBOARD);
		WebElement results = webDriver.findElement(By.id(tabComputerId));
		List<WebElement> list = results.findElements(By.tagName("tr"));
		assertNotEquals(0,list.size());
		assertEquals();
	}
	
	
}
