# Тестовй проект созданый на фраймворке spring boot с использованием:
## spring web mvc
## spring dat jpa
## spring security
## thymeleaf
## flyway
## bootstrap
## jquery

### Запуск осуществляется при помощи команды maven
- `./mvnw spring-boot:run`
- Для запуска проекта необходимо:
- установить mysql локально и создать базу данных crud
- или если установлен docker запустить файл docker-compose в корне проекта командой:
- `docker-compose up -d`
- после чего запустить проект.
#### Проект запускается по-умолчанию на порту 8080, host: localhost

Дефолтные пользователи инициализируются с помощью flyway. см. директорию /resources/db/migration/
### Пользователи:
- admin - логин: admin@mail.ru, пароль: admin
- user - логин: user@mail.ru, пароль: user


