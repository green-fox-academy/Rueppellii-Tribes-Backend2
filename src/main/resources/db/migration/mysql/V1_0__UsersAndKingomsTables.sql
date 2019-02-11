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
