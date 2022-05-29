## CarWash

### Описание
Данное приложение реализует веб сервис для автомойки.

В него входят:
1. Для клиентов:
- регистрация,
- просмотр списка забронированных мест на мойке,
- бронирование записи на ближайшее время с учетом уже существуюших записей,
- заблаговременное бронирование записи,
- отслеживание времени до начала своей ближайшей очереди.

2. Для администрации с учетом возможностей клиентов:
- просмотр стоимости записей клиентов,
- добавление/редактирование/удаление представленных услуг клиентам.

***
### Запуск
Для запуска приложения Вам потребуются инструменты:
- jdk 17 (64-Bit),
- maven 3.6.3 и выше,
- PostgreSQL 10 и выше.

Запуск осуществляется из корня папки проекта с помощью командной строки(Для пользователей windows возможно использовать утилиту "Git Bash").

Выполнить команду:
```sudo mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.url={URL} --spring.datasource.username={USERNAME} --spring.datasource.password={PASSWORD}"```
, где
- ```{URL}``` - ссылка на Вашу созданную базу данных в PostgreSQL, которая выполняется локально на Вашем компьютере. По умолчанию PostgreSQL работает на порту 5432, хотя его можно изменить, если вы этого желаете.

**Примечание** - Созданная база данных должна быть пустой.
- ```{USERNAME}``` - имя пользователя.
- ```{PASSWORD}``` - пароль.

Рабочий пример:
```sudo mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.url=jdbc:postgresql://localhost:5432/carwash --spring.datasource.username=postgre --spring.datasource.password=postgre"```

***
### Ссылки
После успешного запуска проекта можно перейти по ссылкам:
* http://127.0.0.1:8080/login - начало работы с приложением

* http://127.0.0.1:8080/swagger-ui/ - документация Swagger