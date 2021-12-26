## <p align="center">Пример создания автотестов для [сайта](https://www.tezis-doc.ru/)<br/>системы электронного документооборота <img src="src/test/resources/images/logos/tezis.svg" width="100" /></p>
В данном проекте представлен пример создания UI автотестов для web-версии сайта https://www.tezis-doc.ru.<br/>
Тестируемая функциональность - элементы модуля "Header главной страницы":
- Title главной страницы;
- Логотип;
- Пункты основных разделов сайта;
- Кнопка и поле поиска;
- Контактный телефон;
- Форма обратного звонка.
---
### Использованный технологический стек
<img src="src/test/resources/images/logos/java.svg" width="27" /> `Java` 
<img src="src/test/resources/images/logos/selenide.svg" width="42" /> `Selenide` 
<img src="src/test/resources/images/logos/junit.svg" width="33" /> `JUnit` - написание исходного кода;<br/>

<img src="src/test/resources/images/logos/gradle.svg" width="35" height="35" /> `Gradle` 
<img src="src/test/resources/images/logos/jenkins.svg" width="35" height="35" /> `Jenkins` - сборка и запуск автотестов на удаленном билд-сервере;<br/>

<img src="src/test/resources/images/logos/selenoid.svg" width="35" /> `Selenoid` 
<img src="src/test/resources/images/logos/docker.svg" width="45" /> `Docker` - запуск автотестов в изолированных сессиях на билд-сервере;<br/>
<img src="src/test/resources/images/logos/allure.svg" width="28" /> `Allure Report` - формирование отчетности по итогам тестирования;<br/>
<img src="src/test/resources/images/logos/allure_testops.svg" width="24.5" /> `Allure TestOps` - создание тестовой документации.


---
### Конфигурация для запуска тестов
Параметры для запуска автотестов в удаленном браузере на билд-сервере проекта [autotests.cloud](https://selenoid.autotests.cloud/) задаются в настройках job CI Jenkins в виде **Choice Parameter** и передаются в код проекта через терминал как системные переменные (в фигурных скобках) с помощью следующих команд:

```
clean
test
-Dbrowser=${BROWSER} // запускаемый браузер
-DbrowserVersion=${BROWSER_VERSION} // версия браузера
-DbrowserSize=${BROWSER_SIZE} // размер окна браузера
-DremoteDriverUrl=https://<username>:<password>@${REMOTE_DRIVER_URL}/wd/hub/ // URL сервера, на котором запускаются тесты
-DvideoStorage=https://${REMOTE_DRIVER_URL}/video/ // URL сервера с видеофайлами выполнения тестов
-Dthreads=${THREADS} // число потоков для прогона тестов
```
Другой вариант передачи параметров - из файла remote.properties, расположенного по пути `src/test/resources/config/remote.properties`. Для этого вместо установки **Choice Parameter** в настройках Jenkins создается шаг сборки **Create/Update Text File** с созданием текстового файла, содержащего конфигурационные данные:<br/><br/>
![](src/test/resources/images/screenshots/remote_properties.png)<br/><br/>
При этом в терминал передается только команда `clean test`.


В данном проекте прогон автотестов производился со следующими параметрами:<br/>
![](src/test/resources/images/screenshots/build_params.png)

---
### Интеграция с Allure Report и Allure TestOps
Результаты выполнения сборки Allure Report формируются в директории `build/allure-results`<br/><br/>
![](src/test/resources/images/screenshots/allure_task.png)<br/><br/>
Интеграция с Allure TestOps задается в настройках среды сборки<br/><br/>
![](src/test/resources/images/screenshots/allure-server.png)

---
### Настройка оповещений
Для настройки рассылок оповещений о результатах прогона тестов в мессенджеры, например, Telegram и Slack, используется приложение jar приложение из репозитория проекта [Allure notifications](https://github.com/qa-guru/allure-notifications/releases). В настройках послесборочных операций Jenkins job добавлен **Post Buil Task** со скриптом, который проверяет наличие jar-файла allure-notifications в корне репозитория проекта и, при его отсутствии, скачивает данный файл из репозитория https://github.com/qa-guru/allure-notifications/releases
```
cd ..
FILE=./allure-notifications-${ALLURE_NOTIFICATIONS_VERSION}.jar
if [ ! -f "$FILE" ]; then
   wget https://github.com/qa-guru/allure-notifications/releases/download/${ALLURE_NOTIFICATIONS_VERSION}/allure-notifications-${ALLURE_NOTIFICATIONS_VERSION}.jar
fi
```
Также в настройках сборки добавлены шаги сборки **Create/Update Text File** для создания json-файлов, содержащих идентификационные данные приложений-ботов и чатов в мессенджерах, в которые производится отправка оповещений. Для каждого мессенджера создан **Post Buil Task** со скриптом, который запускает рассылку оповещений:

```
java "-DprojectName=${JOB_NAME}" "-Denv=<enviroment>" "-DreportLink=${BUILD_URL}" "-Dcomm=<some comment>" "-Dconfig.file=./src/test/resources/notifications/<messenger name>.json" -jar ../allure-notifications-{ALLURE_NOTIFICATIONS_VERSION}.jar
```
---
### Результаты прогона тестов


![](src/test/resources/images/logos/build.svg)
### Сборка Jenkins
:arrow_right: [**Jenkins Job**](https://jenkins.autotests.cloud/job/08-parenkov-fedor-tezis-doc/) :arrow_left:

---
### Отчет в Allure Report
![](src/test/resources/images/screenshots/allure_3.png)
![](src/test/resources/images/screenshots/allure_4.png)
![](src/test/resources/images/screenshots/allure_5.png)

---
![](src/test/resources/images/screenshots/allure_1.png)
---
![](src/test/resources/images/screenshots/allure_2.png)

---
### Видео работы тестов<br/><br/>
**[NEGATIVE] Отправка заявки на обратный звонок с незаполненными полями формы**<br/><br/>
![](src/test/resources/images/attachs/blank_callback_form.gif)

---
**Переход из главной страницы в раздел Клиенты**<br/><br/>
![](src/test/resources/images/attachs/go_to_link.gif)

---
**Появление pop-up пункта Система при наведении курсора**<br/><br/>
![](src/test/resources/images/attachs/popup.gif)

---
**Выполнение поиска**<br/><br/>
![](src/test/resources/images/attachs/search.gif)

---
### Оповещения о статусе сборки в мессенджерах
<img src="src/test/resources/images/logos/slack.svg" width="80" /><br/>
![](src/test/resources/images/screenshots/slack_notice.png)
---
<img src="src/test/resources/images/logos/telegram.svg" width="100" /><br/>
![](src/test/resources/images/screenshots/telegram_notice.png)

---
### Тестовая документация, сгенерированная в Allure TestOps по итогам прогона тестов
![](src/test/resources/images/screenshots/allure_testops_1.png)<br/><br/>
![](src/test/resources/images/screenshots/allure_testops_2.png)
