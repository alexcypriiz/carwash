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
- docker version 20.10.16 и выше,
- docker-compose version 1.29.2 и выше.

Запуск осуществляется из корня проекта с помощью командной строки (Для пользователей windows возможно использовать утилиту "Git Bash").

Выполнить команды:

```mvn package``` - сборка проекта в jar файл,

```docker-compose build``` - сборка решения,

```docker-compose up``` - запуск решения.

***
### Ссылки
После успешного запуска решения можно перейти по ссылкам:
* http://127.0.0.1:8080/login - начало работы с приложением

* http://127.0.0.1:8080/swagger-ui/ - документация Swagger