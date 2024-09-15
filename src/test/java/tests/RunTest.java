package tests;


import config.WebHooks;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.ProjectPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RunTest extends WebHooks {

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    private final LoginPage loginPage = new LoginPage();
    private final ProjectPage openProjectsPage = new ProjectPage();

    @Test
    @Order(1)
    @DisplayName("Логин тест")
    public void testLogin() {
        loginPage.login("AT3", "Qwerty123");
        assertTrue(loginPage.isLoggedIn(), "Пользователь не вошел в систему");
    }

    @Test
    @Order(2)
    @DisplayName("Переход в проект 'Test'")
    public void testProjectsPageOpen() {
        testLogin();
        openProjectsPage.OpenProjectPage();
        assertTrue(openProjectsPage.isOpenProjectTestPage(), "Не удалось открыть страницу 'Тесты'");
    }

    @Test
    @Order(3)
    @DisplayName("Получение количества созданных задач и проверка увеличения их числа при добавлении новой")
    public void testTaskCountIncrease() {
        testProjectsPageOpen();
        int initialCount = openProjectsPage.getTaskCount();
        openProjectsPage.createTaskAndCount("Тестовая задача");
        assertTrue(openProjectsPage.isTaskCountIncreased(initialCount), "Количество задач не увеличилось на 1");
    }

    @Test
    @Order(4)
    @DisplayName("Проверка параметров задачи")
    public void testVerifyTaskParams() {
        testLogin();
        String[] taskDetails = openProjectsPage.getTaskDetails("TestSeleniumATHomework");
        String status = taskDetails[0];
        String version = taskDetails[1];
        assertEquals("СДЕЛАТЬ", status, "Ожидался статус: 'СДЕЛАТЬ', но найден: " + status);
        assertEquals("Version 2.0", version, "Ожидалась версия: 'Version 2.0', но найдена: " + version);
        System.out.println("Параметры задачи успешно проверены: статус - " + status + ", версия - " + version);
    }

    @Test
    @Order(5)
    @DisplayName("Создание бага и проводка по статусам")
    public void createBugAndStatusChanges() {
        testLogin();
        openProjectsPage.transitionThroughStatuses("Тестовая задача");
    }

}