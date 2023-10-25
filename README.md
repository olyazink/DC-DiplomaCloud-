
## Дипломная работа "Облачное хранилище"


## Описание

Проект создан на основе Spring framework как REST api и соединен с готовой частью Front.

Веб-интерфейс позволяет:
1. Загружать произвольные файлы (размером до 12 МБайт (значение можно изменить в application.properties));
2. Менять имена файлов на случайные имена состоящие из трех цифр;
3. Скачивать файлы;
4. Удалять файлы;
5. Выйти из облачного хранилища на страницу авторизации.

## Основные моменты

Все запросы к сервису авторизованы. Для работы с файлами пользователь должен залогиниться, чтобы получить идентификационный токен. Этот токен будет передаваться в заголовках во всех последующих запросах.

Информация о файлах, пользователях, а также токенах авторизованных пользователей хранится в БД. Для работы с БД используется СУБД PostgreSQL.

Изначально FRONT доступен на порту 8081, BACKEND - на порту 8088.

Настройки приложения хранятся в файле application.properties.

В базе пользователей для теста приложения добавлено два пользователя со всеми правами (без разделения ролей):  
username1: user   password: pass
username2: user2  password: pass

Код покрыт unit-тестами, добавлены интеграционные тесты с использованием testcontainers.