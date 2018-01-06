CREATE TABLE anyrss.feed_entries (
  title       VARCHAR(250)  NOT NULL,
  description VARCHAR(1000) NOT NULL,
  link        VARCHAR(1000) NOT NULL PRIMARY KEY,
  createdDate DATETIME
);

CREATE TABLE users
(
  user_id VARCHAR(20),
  pass    VARCHAR(20)
);

CREATE TABLE feeds
(
  link        VARCHAR(2048) NOT NULL PRIMARY KEY,
  title       VARCHAR(1024),
  createdDate DATETIME
);

CREATE TABLE user_feed
(
  id      INT PRIMARY KEY AUTO_INCREMENT,
  user_id VARCHAR(20),
  feed_id INT
);