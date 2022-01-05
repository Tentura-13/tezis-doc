## Автоматизация UI тестов на примере [сайта](https://www.tezis-doc.ru/) системы электронного документооборота <img src="./images/logos/tezis.svg" width="100" />
В данном проекте представлен пример UI автотестов для web-версии сайта https://www.tezis-doc.ru.<br/>
Тестируемая функциональность - элементы модуля **Header главной страницы**:
- Title главной страницы;
- Логотип;
- Пункты основных разделов сайта;
- Кнопка и поле поиска;
- Контактный телефон;
- Форма обратного звонка.
---
### Использованный технологический стек
<img src="./images/logos/java.svg" width="27" /> `Java` 
<img src="./images/logos/selenide.svg" width="42" /> `Selenide` 
<img src="./images/logos/junit.svg" width="33" /> `JUnit` - написание исходного кода;<br/>
<img src="./images/logos/gradle.svg" width="35" height="35" /> `Gradle` - сборка проекта;<br/>
<img src="./images/logos/jenkins.svg" width="35" height="35" /> `Jenkins` - конфигурация и запуск сборок;<br/>
<img src="./images/logos/selenoid.svg" width="35" /> `Selenoid` 
<img src="./images/logos/docker.svg" width="45" /> `Docker` - запуск автотестов в изолированных сессиях на билд-сервере;<br/>
<img src="./images/logos/allure.svg" width="28" /> `Allure Report` - формирование отчетности по итогам тестирования;<br/>
<img src="./images/logos/allure_testops.svg" width="24.5" /> `Allure TestOps` - создание тестовой документации;<br/>
<img src="./images/logos/jira.svg" width="30" /> `Jira` - создание задачи в таск-трекере.


---
### Конфигурация для запуска тестов
Для запуска автотестов сконфигурирована :arrow_right: **[job](https://jenkins.autotests.cloud/job/08-WakeUpTheo-Tezis/)** :arrow_left: в CI Jenkins. В Jenkins job добавлена интеграция с Allure Report и Allure TestOps, а также настроена расслылка оповещений о результатах выполнения тестов в мессенджеры Slack и Telegram.<br/>
Параметры для запуска автотестов в удаленном браузере на билд-сервере проекта [autotests.cloud](https://selenoid.autotests.cloud/) задаются в виде **Choice Parameter** в настройках job и передаются в код проекта через терминал как системные переменные с помощью следующих команд:

```
clean
test
-Dbrowser=${BROWSER} // запускаемый браузер
-DbrowserVersion=${BROWSER_VERSION} // версия браузера
-DbrowserSize=${BROWSER_SIZE} // размер окна браузера
-DremoteDriverUrl=https://<login>:<password>@${REMOTE_DRIVER_URL}/wd/hub/ // URL сервера, на котором запускаются тесты
-DvideoStorage=https://${REMOTE_DRIVER_URL}/video/ // URL сервера с видеофайлами выполнения тестов
-Dthreads=${THREADS} // число потоков для прогона тестов
```
Другой вариант передачи параметров - из файла remote.properties, расположенного по пути `src/test/resources/config/remote.properties`. Для этого вместо установки **Choice Parameter** в настройках Jenkins создается шаг сборки **Create/Update Text File** с созданием текстового файла, содержащего конфигурационные данные:<br/><br/>
![](./images/screenshots/remote_properties.png)<br/><br/>
При этом в терминал передается только команда `clean test`.


Запуск автотестов производился со следующими параметрами:<br/><br/>
![](./images/screenshots/build_parameters.png)

---
### Интеграция с [Allure Report](https://docs.qameta.io/allure-report/) и [Allure TestOps](https://docs.qameta.io/allure-testops/)
Результаты выполнения сборки для Allure Report формируются в директории `build/allure-results`<br/><br/>
![](./images/screenshots/allure_task.png)<br/><br/>
Интеграция с Allure TestOps задается в настройках среды сборки<br/><br/>
![](./images/screenshots/allure-server.png)

---
### Результаты выполнения тестов


### Отчет в Allure Report
![](./images/screenshots/allure_3.png)
![](./images/screenshots/allure_4.png)
![](./images/screenshots/allure_5.png)

---
![](./images/screenshots/allure_1.png)
---
![](./images/screenshots/allure_2.png)

---
### Видео работы тестов<br/><br/>
**[NEGATIVE] Отправка заявки на обратный звонок с незаполненными полями формы**<br/><br/>
![](./images/attachs/blank_callback_form.gif)

---
**Переход из главной страницы в раздел Клиенты**<br/><br/>
![](./images/attachs/go_to_link.gif)

---
**Появление pop-up пункта Система при наведении курсора**<br/><br/>
![](./images/attachs/popup.gif)

---
**Выполнение поиска**<br/><br/>
![](./images/attachs/search.gif)

---
### Оповещения о статусе сборки в мессенджерах
<img src="./images/logos/slack.svg" width="80" /><br/>
![](./images/screenshots/slack_notice.png)
---
<img src="./images/logos/telegram.svg" width="100" /><br/>
![](./images/screenshots/telegram_notice.png)

---
### Тестовая документация, сгенерированная в [Allure TestOps](https://allure.autotests.cloud/project/670/dashboards) по итогам прохождения тестов
![](./images/screenshots/allure_testops_1.png)<br/><br/>
![](./images/screenshots/allure_testops_2.png)

### [Задача](https://jira.autotests.cloud/browse/HOMEWORK-293) в таск-трекере Jira с данными, экспортированными из Allure TestOps
![](./images/screenshots/jira.png)
