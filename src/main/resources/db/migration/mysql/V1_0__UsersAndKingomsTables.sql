CREATE TABLE users (
	user_id BIGINT auto_increment,
	username VARCHAR(255) UNIQUE,
	password VARCHAR(255),
	PRIMARY KEY (user_id)
);

CREATE TABLE kingdoms (
	id BIGINT auto_increment,
	name VARCHAR(255) UNIQUE,
 	application_user_user_id BIGINT,
	PRIMARY KEY (id),
	FOREIGN KEY (application_user_user_id) REFERENCES users(user_id)
);

INSERT INTO users(user_id, username, password) VALUES (1, 'TestUser1', 'password');
INSERT INTO users(user_id, username, password) VALUES (2, 'TestUser2', 'password');

INSERT INTO kingdoms(id, name, application_user_user_id) VALUES (1, 'TestVillage', 1);
INSERT INTO kingdoms(id, name, application_user_user_id) VALUES (2, 'TestKingdom', 2);