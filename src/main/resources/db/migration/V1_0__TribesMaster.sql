CREATE TABLE users (
	id BIGINT auto_increment,
	username VARCHAR(255) UNIQUE,
	password VARCHAR(255),
	role VARCHAR(255),
	kingdom VARCHAR(255) UNIQUE,
	PRIMARY KEY (id)
	);

-- ALTER TABLE users ADD CONSTRAINT users UNIQUE (username);


CREATE TABLE kingdoms (
	id BIGINT auto_increment,
	name VARCHAR(255) UNIQUE,
	application_user_user_id VARCHAR(255),
	PRIMARY KEY (id)
	);

-- INSERT INTO users(id, username, password, kingdom)
--   VALUES
--   (1, MockUser, password, MockKingdom)
--
-- INSERT INTO kingdoms(id, name)
--   VALUES
--   (1, MockKingdom)