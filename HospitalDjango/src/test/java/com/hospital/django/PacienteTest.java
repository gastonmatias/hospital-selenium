package com.hospital.django;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PacienteTest {
	
	//creacion objeto webdriver, importando libreria de selenium WebDriver
	private WebDriver driver;
	
	/*LOCALIZADORES*/
	//  Login
	By userLocator = By.name("username");
	By passLocator = By.name("password");
	By signInLocator = By.name("submit");
	By homePageLocator = By.id("img-home");
	
	// ingresar pestaña Paciente
	By pacienteLinkLocator = By.linkText("Pacientes");
	By listaTableLocator = By.className("titulo-lista");
	
	// ingresar al form registro de pacientes
	By agregarLinkLocator = By.id("agregar-paciente");
	By registroFormLocator = By.name("registro-paciente");
	
	//para  relleno de form registro paciente
	By nameLocator = By.name("nombre");
	By apellidoLocator = By.name("apellido");
	By rutLocator = By.name("rut");
	By telefonoLocator = By.name("telefono");
	By celularLocator = By.name("celular");
	By emailLocator = By.name("email");
	By nacionalidadLocator = By.name("nacionalidad");
	By fechaNacimientoLocator = By.name("fecha_nacimiento");
	By descripcionLocator = By.name("descripcion");
	By fotoLocator = By.xpath("//input[@type='file']");
	By nickLocator = By.name("nickname");
	By registrarBtnLocator = By.xpath("//input[@type='submit']");
	
	// Confirmación paciente agregado
	By avisoOkLocator = By.xpath("//button[@class='swal2-confirm swal2-styled']");
	
	// actualizar paciente
	By modificarBtnLocator = By.id("modificar-paciente");
	By actualizarBtnLocator = By.xpath("//input[@type='submit']");
	
	@Before
	public void setUp() throws Exception {
		//se indica donde esta el ejecutable a fin de qe el objeto "driver" (creado mas abajo) sea reconocido
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver");
				
		//declaracion objeto driver
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://127.0.0.1:8000/accounts/login/");
		
	}
	
	// metodo para Iniciar sesion como medico 
	public void signIn() throws InterruptedException {
		// 1) confirmación de qe "se está" en la pagina de login, de ser asi
	   // entonces iniciar sesion con las crendenciales dispuestas
		if (driver.findElement(userLocator).isDisplayed()){
			driver.findElement(userLocator).sendKeys("house");
			driver.findElement(passLocator).sendKeys("django123");
			Thread.sleep(2000); //agregar throws o try-catch
			driver.findElement(signInLocator).click();
			
			//Si el inicio de sesion es correcto, comparar con "homePageLocator"
			//qe es la variable que contiene la "bienvenida" de la pagina
			assertTrue(driver.findElement(homePageLocator).isDisplayed());
		} else {
			System.out.println("username was not present");
		}
		
	}
	
	// metodo para ingresar a pestaña "paciente" y ver lista de pacientes registrados
	public void listarPaciente() throws InterruptedException{
		signIn();
		//dar click en pestaña "paciente" desde home
		Thread.sleep(2000);
		driver.findElement(pacienteLinkLocator).click();
		
		assertTrue(driver.findElement(listaTableLocator).isDisplayed());
	}

	// metodo qe prueba boton actualizar de un registro de paciente
	public void actualizarPaciente()throws InterruptedException{
		listarPaciente();
		driver.findElement(modificarBtnLocator).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		driver.findElement(actualizarBtnLocator).submit();

	}
	
	// TEST REGISTRAR PACIENTE
	@Test
	public void registrarPaciente() throws InterruptedException{
		//evocacion metodo listar_paciente()
		listarPaciente();
		// milisegundos de espera
		Thread.sleep(2000);
		//click boton agregar nuevo paciente
		driver.findElement(agregarLinkLocator).click();
		
		// creacion objeto js qe ayudara a "scroll down" en la pagina web
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		
		 // se determina si el form registro es desplegado
		if (driver.findElement(registroFormLocator).isDisplayed()) {
			//relleno de formulario registro paciente
			driver.findElement(nameLocator).sendKeys("Francisco");
			driver.findElement(apellidoLocator).sendKeys("Sanchez");
			driver.findElement(rutLocator).sendKeys("5657656-7");
			driver.findElement(telefonoLocator).sendKeys("256897352");
			driver.findElement(celularLocator).sendKeys("+56951849625");
			driver.findElement(emailLocator).sendKeys("paco@hotmail.es");
			driver.findElement(nacionalidadLocator).sendKeys("española");
			driver.findElement(fechaNacimientoLocator).sendKeys("21/12/1947");
			driver.findElement(descripcionLocator).sendKeys("guitarrista");
			driver.findElement(fotoLocator).sendKeys("/home/gmatias/Desktop/paco.jpg");
			driver.findElement(nickLocator).sendKeys("Paco de Lucia");
			
			// This  will scroll down the page by  1000 pixel vertical		
	        js.executeScript("window.scrollBy(0,1000)");
			
	        Thread.sleep(3000);
	        // click registrar luego de haber rellenado el form
	        driver.findElement(registrarBtnLocator).submit();
			
			// VERIFICAR aparicion cuadro de dialogo "Paciente Registrado"
			assertTrue(driver.findElement(avisoOkLocator).isDisplayed());
			
			// Click "ok" cuadro de dialogo
			Thread.sleep(3000);
			driver.findElement(avisoOkLocator).click();
		}
		else {
			System.out.println("error  was not found");
		}
		 js.executeScript("window.scrollBy(0,1000)");
		
		
		 //lista de elementos "td" de la tabla "listado pacientes"
		 //List<WebElement> tds = driver.findElements(By.tagName("td"));
		
		 //verificar si existe "td" con el email recien creado, correspondiente al index 53 de la lista
		 //assertEquals("paco@hotmail.es",tds.get(53).getAttribute("innerHtml"));
		
	}
	
	@After
	public void tearDown() throws Exception {
		//driver.quit();
	}

}
