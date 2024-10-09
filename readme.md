# Набор тестов Cucumber для проведения двух тестов по Api

---

**Данный проект предназначен для проведения  двух тестов по Api.**

**Тесты реализованы с использованием следующих инструментов:**

- <a href="https://junit.org/junit5/" target="_blank"><img style="margin: 10px" src="https://junit.org/junit5/assets/img/junit5-logo.png" alt="JUnit" height="30" /></a> **JUnit** - для управления тестированием и структурирования тестов.
- <a href="https://maven.apache.org/" target="_blank"><img style="margin: 10px" src="https://user-images.githubusercontent.com/25181517/117207242-07d5a700-adf4-11eb-975e-be04e62b984b.png" alt="Maven" height="30" /></a> **Maven** - для сборки проекта и управления зависимостями.
- <a href="https://cucumber.io/" target="_blank"><img style="margin: 10px" src="https://user-images.githubusercontent.com/25181517/184117353-4b437677-c4bb-4f4c-b448-af4920576732.png" alt="Cucumber" height="30" /></a> **Cucumber** - для написания тестов в формате Gherkin, ориентированных на поведение.
- <a href="https://allurereport.org/" target="_blank"><img style="margin: 10px" src="https://allurereport.org/svg/logo-report-sign.svg" alt="Allure" height="30" /></a>**Allure** - для создания отчетов о тестах.
- <a href="https://wikipedia.org/wiki/REST" target="_blank"><img style="margin: 10px" src="https://user-images.githubusercontent.com/25181517/192107858-fe19f043-c502-4009-8c47-476fc89718ad.png" alt="REST" height="30" /></a> **RestAssured** - для тестирования API.

---

## Описание тестов


#### Первый раздел тестов проверяет создание potato через API.
Тест выполняет запрос на создание potato и проверяет, что статус ответа равен `201`.

Для выполнения теста необходимо изменить значения в [файле](src/test/resources/json/potato.json):

    { "name": "Potato" }

Данный тест должен поменять эти данные на следующие:

    { "name": "Tomato",
    "job": "Eat maket" }

После изменения тест сравнивает заданные значения с данными, полученными в ответе:

    assertEquals(jsonPotato.getString("name"), "Tomato");
    assertEquals(jsonPotato.getString("job"), "Eat maket");

При успешном прохождении теста мы получим логи запроса и никаких ошибок.
***
#### Второй раздел тестов проверяет список персонажей и их данные из мультфильма **Рик и Морти** через API.
Сценарий представлен следующим образом:

    Когда я запрашиваю список персонажей
    Если я нахожу Морти
    Когда я получаю последний эпизод Морти
    Тогда я проверяю, что последний персонаж не соответствует Морти
---

## Запуск тестов

Для выполнения тестового набора убедитесь, что у вас настроена необходимая среда(JDK 17), и выполните следующие шаги:

1. Клонируйте репозиторий.
2. Перейдите в директорию проекта.
3. Выполните команду для запуска тестов с помощью Maven.

       mvn test


## Отчеты Allure

После выполнения тестов вы можете сгенерировать отчеты Allure, чтобы просмотреть их в удобном формате:


    mvn allure:report
    mvn allure:serve


---