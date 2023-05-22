# ЛЭТИ. Учебная практика

Сервис ввода, индексации и поиска txt-образов документов в базе данных

## Задание

Приложение должно реализовывать следующие функции:

- создание индекса базы данных документов (построения нового индекса всей базы документов). Lucene - индекс должен
  храниться на диске;
- индексация вновь добавленных в БД документа;
- очистка индекса от удаленных из БД версий документов;
- поиск релевантных документов по введенной строке.

## Tech Stack

**Client:** HTML, CSS, Thymeleaf

**Server:** Java, Spring Framework, Hibernate, Solr, PostgreSQL

## Docker Launch

### Build Docker Container

Pull and run docker container for Solr

```bash
  docker run -d -p 8983:8983 --name my_solr solr
```

## Run Locally

Clone the project

```bash
  https://github.com/greenblat17/etu-educational-practice.git
```

Go to the project directory

```bash
  cd etu-educational-practice
```

Start the server

```bash
  mvn spring-boot:run
```

## API Reference

### Authentication

#### Registration (web page)

```http
  GET / registration
```

#### Registration

```http
  POST /

  {
      "username": "string"
      "password": "string"
  }
```

#### Login (web page)

```http
  GET / login
```

### Index documents

#### Upload document

```http
  POST /api/v1/documents/upload
```

| Parameter  | Type        | Description                         |
|:-----------|:------------|:------------------------------------|
| `file`     | `Multipart` | **Required**. document for indexing |
| `filename` | `String`    | **Required**. document name         |

#### Update document

```http
  PUT /api/v1/documents/update
```

| Parameter  | Type        | Description                         |
|:-----------|:------------|:------------------------------------|
| `file`     | `Multipart` | **Required**. document for updating |
| `filename` | `String`    | **Required**. document name         |


#### Download document

```http
  GET /api/v1/documents/download
```

| Parameter       | Type        | Description                         |
|:----------------|:------------|:------------------------------------|
| `file`          | `Multipart` | **Required**. document for updating |
| `download date` | `String`    | **Required**. document upload date  |

#### Delete document

```http
  DELETE /api/v1/documents/delete 
```

#### Search document

```http
  GET /api/v1/documents/search
```

| Parameter       | Type     | Description                              |
|:----------------|:---------|:-----------------------------------------|
| `search-line`   | `String` | **Required**. line for searching in file |

## Authors

- [Александр Журавлев](https://github.com/greenblat17)
- [Лев Осипов](https://github.com/BabyJhon)

