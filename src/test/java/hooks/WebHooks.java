package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.closeWebDriver;

/**
 * Класс для настройки браузера перед выполнением тестов.
 */
public class WebHooks {


    @BeforeEach
    public void initBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.browserSize = null;
        Configuration.pageLoadStrategy = PageLoadStrategy.NORMAL.toString();
        Configuration.timeout = 15000;
        Selenide.open("https://edujira.ifellow.ru/");
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

    }

}
