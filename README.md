# Rueppellii-Tribes-Backend2
Tribes backend project, group 2. Starting date 21/01/2019

Mentor: Csadi Laszlo

Group memebers: Lili, Andras, Archi, Gabor

[Heroku Link](https://tribezzz.herokuapp.com/)

### Environment Variables

**Database connection**

First create database "tribes" in mySQL (tribes2 if you want to run it in Development environment)

| Key | Value |
| --- | ----- | 
|DATASOURCE_URL | jdbc:mysql://localhost/tribes |
|DATASOURCE_USERNAME | *your local mysql username* |
|DATASOURCE_PASSWORD | *your local mysql password* |
|HIBERNATE_DIALECT | org.hibernate.dialect.MySQL57Dialect |
|DEVDATABASE | jdbc:mysql://localhost/tribes2 |
|SIGNING_KEY | secret |

**Spring profiles**

| Key | Value |
| --- | ----- | 
|PROFILE | Production or Test or Heroku or Development |

There are 4 Spring profiles available: 

| Profile | Purpose |
| ------- | ------- |
|Production | with Flyway set up, mySQL database |
|Test | with H2 database |
|Heroku | Flyway is disabled, PostgreSQL database |
|Development | Flyway is disabled, mySQL database |