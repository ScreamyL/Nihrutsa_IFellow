package tests;


import hooks.Hooks;
import org.junit.jupiter.api.*;
import pages.BugPage;
import pages.LoginPage;
import pages.ProjectPage;

import static utils.Props.props;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//Очерёдность добавлена больше для 'Красоты' выполнения тестов, без неё всё тоже будет отлично работать.
public class RunTest extends Hooks {

    private final LoginPage loginPage = new LoginPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final BugPage bugPage = new BugPage();


    @Test

    @Order(1)
    @DisplayName("Логин тест")
    public void testLogin() {
        loginPage.login(props.user(), props.password());
        loginPage.isLoggedIn();
    }

    @Test
    @Order(2)
    @DisplayName("Переход в проект 'Test'")
    public void testProjectsPageOpen() {
        loginPage.login(props.user(), props.password());
        projectPage.openProjectPage();
    }

    @Test
    @Order(3)
    @DisplayName("Получение количества созданных задач и проверка увеличения их числа при добавлении новой")
    public void testTaskCountIncrease() {
        testProjectsPageOpen();
        int initialCount = projectPage.getTaskCount();
        projectPage.createTaskAndCount(props.taskkey(), initialCount);
    }

    @Test
    @Order(4)
    @DisplayName("Проверка параметров задачи")
    public void testVerifyTaskParams() {
        loginPage.login(props.user(), props.password());
        projectPage.getTaskDetails(props.taskname());
    }

    @Test
    @Order(5)
    @DisplayName("Создание бага и проводка по статусам")
    public void createBugAndStatusChanges() {
        loginPage.login(props.user(), props.password());
        bugPage.transitionThroughStatuses(props.taskkey());
    }


}