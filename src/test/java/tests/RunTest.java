package tests;


import config.WebHooks;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.ProjectPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RunTest extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final ProjectPage projectPage = new ProjectPage();

    @BeforeEach
    public void setUp() {
        loginPage.login("AT3", "Qwerty123");
    }


    @Test
    @Order(1)
    @DisplayName("Логин тест")
    public void testLogin() {
        loginPage.isLoggedIn();
    }

    @Test
    @Order(2)
    @DisplayName("Переход в проект 'Test'")
    public void testProjectsPageOpen() {
        projectPage.openProjectPage();
    }

    @Test
    @Order(3)
    @DisplayName("Получение количества созданных задач и проверка увеличения их числа при добавлении новой")
    public void testTaskCountIncrease() {
        testProjectsPageOpen();
        int initialCount = projectPage.getTaskCount();
        projectPage.createTaskAndCount("Тестовая задача", initialCount);
    }

    @Test
    @Order(4)
    @DisplayName("Проверка параметров задачи")
    public void testVerifyTaskParams() {
        projectPage.getTaskDetails("TestSeleniumATHomework");
    }

    @Test
    @Order(5)
    @DisplayName("Создание бага и проводка по статусам")
    public void createBugAndStatusChanges() {
        projectPage.transitionThroughStatuses("Тестовая задача");
    }

}