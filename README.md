# Для запуска необходимо следующее ПО:
1. Docker;
1. NodeJS;
1. PowerShell;
1. IntelliJ IDEA.

# Перед запуском тестов сделать следующее:
1. Docker: 
* запустить Docker;
* убедиться, что вы в нужной папке с файлом docker-compose (чтобы перейти, ввести команду "cd .\artifacts\" в терминале IDEA);
* в терминале IDEA ввести команду "docker-compose up";
* убедиться, что СУБД запущены (в логах в терминале видим "database system is ready to accept connections" и "ready for connections").

2. gate-simulator:
* открыть новый терминал IDEA;
* убедиться, что вы в нужной папке \artifacts\gate-simulator (чтобы перейти, ввести команду "cd .\artifacts\gate-simulator\" в терминале IDEA);
* в терминале IDEA ввести команду "npm start";
* убедиться, что симулятор запущен (в логах видим "starting `node app.js`" и список карт).

3. aqa-shop.jar + postgreSQL:
* открыть новый терминал IDEA;
* убедиться, что вы в нужной папке \artifacts (чтобы перейти, ввести команду "cd .\artifacts\" в терминале IDEA);
* ввести команду "java -jar aqa-shop.jar --spring.config.location=./postgreSQL.properties";
* убедиться, что приложение запущено (в логах в терминале видим "Started ShopApplication", приложение запускается на http://localhost:8080/).

4. aqa-shop.jar + mySQL:
* открыть новый терминал IDEA;
* убедиться, что вы в нужной папке \artifacts (чтобы перейти, ввести команду "cd .\artifacts\" в терминале IDEA);
* ввести команду "java -jar aqa-shop.jar --spring.config.location=./mySQL.properties";
* убедиться, что приложение запущено (в логах в терминале видим "Started ShopApplication", приложение запускается на http://localhost:8080/).