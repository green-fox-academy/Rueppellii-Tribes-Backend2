CREATE TABLE IF NOT EXISTS locations (
  country_code VARCHAR (3),
  kingdom_id BIGINT,
  PRIMARY KEY (country_code),
  FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);