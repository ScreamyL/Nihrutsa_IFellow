package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.BugPage;

import static utils.Props.props;

public class BugStep {


    private final BugPage bugPage = new BugPage();


    @Когда("^Когда пользователь создаёт новый баг и проводит его через статусы$")
    public void createNewBugAndGoThroughStatuses() {
        bugPage.transitionThroughStatuses(props.taskkey());
    }

    @Тогда("статус бага должен быть изменён на {string}")
    public void bugStatusCanChangeTo(String status) {
        bugPage.getCurrentStatus(status);
    }
}