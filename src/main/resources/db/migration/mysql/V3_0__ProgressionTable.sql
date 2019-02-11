CREATE TABLE IF NOT EXISTS progression (
  id BIGINT auto_increment,
  type VARCHAR(255),
  game_object_id BIGINT,
  time_to_progress BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS kingdom_progresses (
  progress_kingdom_id BIGINT,
  id BIGINT,
  CONSTRAINT fk_kingdoms_progresses FOREIGN KEY (progress_kingdom_id) REFERENCES kingdoms(id),
  CONSTRAINT fk_progression FOREIGN KEY (id) REFERENCES progression(id)
);