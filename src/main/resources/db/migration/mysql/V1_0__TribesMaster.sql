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

INSERT INTO users VALUES (1, 'TestUser1', 'pass1', 'user', 'UserCastle');
INSERT INTO users VALUES (2, 'TestUser2', 'pass2', 'user', 'MockVillage');

INSERT INTO kingdoms (name, application_user_user_id) SELECT kingdom, user_id FROM users;
