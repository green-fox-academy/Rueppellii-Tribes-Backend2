DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id BIGINT auto_increment,
	username VARCHAR(255) UNIQUE,
	password VARCHAR(255),
	role VARCHAR(255),
	kingdom VARCHAR(255) UNIQUE,
	PRIMARY KEY (user_id)
);

CREATE TABLE kingdoms (
	id BIGINT auto_increment,
	name VARCHAR(255) UNIQUE,
 	application_user_user_id BIGINT,
	PRIMARY KEY (id)
);

INSERT INTO users VALUES (1, 'test1', 'pass', 'userke', 'faluvege');

INSERT INTO kingdoms (name, application_user_user_id) SELECT kingdom, user_id FROM users;
