package steps;


import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.LoginPage;

import static utils.Props.props;

public class LoginStep {


    private final LoginPage loginPage = new LoginPage();


    @Когда("^пользователь выполняет вход в систему$")
    public void userLogin() {
        loginPage.login(props.user(), props.password());
    }

    @Тогда("^пользователь успешно авторизован$")
    public void loginSuccess() {
        loginPage.isLoggedIn();
    }
}





