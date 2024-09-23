package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.То;
import io.cucumber.java.ru.Тогда;
import pages.ProjectPage;

import static org.junit.Assert.assertTrue;
import static utils.Props.props;

public class ProjectStep {


    private final ProjectPage projectPage = new ProjectPage();
    private int initialTaskCount;


    @Когда("^пользователь открывает страницу проекта$")
    public void userOpensProjectPage() {
        projectPage.openProjectPage();
    }

    @То("^он видит страницу проекта$")
    public void userSeesProjectPage() {
        assertTrue("Заголовок страницы не отображается", projectPage.isPageTitleVisible());
    }

    @Когда("^пользователь получает детали задачи с именем указанным в пропсах$")
    public void userGetsTaskDetails() {
        projectPage.getTaskDetails(props.taskname());
    }


    @Когда("^пользователь получает текущее количество задач$")
    public void userGetsCurrentTaskCount() {
        initialTaskCount = projectPage.getTaskCount();
        System.out.println("Текущее количество задач: " + initialTaskCount);
    }

    @Тогда("^пользователь создаёт новый баг и увеличивает количество задач на 1$")
    public void userCreatesNewBugAndCount() {
        projectPage.createTaskAndCount(props.taskkey(), initialTaskCount);
    }


}