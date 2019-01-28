# Rueppellii-Tribes-Backend2
Tribes backend project, group 2. Starting date 21/01/2019

Mentor: Csadi Laszlo

Group memebers: Lili, Andras, Archi, Gabor

[Heroku Link](https://tribezzz.herokuapp.com/)

### Environment Variables

**Database connection**

First create database "tribes" in mySQL

| Key | Value |
| --- | ----- | 
|JDBC_DATABASE_URL | jdbc:mysql://localhost/tribes |
|JDBC_DATABASE_NAME | *your local mysql username* |
|JDBC_DATABASE_PASSWORD | *your local mysql password* |
|HIBERNATE_DIALECT | org.hibernate.dialect.MySQL57Dialect |

**Spring profiles**

| Key | Value |
| --- | ----- | 
|PROFILE | Production or Test or Heroku |

There are 3 Spring profiles available: 

| Profile | Purpose |
| ------- | ------- |
|Production | with Flyway set up, mySQL database |
|Test | with H2 database |
|Heroku | Flyway is disabled, PostgreSQL database |