package tests;


import config.WebHooks;
import org.junit.jupiter.api.*;
import pages.BugPage;
import pages.LoginPage;
import pages.ProjectPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//Очерёдность добавлена больше для 'Красоты' выполнения тестов, без неё всё тоже будет отлично работать.
public class RunTest extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final BugPage bugPage = new BugPage();
    @BeforeEach
    public void setUp() {
        loginPage.login("AT3", "Qwerty123");
    }


    @Test
    //Тест ради теста именно с помощью ассерта, по сути залогинивание и так проверяется в методе логина путём видимых ожиданий.
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
        bugPage.transitionThroughStatuses("Тестовая задача");
    }

}