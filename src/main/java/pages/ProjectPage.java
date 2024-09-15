package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ProjectPage {

    private final SelenideElement ProjectsButton = $(By.xpath("//a[text()='Проекты']"));
    private final SelenideElement TestsButton = $(By.xpath("//a[text()='Test (TEST)']"));
    private final SelenideElement PageTitle = $(By.xpath("//span[@id= 'issues-subnavigation-title']"));
    private final SelenideElement taskCount = $(By.xpath("//div[@class= 'showing']"));
    private final SelenideElement createTaskButton = $(By.xpath("//a[@id= 'create_link']"));
    private final SelenideElement issueType = $(By.xpath("//input[@id= 'issuetype-field']"));
    private final SelenideElement taskTitleInput = $(By.xpath("//input[@class= 'text long-field']"));
    private final SelenideElement submitTaskButton = $(By.xpath("//input[@value= 'Создать']"));
    private final SelenideElement searchInput = $(By.xpath("//input[@id= 'quickSearchInput']"));
    private final SelenideElement successMessage = $(By.xpath("//div[@class= 'aui-message closeable aui-message-success aui-will-close']/a[@class= 'issue-created-key issue-link']"));


    public void openProjectPage() {
        ProjectsButton.click();
        TestsButton.click();
    }

    public boolean isOpenProjectTestPage() {
        return PageTitle.shouldBe(Condition.visible).isDisplayed();
    }

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
        submitTaskButton.click();


    }

    public void createTaskAndCount(String title) {
        createTask(title);
        Selenide.sleep(3000);//Сорри, не очень красиво, но с видимыми ожиданиями страница почему-то не обновлялась
        Selenide.refresh();
        int updatedCount = getTaskCount();
        System.out.println("Общее количество задач после добавления новой: " + updatedCount);
    }

    public boolean isTaskCountIncreased(int previousCount) {
        return getTaskCount() == previousCount + 1;
    }

    public String getCurrentStatus() {
        return $(By.xpath("//span[@id= 'status-val']")).getText();
    }

    public String[] getTaskDetails(String taskName) {
        searchInput.setValue(taskName).pressEnter();
        String status = $(By.xpath("//span[@id= 'status-val']")).getText();
        String version = $(By.xpath("//span[@id='fixfor-val']")).getText();
        return new String[]{status, version};

    }

    public void transitionToTodo() {
        $(By.xpath("//span[contains(text(), 'Нужно сделать')]")).click();
        Selenide.sleep(1000);
        assert getCurrentStatus().equals("СДЕЛАТЬ") : "Статус не изменился на 'Cделать'";
        System.out.println("Статус: 'Cделать'");
    }

    public void transitionToInProgress() {
        $(By.xpath("//span[contains(text(), 'В работе')]")).click();
        Selenide.sleep(1000);
        assert getCurrentStatus().equals("В РАБОТЕ") : "Статус не изменился на 'В работе'";
        System.out.println("Статус: 'В работе'");
    }

    public void transitionToDone() {
        $(By.xpath("//a[@id='opsbar-transitions_more']")).click();
        $(By.xpath("//span[contains(text(), 'Исполнено')]")).click();
        $(By.xpath("//input[@id='issue-workflow-transition-submit']")).click();
        Selenide.sleep(1000);
        assert getCurrentStatus().equals("РЕШЕННЫЕ") : "Статус не изменился на 'Решенные'";
        System.out.println("Статус: 'Решенные'");
    }

    public void transitionToCompleted() {
        $(By.xpath("//a[@id='opsbar-transitions_more']")).click();
        $(By.xpath("//span[contains(text(), 'Выполнено')]")).click();
        Selenide.sleep(1000);
        assert getCurrentStatus().equals("ГОТОВО") : "Статус не изменился на 'Готово'";
        System.out.println("Статус: 'Готово'");
    }

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
