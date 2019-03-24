-- Up
CREATE TABLE message (
  id INTEGER PRIMARY KEY,
  author TEXT,
  entry TEXT
);

INSERT INTO message (id, author, entry) VALUES (1, 'Admin', 'Welcome to the chat!');

-- Down
DROP TABLE message;
