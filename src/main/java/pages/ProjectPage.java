package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс PageObject для страницы проекта "Test"
 */
public class ProjectPage {

    private final SelenideElement projectsButton = $x("//a[text()='Проекты']").as("Кнопка 'Проекты'");
    private final SelenideElement testsButton = $x("//a[text()='Test (TEST)']").as("Кнопка проекта 'Test'");
    private final SelenideElement pageTitle = $x("//span[@id= 'issues-subnavigation-title']").as("Оглавление страницы");
    private final SelenideElement taskCount = $x("//div[@class= 'showing']").as("Количество задач");
    private final SelenideElement createTaskButton = $x("//a[@id= 'create_link']").as("Кнопка создания новой задачи");
    private final SelenideElement issueType = $x("//input[@id= 'issuetype-field']").as("Поле ввода типа задачи");
    private final SelenideElement taskStatus = $x("//span[@id= 'status-val']").as("Статус задачи");
    private final SelenideElement taskVersion = $x("//span[@id='fixfor-val']").as("Версия задачи");
    private final SelenideElement taskTitleInput = $x("//input[@class= 'text long-field']").as("Поле ввода названия задачи");
    private final SelenideElement toTextButton = $x("//label[text()='Описание']/following-sibling::div//li[@data-mode='source']/button[text()='Текст']").as("Кнопка переключения в режим 'Текст'");
    private final SelenideElement description = $x("//label[text()='Описание']/following-sibling::div//textarea[@id='description']").as("Поле ввода описания задачи");
    private final SelenideElement submitTaskButton = $x("//input[@value= 'Создать']").as("Кнопка подтверждения создания задачи");
    private final SelenideElement searchInput = $x("//input[@id= 'quickSearchInput']").as("Поле ввода поиска");
    private final SelenideElement successMessage = $x("//div[@class= 'aui-message closeable aui-message-success aui-will-close']/a[@class= 'issue-created-key issue-link']").as("Сообщение об успешно созданной задаче");

    @Step("Открытие страницы проекта")
    public void openProjectPage() {
        projectsButton.click();
        testsButton.click();
        assertTrue(pageTitle.shouldBe(Condition.visible).isDisplayed(), "Не удалось открыть страницу 'Тесты'");
    }


    @Step("Получение текущего количества задач")
    public int getTaskCount() {
        String countText = taskCount.getText();
        return Integer.parseInt(countText.split("из ")[1]);

    }

    public void createTask(String title) {
        createTaskButton.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        issueType.shouldBe(Condition.visible, Duration.ofSeconds(5)).setValue("Ошибка");
        taskTitleInput.setValue(title);
        toTextButton.click();
        description.setValue("Временный баг для проверки ДЗ");
        submitTaskButton.click();


    }

    @Step("Создание задачи и обновление количества задач")
    public void createTaskAndCount(String title, int initialCount) {
        createTask(title);
        successMessage.shouldBe(Condition.visible, Duration.ofSeconds(5));
        Selenide.refresh();
        int updatedCount = getTaskCount();
        assertEquals(updatedCount, initialCount + 1, "Количество задач не увеличилось на 1");
        System.out.println("Общее количество задач после добавления новой: " + updatedCount);
    }


    public boolean getCurrentStatus(String expected) {
        return taskStatus.getText().equals(expected);
    }

    @Step("Получение деталей задачи с именем {taskName}")
    public String[] getTaskDetails(String taskName) {

        searchInput.setValue(taskName).pressEnter();
        assertTrue(taskStatus.shouldBe(Condition.visible).isDisplayed(), "Не удалось найти параметр 'Статус'");
        assertTrue(taskVersion.shouldBe(Condition.visible).isDisplayed(), "Не удалось найти параметр 'Версия'");
        String status = taskStatus.getText();
        String version = taskVersion.getText();
        System.out.println("Параметры задачи успешно проверены: статус - " + status + ", версия - " + version);
        return new String[]{status, version};

    }

    public void transitionToTodo() {
        $x("//span[contains(text(), 'Нужно сделать')]").click();
        Selenide.sleep(1000);
        assert getCurrentStatus("СДЕЛАТЬ") : "Статус не изменился на 'Cделать'";
        getCurrentStatus("СДЕЛАТЬ");
    }

    public void transitionToInProgress() {
        $x("//span[contains(text(), 'В работе')]").click();
        Selenide.sleep(1000);
        assert getCurrentStatus("В РАБОТЕ") : "Статус не изменился на 'В работе'";
        getCurrentStatus("В РАБОТЕ");
    }

    public void transitionToDone() {
        $x("//a[@id='opsbar-transitions_more']").click();
        $x("//span[contains(text(), 'Исполнено')]").click();
        $x("//input[@id='issue-workflow-transition-submit']").click();
        Selenide.sleep(1000);
        assert getCurrentStatus("РЕШЕННЫЕ") : "Статус не изменился на 'Решенные'";
        getCurrentStatus("РЕШЕННЫЕ");
    }

    public void transitionToCompleted() {
        $x("//a[@id='opsbar-transitions_more']").click();
        $x("//span[contains(text(), 'Выполнено')]").click();
        Selenide.sleep(1000);
        assert getCurrentStatus("ГОТОВО") : "Статус не изменился на 'Готово'";
        getCurrentStatus("ГОТОВО");
    }

    @Step("Прохождение всех статусов для задачи с заголовком {title}")
    public void transitionThroughStatuses(String title) {
        createTask(title);
        successMessage.shouldBe(Condition.visible, Duration.ofSeconds(10));
        String issueKey = successMessage.getAttribute("href");
        assert issueKey != null;
        open(issueKey);
        transitionToTodo();
        transitionToInProgress();
        transitionToDone();
        transitionToCompleted();
    }

}
