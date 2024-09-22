package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;

import static com.codeborne.selenide.Selenide.closeWebDriver;

/**
 * Класс для настройки браузера перед выполнением тестов.
 */
public class WebHooks {


    @BeforeEach
    public void initBrowser() {
        Selenide.open("https://edujira.ifellow.ru/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        System.out.println((Configuration.pageLoadStrategy));
        Configuration.pageLoadStrategy = PageLoadStrategy.NORMAL.toString();
        Configuration.timeout = 15000;
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

}
