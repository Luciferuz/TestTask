# Test Task project

## Приложение-помощник библиотекаря.
В системе существуют две основные сущности: читатель и книга.
Нужно реализовать:
- Rest-сервис для создания, редактирования, получения информации о читателях
- Rest-сервис для создания, редактирования, получения информации о книгах
- Rest-сервис для сохранения событий о том, что читатель взял книгу домой/ отдал ее назад
- Поиск самой популярной книги за определенный период времени
- Поиск читателя, прочитавшего самое большое количество книг за определенный период времени
- Интеграционные тесты
- Инструкция по запуску приложения

Пользовательский интерфейс не нужен, достаточно только бэкенд-сервисов.

## Инфо
- Java
- Postgres
- Test containers
- Git-flow

## Docker

```docker-compose up -d```

### Примеры запросов в Postman

**POST запрос для создания книги**

```localhost:8080/books```

body: ```
{
"title": "Чистый код",
"author": "григорий",
"totalCopies": 12,
"genre": "Comedy"
}```

**DELETE запрос для удаления книги** 

```localhost:8080/books/1```

**GET запрос для получения информации о книге** 

```localhost:8080/books/2```

**PUT запрос для обновления информации о книге** 

```localhost:8080/books/2```

body: ```
{
"title": "Чистый код",
"author": "Григорий",
"totalCopies": 12121212,
"genre": "Comedy"
}```

**POST запрос для создания читателя**

```localhost:8080/readers```

body: ```
{
"address": "Боровая",
"email": "amail@ya.ru",
"name": "Артем",
"phone": "+79130121211"
}```

**DELETE запрос для удаления читателя** 

```localhost:8080/readers/1```

**GET запрос для получения инфы о читателе** 

```localhost:8080/readers/2```

**PUT запрос для обновления информации о читателе** 

```localhost:8080/readers/2```

body: ```
{
"address": "Боровая 111",
"email": "email@ya.ru",
"name": "Анастасия",
"phone": "+79130121211"
}```

**POST запрос для создания event - возврат книги или взятие**

```localhost:8080/events```

body: ```
{
"event": "TAKE",
"book": {
"id": 3
},
"reader": {
"id": 1
},
"localDateTime": "2023-08-01T12:34:56"
}```

**GET запрос - самая популярная книга в библиотеке (которую больше всего брали) за определенное время** 

```localhost:8080/statistics/popular/book?from=2023-01-01T12:34:56&to=2024-01-11T12:30:56```

**SQL запрос - самая частовстречаемая книга в журнале событий (без привязки к типу операции)** 

```
SELECT b.*
FROM book b
JOIN event e ON b.id = e.book_id
WHERE e.local_date_time BETWEEN CAST(:from AS timestamp) AND CAST(:to AS timestamp)
GROUP BY b.id
ORDER BY COUNT(e.id) DESC
LIMIT 1;
```

![img.png](img.png)

![img_1.png](img_1.png)

**GET запрос - нахождение человека, кто чаще всего брал книги в библиотеке за определенное время**

```localhost:8080/statistics/popular/reader?from=2023-01-01T12:34:56&to=2024-01-11T12:30:56```

![img_4.png](img_4.png)

