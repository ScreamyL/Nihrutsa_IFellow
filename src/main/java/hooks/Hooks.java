package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.PageLoadStrategy;

import static com.codeborne.selenide.Selenide.closeWebDriver;

/**
 * Класс для настройки браузера перед выполнением тестов.
 */
public class Hooks {


    @Before
    public void initBrowser() {
        Selenide.open("https://edujira.ifellow.ru/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        System.out.println((Configuration.pageLoadStrategy));
        Configuration.pageLoadStrategy = PageLoadStrategy.NORMAL.toString();
        Configuration.timeout = 15000;
    }

    @After
    public void tearDown() {
        closeWebDriver();
    }

}
