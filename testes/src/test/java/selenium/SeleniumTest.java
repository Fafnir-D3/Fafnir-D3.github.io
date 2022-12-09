package selenium;

import static org.junit.Assert.assertEquals;

import java.io.File;
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
        "webdriver.gecko.driver",
        rootPath
            + File.separator
            + "src"
            + File.separator
            + "test"
            + File.separator
            + "resources"
            + File.separator
            + "geckodriver.exe");
  }

  @Before
  public void createDriver() {
    driver = new FirefoxDriver();
    driver.get("https://bank-simulator.herokuapp.com/");
  }

  /**
   * RF1 O administrador acessa a area privada da aplicacao por meio de login
   */
  @Test
  public void testLogin_quandoAdministradorValido_entaoEntra() {
    // SETUP : LOGIN
    driver.findElement(By.id("cpflogin")).sendKeys("  2  4  9  2  5  2  8  1  0  3  8");
    // as vezes ele manda os caracteres na ordem errada, entao adicionei espacos pra corrigir
    driver.findElement(By.id("senhalogin")).sendKeys("111");
    driver.findElement(By.xpath("/html/body/div/div[1]/div/form/button")).click();

    String usuarios =
            driver.findElement(By.xpath("/html/body/div/div[2]/h1")).getText();
    // CLOSE + ASSERTS
    driver.close();
    assertEquals("Usuários", usuarios);
  }

  /**
   * RF2 O administrador cadastra os administradores no sistema
   */
  @Test
  public void testCadastraAdministrador_quandoAdministrador_entaoNovoAdministrador() {
    // SETUP : LOGIN
    driver.findElement(By.id("cpflogin")).sendKeys("  2  4  9  2  5  2  8  1  0  3  8");
    // as vezes ele manda os caracteres na ordem errada, entao adicionei espacos pra corrigir
    driver.findElement(By.id("senhalogin")).sendKeys("111");
    driver.findElement(By.xpath("/html/body/div/div[1]/div/form/button")).click();

    // ACT : Cria Administrador
    driver.findElement(By.xpath("/html/body/div/div[1]/nav/ul/li[1]/a[1]/img")).click();
    driver.findElement(By.id("formButton")).click();
    driver.findElement(By.id("nomeregistro")).sendKeys("Administrador Teste");
    driver.findElement(By.id("cpfregistro")).sendKeys("00000000000");
    driver.findElement(By.id("senharegistro")).sendKeys("000");
    driver.findElement(By.xpath("/html/body/div/div[3]/form/button")).click();
    // Checa se foi criado
    // A proxima linha pode quebrar caso a posicao do usuario mude
    String cpf = driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr[2]/td[2]")).getText();
    // Exclui o usuario criado
    driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr[2]/td[3]/a")).click();
    // CLOSE + ASSERTS
    driver.close();
    assertEquals("000.000.000-00", cpf);
  }

  /**
   * RF6 O usuario acessa a area privada da aplicacao por meio de login
   */
  @Test
  public void testLogin_quandoUsuarioValido_entaoEntra() {
    // SETUP : LOGIN
    driver.findElement(By.id("cpflogin")).sendKeys("  1  2  3  4  5  6  7  8  9  0  0");
    // as vezes ele manda os caracteres na ordem errada, entao adicionei espacos pra corrigir
    driver.findElement(By.id("senhalogin")).sendKeys("  1  2  3");
    driver.findElement(By.xpath("/html/body/div/div[1]/div/form/button")).click();

    String meusDados =
        driver.findElement(By.xpath("/html/body/div/div[2]/h1")).getText();
    // CLOSE + ASSERTS
    driver.close();
    assertEquals("Meus Dados", meusDados);
  }

  /**
   * RF2 O administrador cadastra os usuarios no sistema
   */
  @Test
  public void testCadastraUsuario_quandoAdministrador_entaoNovoUsuario() {
    // SETUP : LOGIN
    driver.findElement(By.id("cpflogin")).sendKeys("  2  4  9  2  5  2  8  1  0  3  8");
    // as vezes ele manda os caracteres na ordem errada, entao adicionei espacos pra corrigir
    driver.findElement(By.id("senhalogin")).sendKeys("111");
    driver.findElement(By.xpath("/html/body/div/div[1]/div/form/button")).click();

    // ACT : Cria Usuario
    driver.findElement(By.id("formButton")).click();
    driver.findElement(By.id("nomeregistro")).sendKeys("Usuario Teste");
    driver.findElement(By.id("cpfregistro")).sendKeys("00000000000");
    driver.findElement(By.id("senharegistro")).sendKeys("000");
    driver.findElement(By.id("suspensoregistro")).sendKeys("N");
    driver.findElement(By.xpath("/html/body/div/div[3]/form/button")).click();
    // Checa se foi criado
    // A proxima linha pode quebrar caso a posicao do usuario mude
    String cpf = driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr[4]/td[2]")).getText();
    // Exclui o usuario criado
    driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr[4]/td[4]/a")).click();
    // CLOSE + ASSERTS
    driver.close();
    assertEquals("000.000.000-00", cpf);
  }

  /**
   * RF1 e RF6 caso negativo
   */
  @Test
  public void testLogin_quandoUsuarioInvalido_entaoNaoEntra() {
    // SETUP : LOGIN
    driver.findElement(By.id("cpflogin")).sendKeys("  1  2  3  4  5  6  7  8  9  0  0");
    driver.findElement(By.id("senhalogin")).sendKeys("000");
    driver.findElement(By.xpath("/html/body/div/div[1]/div/form/button")).click();

    String mensagem = driver.findElement(By.xpath("/html/body/h1")).getText();
    // CLOSE + ASSERTS
    driver.close();
    assertEquals("ERRO!",mensagem);
  }
}
