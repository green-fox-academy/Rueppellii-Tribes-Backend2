# Rueppellii-Tribes-Backend2
Tribes backend project, group 2. Starting date 21/01/2019

Mentor: Csadi Laszlo

Group memebers: Lili, Andras, Archi, Gabor

[Heroku Link](https://tribezzz.herokuapp.com/)

Environment Variables:

spring.profiles.active=${PROFILE} (<- should be Production for now)
spring.datasource.url=${JDBC_DATABASE_URL} (<- the name of your local database)
spring.datasource.username=${JDBC_DATABASE_NAME} (<- your mySQL username)
spring.datasource.password=${JDBC_DATABASE_PASSWORD} (<- your mySQL password)
spring.jpa.properties.hibernate.dialect=${HIBERNATE_DIALECT} (<- should be mySQL because of Flyway)