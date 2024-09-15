package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

/**
 * Класс для представления страницы проекта "Test".
 * Предоставляет собой методы для взаимодействия с элементами страницы проекта,
 * такими как создание задач, изменение их статусов и получение информации о проекте.
 */
public class ProjectPage {

    private final SelenideElement ProjectsButton = $($x("//a[text()='Проекты']"));
    private final SelenideElement TestsButton = $($x("//a[text()='Test (TEST)']"));
    private final SelenideElement PageTitle = $($x("//span[@id= 'issues-subnavigation-title']"));
    private final SelenideElement taskCount = $($x("//div[@class= 'showing']"));
    private final SelenideElement createTaskButton = $($x("//a[@id= 'create_link']"));
    private final SelenideElement issueType = $($x("//input[@id= 'issuetype-field']"));
    private final SelenideElement taskTitleInput = $($x("//input[@class= 'text long-field']"));
    private final SelenideElement toTextButton = $($x("//label[text()='Описание']/following-sibling::div//li[@data-mode='source']/button[text()='Текст']"));
    private final SelenideElement description = $($x("//label[text()='Описание']/following-sibling::div//textarea[@id='description']"));
    private final SelenideElement submitTaskButton = $($x("//input[@value= 'Создать']"));
    private final SelenideElement searchInput = $($x("//input[@id= 'quickSearchInput']"));
    private final SelenideElement successMessage = $($x("//div[@class= 'aui-message closeable aui-message-success aui-will-close']/a[@class= 'issue-created-key issue-link']"));

    @Step("Открытие страницы проекта")
    public void openProjectPage() {
        ProjectsButton.click();
        TestsButton.click();
    }

    @Step("Проверка, открыта ли страница проекта 'Test'")
    public boolean isOpenProjectTestPage() {
        return PageTitle.shouldBe(Condition.visible).isDisplayed();
    }

    @Step("Получение текущего количества задач")
    public int getTaskCount() {
        String countText = taskCount.getText();
        String[] parts = countText.split(" ");
        return Integer.parseInt(parts[2]);

    }

    public void createTask(String title) {
        createTaskButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        createTaskButton.click();
        issueType.shouldBe(Condition.visible, Duration.ofSeconds(5));
        issueType.setValue("Ошибка");
        taskTitleInput.setValue(title);
        toTextButton.click();
        description.setValue("Временный баг для проверки ДЗ");
        submitTaskButton.click();


    }

    @Step("Создание задачи и обновление количества задач")
    public void createTaskAndCount(String title) {
        createTask(title);
        successMessage.shouldBe(Condition.visible, Duration.ofSeconds(5));
        Selenide.refresh();
        int updatedCount = getTaskCount();
        System.out.println("Общее количество задач после добавления новой: " + updatedCount);
    }

    @Step("Проверка, увеличилось ли количество задач на 1")
    public boolean isTaskCountIncreased(int previousCount) {
        return getTaskCount() == previousCount + 1;
    }

    public String getCurrentStatus() {
        return $($x("//span[@id= 'status-val']")).getText();
    }

    @Step("Получение деталей задачи с именем {taskName}")
    public String[] getTaskDetails(String taskName) {

        searchInput.setValue(taskName).pressEnter();
        String status = $($x("//span[@id= 'status-val']")).getText();
        String version = $($x("//span[@id='fixfor-val']")).getText();
        return new String[]{status, version};

    }

    public void transitionToTodo() {
        $($x("//span[contains(text(), 'Нужно сделать')]")).click();
        Selenide.sleep(1000);
        assert getCurrentStatus().equals("СДЕЛАТЬ") : "Статус не изменился на 'Cделать'";
        System.out.println("Статус: 'Cделать'");
    }

    public void transitionToInProgress() {
        $($x("//span[contains(text(), 'В работе')]")).click();
        Selenide.sleep(1000);
        assert getCurrentStatus().equals("В РАБОТЕ") : "Статус не изменился на 'В работе'";
        System.out.println("Статус: 'В работе'");
    }

    public void transitionToDone() {
        $($x("//a[@id='opsbar-transitions_more']")).click();
        $($x("//span[contains(text(), 'Исполнено')]")).click();
        $($x("//input[@id='issue-workflow-transition-submit']")).click();
        Selenide.sleep(1000);
        assert getCurrentStatus().equals("РЕШЕННЫЕ") : "Статус не изменился на 'Решенные'";
        System.out.println("Статус: 'Решенные'");
    }

    public void transitionToCompleted() {
        $($x("//a[@id='opsbar-transitions_more']")).click();
        $($x("//span[contains(text(), 'Выполнено')]")).click();
        Selenide.sleep(1000);
        assert getCurrentStatus().equals("ГОТОВО") : "Статус не изменился на 'Готово'";
        System.out.println("Статус: 'Готово'");
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
