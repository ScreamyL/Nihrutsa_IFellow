package tests;


import config.WebHooks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProjectPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RunTest extends WebHooks {


    private final LoginPage loginPage = new LoginPage();
    private final ProjectPage openProjectsPage = new ProjectPage();

    private void loginUser() {
        loginPage.login("AT3", "Qwerty123");
        assertTrue(loginPage.isLoggedIn(), "Пользователь не вошел в систему");
    }

    private void openProjectsPage() {
        openProjectsPage.OpenProjectPage();
        assertTrue(openProjectsPage.isOpenProjectTestPage(), "Не удалось открыть страницу 'Тесты'");
    }

    @Test
    @DisplayName("Логин тест")
    public void testSuccessfulLogin() {
        loginUser();

    }

    @Test
    @DisplayName("Переход в проект 'Test'")
    public void testProjectsPageOpen() {
        loginUser();
        openProjectsPage();


    }

    @Test
    @DisplayName("Получение количества созданных задач и проверка увеличения их числа при добавлении новой")
    public void testTaskCountIncrease() {
        loginUser();
        openProjectsPage();
        int initialCount = openProjectsPage.getTaskCount();
        openProjectsPage.createTask("Тестовая задача");
        assertTrue(openProjectsPage.isTaskCountIncreased(initialCount), "Количество задач не увеличилось на 1");
    }

    @Test
    @DisplayName("Проверка параметров задачи")
    public void testVerifyTaskParams() {
        loginUser();
        String[] taskDetails = openProjectsPage.getTaskDetails("TestSeleniumATHomework");
        String status = taskDetails[0];
        String version = taskDetails[1];
        assertEquals("СДЕЛАТЬ", status, "Ожидался статус: 'СДЕЛАТЬ', но найден: " + status);
        assertEquals("Version 2.0", version, "Ожидалась версия: 'Version 2.0', но найдена: " + version);
        System.out.println("Параметры задачи успешно проверены: статус - " + status + ", версия - " + version);
    }


}