package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

/**
 * Класс для представления страницы входа в систему.
 * Содержит методы для взаимодействия с элементами страницы входа,
 * такими как поля для ввода имени пользователя и пароля, а также кнопку входа.
 */
public class LoginPage {

    private final SelenideElement usernameInput = $(By.xpath("//input[@id= 'login-form-username']"));
    private final SelenideElement passwordInput = $(By.xpath("//input[@id= 'login-form-password']"));
    private final SelenideElement loginButton = $(By.xpath("//input[@class= 'aui-button aui-button-primary']"));
    private final SelenideElement userProfile = $(By.xpath("//a[@id= 'header-details-user-fullname']"));


    public LoginPage setCredentials(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        return this;
    }


    public void clickLoginButton() {
        loginButton.click();
    }

    @Step("Авторизация с использованием пользователя {username}")
    public void login(String username, String password) {
        setCredentials(username, password);
        clickLoginButton();
        userProfile.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Проверка, вошел ли пользователь в систему")
    public boolean isLoggedIn() {
        userProfile.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return userProfile.isDisplayed();
    }


}
