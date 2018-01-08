CREATE TABLE feed_entries
(
  title       VARCHAR(250)  NOT NULL,
  description VARCHAR(1000) NOT NULL,
  link        VARCHAR(1000) NOT NULL,
  createdDate DATETIME      NULL,
  pubDate     DATETIME      NOT NULL,
  parent_feed INT           NULL,
  PRIMARY KEY (link, pubDate)
);

CREATE INDEX feed_entries_feeds_id_fk
  ON feed_entries (parent_feed);

CREATE TABLE feeds
(
  id          INT AUTO_INCREMENT,
  link        VARCHAR(1024) NOT NULL
    PRIMARY KEY,
  title       VARCHAR(1024) NULL,
  createdDate DATETIME      NULL,
  CONSTRAINT id
  UNIQUE (id)
);

ALTER TABLE feed_entries
  ADD CONSTRAINT feed_entries_feeds_id_fk
FOREIGN KEY (parent_feed) REFERENCES feeds (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;

