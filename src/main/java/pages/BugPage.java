package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс PageObject для страницы создания и описания бага
 */
public class BugPage {


    private final SelenideElement taskStatus = $x("//span[@id= 'status-val']").as("Статус задачи");
    private final SelenideElement successMessage = $x("//div[@class= 'aui-message closeable aui-message-success aui-will-close']/a[@class= 'issue-created-key issue-link']").as("Сообщение об успешно созданной задаче");
    private final SelenideElement toDoButton = $x("//span[contains(text(), 'Нужно сделать')]").as("Кнопка статуса задачи 'Сделать'");
    private final SelenideElement inProgressButton = $x("//span[contains(text(), 'В работе')]").as("Кнопка статуса задачи 'В работе'");
    private final SelenideElement moreStatButton = $x("//a[@id='opsbar-transitions_more']").as("Кнопка выпадающего меню статусов");
    private final SelenideElement toDoneButton = $x("//span[contains(text(), 'Исполнено')]").as("Кнопка статуса задачи 'Исполнено'");
    private final SelenideElement submitDoneButton = $x("//input[@id='issue-workflow-transition-submit']").as("Кнопка подтверждения статуса 'Исполнено'");
    private final SelenideElement toCompletedButton = $x("//span[contains(text(), 'Выполнено')]").as("Кнопка статуса задачи 'Выполнено'");


    private final ProjectPage projectPage = new ProjectPage();


    public boolean getCurrentStatus(String expected) {
        taskStatus.shouldHave(Condition.text(expected), Duration.ofSeconds(10));
        String actualStatus = taskStatus.getText();
        assertEquals(expected, actualStatus, "Статус задачи не соответствует ожидаемому: " + expected);
        return true;
    }

    public void transitionToTodo() {
        toDoButton.click();
        getCurrentStatus("СДЕЛАТЬ");
    }

    public void transitionToInProgress() {
        inProgressButton.click();
        getCurrentStatus("В РАБОТЕ");
    }

    public void transitionToDone() {
        moreStatButton.click();
        toDoneButton.click();
        submitDoneButton.click();
        getCurrentStatus("РЕШЕННЫЕ");
    }

    public void transitionToCompleted() {
        moreStatButton.click();
        toCompletedButton.click();
        getCurrentStatus("ГОТОВО");
    }

    @Step("Прохождение всех статусов для задачи с заголовком {title}")
    public void transitionThroughStatuses(String title) {
        projectPage.createTask(title);
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
