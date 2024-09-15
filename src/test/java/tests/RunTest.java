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
    public void testLogin() {
        assertTrue(loginPage.isLoggedIn(), "Пользователь не вошел в систему");
    }

    @Test
    @Order(2)
    @DisplayName("Переход в проект 'Test'")
    public void testProjectsPageOpen() {
        projectPage.openProjectPage();
        assertTrue(projectPage.isOpenProjectTestPage(), "Не удалось открыть страницу 'Тесты'");
    }

    @Test
    @Order(3)
    @DisplayName("Получение количества созданных задач и проверка увеличения их числа при добавлении новой")
    public void testTaskCountIncrease() {
        testProjectsPageOpen();
        int initialCount = projectPage.getTaskCount();
        projectPage.createTaskAndCount("Тестовая задача");
        assertTrue(projectPage.isTaskCountIncreased(initialCount), "Количество задач не увеличилось на 1");
    }

    @Test
    @Order(4)
    @DisplayName("Проверка параметров задачи")
    public void testVerifyTaskParams() {
        String[] taskDetails = projectPage.getTaskDetails("TestSeleniumATHomework");
        assertEquals("СДЕЛАТЬ", taskDetails[0], "Ожидался статус: 'СДЕЛАТЬ', но найден: " + taskDetails[0]);
        assertEquals("Version 2.0", taskDetails[1], "Ожидалась версия: 'Version 2.0', но найдена: " + taskDetails[1]);

        System.out.println("Параметры задачи успешно проверены: статус - " + taskDetails[0] + ", версия - " + taskDetails[1]);
    }

    @Test
    @Order(5)
    @DisplayName("Создание бага и проводка по статусам")
    public void createBugAndStatusChanges() {
        projectPage.transitionThroughStatuses("Тестовая задача");
    }

}