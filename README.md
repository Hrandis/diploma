# Для запуска необходимо следующее ПО:
1. Docker;
1. NodeJS;
1. PowerShell;
1. IntelliJ IDEA.

# Перед запуском тестов сделать следующее:
1. для соединения с БД запустить Docker и СУБД: 
* запустить Docker;
* убедиться, что вы в нужной папке с файлом docker-compose (чтобы перейти, ввести команду "cd .\artifacts\" в терминале IDEA);
* в терминале IDEA ввести команду "docker-compose up";
* убедиться, что СУБД запущены (в логах в терминале видим "database system is ready to accept connections" и "ready for connections").

2. запустить симулятор платежной системы gate-simulator:
* открыть новый терминал IDEA;
* убедиться, что вы в нужной папке \artifacts\gate-simulator (чтобы перейти, ввести команду "cd .\artifacts\gate-simulator\" в терминале IDEA);
* в терминале IDEA ввести команду "npm start";
* убедиться, что симулятор запущен (в логах видим "starting `node app.js`" и список карт).

3. запустить приложение aqa-shop.jar:
* открыть новый терминал IDEA;
* убедиться, что вы в нужной папке \artifacts (чтобы перейти, ввести команду "cd .\artifacts\" в терминале IDEA);
* ввести команду java -jar aqa-shop.jar -Dspring.datasource.url="______" -Dspring.datasource.username="hrandis" -Dspring.datasource.password="hrandispass";
на место пропуска:
для подключения к PostgreSQL jdbc:postgresql://localhost:5432/pdb
для подключения к MySQL jdbc:mysql://localhost:3306/mdb
* убедиться, что приложение запущено (в логах в терминале видим "Started ShopApplication", приложение запускается на http://localhost:8080/).

# Для запуска тестов:
* все тесты можно запустить командой из корневой папки ./gradlew clean test -Djdbc="________" -Dlogin="hrandis" -Dpassword="hrandispass"
на место пропуска:
для подключения к PostgreSQL jdbc:postgresql://localhost:5432/pdb
для подключения к MySQL jdbc:mysql://localhost:3306/mdb
* отдельные тесты и классы можно запускать, нажав на зеленую стрелку в самом классе или на верхней панели IDEA (без передачи флагов методы @AfterAll не выполнятся);
* также в дереве проекта можно вызвать контекстное меню пакета и выбрать пункт Run;
* между тестами рекомендуется выполнять команду  "./gradlew clean".