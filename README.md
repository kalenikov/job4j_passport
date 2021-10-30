[![Build Status](https://app.travis-ci.com/kalenikov/job4j_passport.svg?branch=main)](https://app.travis-ci.com/kalenikov/job4j_passport)

# job4j_passport

Создать сервис, используя Spring Boot и архитектуру REST. Для хранения можно использовать любую БД.

Сервис должен заниматься управлением паспортами. Доменной моделью будет - паспорт.

Должны поддерживаться следующие методы:

[comment]: <> (- /save, сохранить данные паспорта)

[comment]: <> (- /update?id=*, обновить данные паспорта)

[comment]: <> (- /delete?id=*, удалить данные паспорта)

[comment]: <> (- /find, загрузить все паспорта)

[comment]: <> (- /find?seria=*, загрузить паспорта с заданной серией)

[comment]: <> (- /unavaliabe, загрузить паспорта чей срок вышел)

[comment]: <> (- /find-replaceable, загрузить паспорта, которые нужно заменить в ближайшие 3 месяца)

2. Создать второй сервис, используя Spring Boot. Данный сервис должен вызывать методы первого сервиса используя RestTemplate