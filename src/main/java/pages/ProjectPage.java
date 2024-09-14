package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;


public class ProjectPage {

    private final SelenideElement ProjectsButton = $(By.xpath("//a[text()='Проекты']"));
    private final SelenideElement TestsButton = $(By.xpath("//a[text()='Test (TEST)']"));
    private final SelenideElement PageTitle = $(By.xpath("//span[@id= 'issues-subnavigation-title']"));
    private final SelenideElement taskCount = $(By.xpath("//div[@class= 'showing']"));
    private final SelenideElement createTaskButton = $(By.xpath("//a[@id= 'create_link']"));
    private final SelenideElement taskTitleInput = $(By.xpath("//input[@class= 'text long-field']"));
    private final SelenideElement submitTaskButton = $(By.xpath("//input[@value= 'Создать']"));
    private final SelenideElement searchInput = $(By.xpath("//input[@id= 'quickSearchInput']"));


    public void clickProjectsButton() {
        ProjectsButton.click();
    }

    public void clickTestsButton() {
        TestsButton.click();
    }

    public void OpenProjectPage() {
        clickProjectsButton();
        clickTestsButton();
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
        taskTitleInput.setValue(title);
        submitTaskButton.click();
        Selenide.sleep(5000); //Сорри, не очень красиво, но с видимыми ожиданиями страница почему-то не обновлялась
        Selenide.refresh();
        int updatedCount = getTaskCount();
        System.out.println("Общее количество задач после добавления новой: " + updatedCount);
    }

    public boolean isTaskCountIncreased(int previousCount) {
        return getTaskCount() == previousCount + 1;
    }

    public String[] getTaskDetails(String taskName) {
        searchInput.setValue(taskName).pressEnter();
        String status = $(By.xpath("//span[@id= 'status-val']")).getText();
        String version = $(By.xpath("//span[@id='fixfor-val']")).getText();
        return new String[]{status, version};

    }


}
