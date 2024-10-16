package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SetValueOptions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс PageObject для страницы "Авторизация"
 */
public class LoginPage {

    private final SelenideElement usernameInput = $x("//input[@id= 'login-form-username']")
            .as("Поле ввода логина");
    private final SelenideElement passwordInput = $x("//input[@id= 'login-form-password']")
            .as("Поле ввода пароля");
    private final SelenideElement loginButton = $x("//input[@class= 'aui-button aui-button-primary']")
            .as("Кнопка 'Войти'");
    private final SelenideElement userProfile = $x("//a[@id= 'header-details-user-fullname']")
            .as("Пользовательский профиль");


    public LoginPage setCredentials(String username, String password) {
        usernameInput.setValue(username);
        Allure.getLifecycle().updateStep(stepResult ->
                stepResult.getParameters().add(1,
                        new Parameter().setName(stepResult.getParameters().remove(1).getName()).setValue("***")));
        passwordInput.setValue(SetValueOptions.withText(password).sensitive());
        return this;
    }


    /**
     * @param username - логин пользователя
     * @param password - пароль пользователя
     */
    @Step("Авторизация с использованием пользователя {username}")
    public void login(String username, String password) {
        setCredentials(username, password);
        loginButton.click();
        userProfile.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверка, вошел ли пользователь в систему")
    public void isLoggedIn() {
        boolean loggedIn = userProfile.isDisplayed();
        assertTrue(loggedIn, "Пользователь не вошел в систему");
    }


}
