CREATE TABLE IF NOT EXISTS user_role (
  role_id BIGINT auto_increment,
  role_enum VARCHAR(255),
  PRIMARY KEY (role_id)
);

CREATE TABLE IF NOT EXISTS app_user_user_role (
  user_id BIGINT,
  role_id BIGINT,
  CONSTRAINT fk_app_user_role FOREIGN KEY (role_id) REFERENCES user_role(role_id),
  CONSTRAINT fk_app_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);