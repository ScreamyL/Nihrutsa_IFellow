package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

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

    public void login(String username, String password) {
        setCredentials(username, password);
        clickLoginButton();
        userProfile.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public boolean isLoggedIn() {
        return userProfile.isDisplayed();
    }


}
