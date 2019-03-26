package com.excilys.computer_database.servletTest;

import static org.junit.Assert.assertEquals;
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

	
	private static String TAB_COMPUTER_ID = "results";
	private static final String NEXT_BUTTON = "next";
	private static final String PREVIOUS_BUTTON = "previous";
	
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
		WebElement results = webDriver.findElement(By.id(TAB_COMPUTER_ID));
		List<WebElement> list = results.findElements(By.tagName("tr"));
		assertNotEquals(0,list.size());
		assertEquals(webDriver.findElement(By.id("currentSize")).getText(),Integer.toString(list.size()));
	}
	
	@Test
	public void nextTest() {
		webDriver.get(DASHBOARD);
		WebElement nextButton = webDriver.findElement(By.id(NEXT_BUTTON));
		WebElement previousButton = webDriver.findElement(By.id(PREVIOUS_BUTTON));
		WebElement results = webDriver.findElement(By.id(TAB_COMPUTER_ID));
		List<WebElement> list = results.findElements(By.tagName("tr"));
		nextButton.click();
		results = webDriver.findElement(By.id(TAB_COMPUTER_ID));
		List<WebElement> list2 = results.findElements(By.tagName("tr"));
		assertNotEquals(list,list2);
		previousButton.click();
	}
	
	@Test
	public void previousTest() {
		webDriver.get(DASHBOARD);
		WebElement nextButton = webDriver.findElement(By.id(NEXT_BUTTON));
		WebElement previousButton = webDriver.findElement(By.id(PREVIOUS_BUTTON));
		
		//Previous when first page
		WebElement results = webDriver.findElement(By.id(TAB_COMPUTER_ID));
		List<WebElement> list = results.findElements(By.tagName("tr"));
		previousButton.click();
		results = webDriver.findElement(By.id(TAB_COMPUTER_ID));
		List<WebElement> list2 = results.findElements(By.tagName("tr"));
		assertEquals(list,list2);
		
		//Previous when not first page
		nextButton.click();
		results = webDriver.findElement(By.id(TAB_COMPUTER_ID));
		list = results.findElements(By.tagName("tr"));
		previousButton.click();
		results = webDriver.findElement(By.id(TAB_COMPUTER_ID));
		list2 = results.findElements(By.tagName("tr"));
		assertNotEquals(list,list2);
	}
	
}