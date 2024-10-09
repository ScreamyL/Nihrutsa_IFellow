# Автоматизированные тесты для сайта Edujira

---

**Данный проект предназначен для проведения ряда тестов на сайте Edujira. Тесты реализованы с использованием следующих
инструментов:**

- <a href="https://junit.org/junit5/" target="_blank"><img style="margin: 10px" src="https://junit.org/junit5/assets/img/junit5-logo.png" alt="JUnit" height="30" /></a> **JUnit** - для управления тестированием и структурирования тестов.
-  <a href="https://maven.apache.org/" target="_blank"><img style="margin: 10px" src="https://user-images.githubusercontent.com/25181517/117207242-07d5a700-adf4-11eb-975e-be04e62b984b.png" alt="Maven" height="30" /></a>**Maven** - для сборки проекта и управления зависимостями.
-  <a href="https://selenide.org/" target="_blank"><img style="margin: 10px" src="https://selenide.org/images/selenide-logo-big.png" alt="selenide" height="30" /></a> **Selenide** - для автоматизации браузерного тестирования.
- <a href="https://allurereport.org/" target="_blank"><img style="margin: 10px" src="https://allurereport.org/svg/logo-report-sign.svg" alt="Allure" height="30" /></a> **Allure** - для создания отчетов о тестах.

---

## Описание тестов

В проекте реализованы следующие тесты, выполняемые в определенном порядке:

1. **Логин тест:**
   Этот тест проверяет успешный логин пользователя на веб-сайте Edujira.

       @Test
       @Order(1)
       @DisplayName("Логин тест")
       public void testLogin() {
       loginPage.login(props.user(), props.password());
       loginPage.isLoggedIn();
       }


2. **Переход в проект 'Test':**
   Простой тест, удостоверяющийся, что пользователь может получить доступ к странице проекта после логина.

       @Test
       @Order(2)
       @DisplayName("Переход в проект 'Test'")
       public void testProjectsPageOpen() {
       loginPage.login(props.user(), props.password());
       projectPage.openProjectPage();
       }


3. **Получение количества созданных задач и проверка увеличения их числа при добавлении новой:**
   Этот тест подсчитывает текущее количество задач и проверяет его увеличение после создания новой задачи.

       @Test
       @Order(3)
       @DisplayName("Получение количества созданных задач и проверка увеличения их числа при добавлении новой")
       public void testTaskCountIncrease() {
       testProjectsPageOpen();
       int initialCount = projectPage.getTaskCount();
       projectPage.createTaskAndCount(props.taskkey(), initialCount);
       }


4. **Проверка параметров задачи:**
   В этом тесте проверяются детали задачи для их правильности.

       @Test
       @Order(4)
       @DisplayName("Проверка параметров задачи")
       public void testVerifyTaskParams() {
       loginPage.login(props.user(), props.password());
       projectPage.getTaskDetails(props.taskname());
       }


5. **Создание бага и проводка по статусам:**
   Этот тест создает баг и проверяет переходы статусов.

       @Test
       @Order(5)
       @DisplayName("Создание бага и проводка по статусам")
       public void createBugAndStatusChanges() {
       loginPage.login(props.user(), props.password());
       bugPage.transitionThroughStatuses(props.taskkey());
       }

> **Примечание:** Очередность тестов добавлена для удобства восприятия и не влияет на результат выполнения.

---

## Запуск тестов

Чтобы запустить тесты, выполните следующие шаги:

1. Убедитесь, что установлен JDK 17.

2. Клонируйте репозиторий:

       git clone https://github.com/ScreamyL/Nihrutsa_IFellow.git -b IF_HW_6


3. Перейдите в директорию вашего проекта:

       cd Nihrutsa_IFellow


4. Установите зависимости Maven:

       mvn install


5. Запустите тесты с помощью команды:

       mvn test / mvn clean test

---

## Отчеты Allure

После выполнения тестов вы можете сгенерировать отчеты Allure, чтобы просмотреть их в удобном формате:

     mvn allure:report
     mvn allure:serve

---