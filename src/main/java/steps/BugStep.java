package steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.BugPage;
import pages.LoginPage;

import static utils.Props.props;

public class BugStep {

    private final LoginPage loginPage = new LoginPage();
    private final BugPage bugPage = new BugPage();

    @Дано("^пользователь вошел в систему$")
    public void userIsLoggedIn() {
        loginPage.login(props.user(), props.password());
    }

    @Когда("^он авторизован$")
    public void loginSuccess() {
        loginPage.isLoggedIn();
    }

    @Когда("^Когда пользователь создаёт новый баг и проводит его через статусы$")
    public void createNewBugAndGoThroughStatuses() {
        bugPage.transitionThroughStatuses(props.taskkey());
    }

    @Тогда("статус бага должен быть изменён на {string}")
    public void bugStatusCanChangeTo(String status) {
        bugPage.getCurrentStatus(status);
    }
}