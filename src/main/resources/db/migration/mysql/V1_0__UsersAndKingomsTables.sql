CREATE TABLE users (
	user_id BIGINT auto_increment,
	username VARCHAR(255) UNIQUE,
	password VARCHAR(255),
	kingdom VARCHAR(255) UNIQUE,
	PRIMARY KEY (user_id)
);

CREATE TABLE kingdoms (
	id BIGINT auto_increment,
	name VARCHAR(255) UNIQUE,
 	application_user_user_id BIGINT,
	PRIMARY KEY (id)
);
 ALTER TABLE kingdoms ADD CONSTRAINT fk_users_user FOREIGN KEY (application_user_user_id) REFERENCES users(user_id);
 ALTER TABLE kingdoms ADD CONSTRAINT uq_users_kingdom UNIQUE (name);


INSERT INTO users VALUES (1, 'TestUser1', 'pass1', 'UserCastle');
INSERT INTO users VALUES (2, 'TestUser2', 'pass2', 'MockVillage');

INSERT INTO kingdoms (name, application_user_user_id) SELECT kingdom, user_id FROM users;

ALTER TABLE users DROP kingdom;