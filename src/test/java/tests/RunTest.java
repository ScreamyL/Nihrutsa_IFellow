package tests;


import config.WebHooks;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.ProjectPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RunTest extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final ProjectPage projectPage = new ProjectPage();

    @BeforeEach
    public void setUp() {
        loginPage.login("AT3", "Qwerty123");
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    @Test
    @Order(1)
    @DisplayName("Логин тест")
    @Step("Проверка логина пользователя")
    public void testLogin() {
        assertTrue(loginPage.isLoggedIn(), "Пользователь не вошел в систему");
    }

    @Test
    @Order(2)
    @DisplayName("Переход в проект 'Test'")
    @Step("Открытие страницы проектов")
    public void testProjectsPageOpen() {
        projectPage.openProjectPage();
        assertTrue(projectPage.isOpenProjectTestPage(), "Не удалось открыть страницу 'Тесты'");
    }

    @Test
    @Order(3)
    @DisplayName("Получение количества созданных задач и проверка увеличения их числа при добавлении новой")
    @Step("Получение текущего количества задач")
    public void testTaskCountIncrease() {
        testProjectsPageOpen();
        int initialCount = projectPage.getTaskCount();
        projectPage.createTaskAndCount("Тестовая задача");
        assertTrue(projectPage.isTaskCountIncreased(initialCount), "Количество задач не увеличилось на 1");
    }

    @Test
    @Order(4)
    @DisplayName("Проверка параметров задачи")
    @Step("Проверка статуса и версии задачи")
    public void testVerifyTaskParams() {
        String[] taskDetails = projectPage.getTaskDetails("TestSeleniumATHomework");
        assertEquals("СДЕЛАТЬ", taskDetails[0], "Ожидался статус: 'СДЕЛАТЬ', но найден: " + taskDetails[0]);
        assertEquals("Version 2.0", taskDetails[1], "Ожидалась версия: 'Version 2.0', но найдена: " + taskDetails[1]);
        System.out.println("Параметры задачи успешно проверены: статус - " + taskDetails[0] + ", версия - " + taskDetails[1]);
    }

    @Test
    @Order(5)
    @DisplayName("Создание бага и проводка по статусам")
    @Step("Создание бага и изменение его статусов")
    public void createBugAndStatusChanges() {
        projectPage.transitionThroughStatuses("Тестовая задача");
    }

}