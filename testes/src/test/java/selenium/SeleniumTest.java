package selenium;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumTest {

  RemoteWebDriver driver;

  @BeforeClass
  public static void configureDriver() {
    String rootPath = System.getProperty("user.dir");
    System.setProperty(
        "webdriver.gecko.driver", rootPath + "\\src\\test\\resources\\geckodriver.exe");
  }

  @Before
  public void createDriver() {
    driver = new FirefoxDriver();
    driver.get("https://bank-simulator.herokuapp.com/");
  }

  @Test
  public void testConta_quandoClica_entaoNomeConta() {
    // SETUP : LOGIN
    driver.findElement(By.id("cpflogin")).sendKeys("  1  2  3  4  5  6  7  8  9  0  0");
    // as vezes ele manda os caracteres na ordem errada, entao adicionei espacos pra corrigir
    driver.findElement(By.id("senhalogin")).sendKeys("  1  2  3");
    driver.findElement(By.xpath("/html/body/div/div[1]/div/form/button")).click();

    // ACT : CONTA
    driver.findElement(By.xpath("/html/body/div/div[1]/nav/ul/li[2]/a[1]/img")).click();
    String nomeConta =
        driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr/td[1]/a")).getText();

    // CLOSE + ASSERTS
    driver.close();
    assertEquals("conta teste", nomeConta);
  }
}
