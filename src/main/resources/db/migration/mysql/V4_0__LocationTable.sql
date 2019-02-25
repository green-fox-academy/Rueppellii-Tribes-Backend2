CREATE TABLE IF NOT EXISTS locations (
  id BIGINT auto_increment,
  location VARCHAR (20),
  PRIMARY KEY (id),
  FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);