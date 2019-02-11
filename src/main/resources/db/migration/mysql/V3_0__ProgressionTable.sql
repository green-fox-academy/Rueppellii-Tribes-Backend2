CREATE TABLE IF NOT EXISTS progression (
  id BIGINT auto_increment,
  game_object_id BIGINT,
  object_to_progress VARCHAR(255),
  time_to_create BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS kingdom_progresses (
  kingdom_id BIGINT,
  progress_id BIGINT,
  CONSTRAINT fk_kingdoms_progresses FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id),
  CONSTRAINT fk_progression FOREIGN KEY (progress_id) REFERENCES progression(id)
);