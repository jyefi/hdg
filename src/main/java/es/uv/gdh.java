package es.uv;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class gdh {

    static Logger log = LogManager.getLogger(gdh.class.getName());

    public static void main(String[] args) throws InterruptedException {

        log.info("Inicio programa");

        //Parámetros
        String user=null;
        String pass=null;
        Boolean silentMode = false;
        Boolean checkBefore = false;
        Boolean checkAfter = false;
        ChromeDriver driver=null;
        String attendType=null;
        Actions action=null;
        String lang=null;
        JavascriptExecutor js = null;
        Alert alert = null;
        String textAlert=null;
        String mainWindowHandle = null;
        String text=null;
        boolean isAlreadyOpened=false;
        boolean isAlreadyClosed=false;
        String fecha = null;

        int min = 152427; // Minimum value of range
        int max = 726837; // Maximum value of range

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].contains("=")) {
                    String[] parts = args[i].split("=");
                    switch (parts[0].toString()) {
                        case "user":
                            user = parts[1];
                            break;
                        case "pass":
                            pass = parts[1];
                            break;
                        case "silent":
                            silentMode = Boolean.parseBoolean(parts[1]);
                            break;
                        case "checkBefore":
                            checkBefore = Boolean.parseBoolean(parts[1]);
                            break;
                        case "checkAfter":
                            checkAfter = Boolean.parseBoolean(parts[1]);
                            break;
                        case "attendType":
                            attendType = parts[1];
                            break;
                        case "lang":
                            lang = parts[1];
                            break;
                        default:
                            System.out.println("Warning: invalid argument");
                            break;
                    }
                }
            }
        }
        else{
            showOptions();
            System.exit(1);
        }

        if (user == null || pass == null) {
            System.out.println("Error: user or pass not set");
            System.exit(1);
        }

        log.info("Tipo marcado:" + attendType);

        int random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);

        String espera;

        espera="Espero ";
        espera += String.valueOf(random_int);
        espera += " milisegundos.";
        log.info(espera);

        //java.lang.Thread.sleep(random_int);

        WebElement anchor;

        try {
            System.setProperty("webdriver.chrome.driver", "/home/jaime/Documents/dev/chromedriver/chromedriver");
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("--webview-enable-modern-cookie-same-site");
            //options.addArguments("--bound-session-cookie-rotation-delay");
            //options.addArguments("--same-site-by-default-cookies");

            if (silentMode == true) {
                options.addArguments("--headless");
            }
            else{
                options.addArguments("--start-maximized");
            }

            driver = new ChromeDriver(options);
            mainWindowHandle = driver.getWindowHandle();

            //Login
            driver.get("about:blank"); 
            Thread.sleep(3000);
            driver.get("http://gdh.uv.es");
            Thread.sleep(3000); // Espera a que termine de cargar la página (3 segundos)

            //Fill form
            anchor = driver.findElement(By.id("username"));
            anchor.sendKeys(user);
            anchor = driver.findElement(By.id("password"));
            anchor.sendKeys(pass);
    
            //Botón de login
            anchor = driver.findElement(By.id("botonLdap"));
            anchor.click();
            Thread.sleep(3000);

            //idioma
            if (lang != null && lang.equals("es")){
                anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[2]/ul/li/p/a[2]"));
                anchor.click();
                Thread.sleep(3000);

                alert = driver.switchTo().alert();
                log.info(alert.getText());
                alert.accept();

                Thread.sleep(3000);

                // Guarda el identificador de la ventana principal
                mainWindowHandle = driver.getWindowHandle();

                // Obtén todos los identificadores de las ventanas abiertas
                Set<String> allWindowHandles = driver.getWindowHandles();

                // Itera sobre todos los identificadores para encontrar la nueva ventana
                for (String windowHandle : allWindowHandles) {
                    if (!windowHandle.equals(mainWindowHandle)) {
                        // Cambia el control a la nueva ventana
                        driver.switchTo().window(windowHandle);

                        // Aquí puedes realizar las acciones necesarias en la nueva ventana
                        // Por ejemplo, verificar el título de la ventana emergente
                        System.out.println("Título de la ventana popup (window.open): " + driver.getTitle());

                        // Cierra la ventana emergente
                        driver.close();

                        // Vuelve a cambiar el control a la ventana principal
                        driver.switchTo().window(mainWindowHandle);
                    }
                }

                driver.get("http://webges.uv.es/uvGestionHorariaJJWeb/logout");

                //Do login again
                //Login
                driver.get("about:blank"); 
                driver.get("http://gdh.uv.es");
                Thread.sleep(3000); // Espera a que termine de cargar la página (3 segundos)

                //Fill form
                anchor = driver.findElement(By.id("username"));
                anchor.sendKeys(user);
                anchor = driver.findElement(By.id("password"));
                anchor.sendKeys(pass);
        
                //Botón de login
                anchor = driver.findElement(By.id("botonLdap"));
                anchor.click();
                Thread.sleep(3000);
            }

            // Bloque de entrada
            if ((attendType.equals("entrada"))){

                //comprueba si ya ha marcado la entrada
                if (checkBefore){

                    //Simular el movimiento del ratón
                    action = new Actions(driver);
                    anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td/a"));
                    action.moveToElement(anchor).perform();

                    //Simular un clic del ratón
                    action.click().perform();
                    Thread.sleep(3000);

                    js = (JavascriptExecutor) driver;

                    fecha = java.time.LocalDate.now().toString();
                    js.executeScript("enviar('" + fecha + "');");

                    // Esperar un poco para ver la alerta
                    Thread.sleep(3000);
                    
                    anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[3]/form[3]/table[3]/tbody/tr[2]/td[2]"));
                    text = anchor.getText().trim();
                    if ("Sin fichajes".equals(text) || "Sense fitxatges".equals(text)){
                        log.info("No ha marcado la entrada");
                    }
                    else{
                        log.info("Ya ha marcado la entrada, estado: " + text);
                        log.info("Saliendo del sistema");
                        isAlreadyOpened=true;
                    }
                }
                if(isAlreadyOpened){
                    driver.close();
                    log.info("Fin de ejecución");
                    System.exit(1);
                }
                else{
                    //Registre
                    action = new Actions(driver);
                    anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table[1]/tbody/tr/td/a"));
                    action.moveToElement(anchor).perform();

                    //Simular un clic del ratón
                    action.click().perform();
                    Thread.sleep(3000);

                    //Entrada
                    anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[3]/table/tbody/tr[2]/td[1]/a"));
                    anchor.click();

                    try {
                        alert = driver.switchTo().alert();
                        if (alert != null) {
                            Thread.sleep(3000);
                            textAlert = alert.getText();
                            alert.accept();
                            log.info(textAlert);
                        }
                    } catch (Exception e) {
                        log.info("No existe alert");
                    }
                    }

            }

            // Bloque de salida
            if (attendType.equals("salida")){

                //Simular la entrada a "Calendario"
                anchor= driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td/a"));
                action = new Actions(driver);
                action.moveToElement(anchor).perform();
                action.click().perform();
                Thread.sleep(3000);

                js = (JavascriptExecutor) driver;

                fecha = java.time.LocalDate.now().toString();
                js.executeScript("enviar('" + fecha + "');");

                // Esperar un poco para ver la alerta
                Thread.sleep(3000);

                //Obtiene el Estado
                anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[3]/form[3]/table[3]/tbody/tr[2]/td[2]"));
                text = anchor.getText().trim();
                if ("No complet".equals(text) || "No Completo".equals(text)){
                    log.info("Pendiente de fichar salida [OK]");
                }
                else{
                    log.info("Revisar, estado: " + text);
                    log.info("Saliendo del sistema");
                    isAlreadyClosed=true;                   
                }
                
                if (isAlreadyClosed){
                    driver.close();
                    log.info("Fin de ejecución");
                    System.exit(1);
                }
                else{

                    //Registre
                    action = new Actions(driver);
                    anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table[1]/tbody/tr/td/a"));
                    action.moveToElement(anchor).perform();

                    //Simular un clic del ratón
                    action.click().perform();
                    Thread.sleep(3000);

                    //Salida
                    anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[3]/table/tbody/tr[2]/td[2]/a"));
                                //ejecuta la entrada/salida
                    anchor.click();

                    try {
                        alert = driver.switchTo().alert();
                        if (alert != null) {
                            Thread.sleep(3000);
                            textAlert = alert.getText();
                            alert.accept();
                            log.info(textAlert);
                        }
                    } catch (Exception e) {
                        log.info("No existe alert (¿Ya ha marcado la entrada?)");
                    }
                    if (checkAfter){
                        //Simular la entrada a "Calendario"
                        action = new Actions(driver);
                        anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td/a"));
                        action.moveToElement(anchor).perform();
        
                        action.click().perform();
                        Thread.sleep(3000);
        
                        js = (JavascriptExecutor) driver;
        
                        fecha = java.time.LocalDate.now().toString();
                        js.executeScript("enviar('" + fecha + "');");
                        
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
        
                        //Obtiene el Estado
                        anchor = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr/td[3]/form[3]/table[3]/tbody/tr[2]/td[2]"));
                        text = anchor.getText().trim();
                        if ("Complet".equals(text) || "Completo".equals(text)){
                            log.info("Ya ha marcado la salida");
                            attendType="Ya marcado";
                        }
                        else{
                            log.info("Revisar, estado: " + text);
                            log.info("Saliendo del sistema");
                        }

                        //driver.wait(3000);
                        Thread.sleep(3000);
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally
        {
            driver.close();
            System.out.println("Fin de ejecución");
        }
    }   
    public static void showOptions(){
        System.out.println("use \"java -jar gdh-1.0.jar user=[jhondoe] pass=[1234] silent=[true|false] checkBefore=[true|false] checkAfter=[true|false] attendType=[entrada|salida] lang=[es|ca]\"");
    }
}

