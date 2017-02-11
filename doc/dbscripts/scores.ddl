CREATE TABLE USERSCORE (
  user  VARCHAR(32),
  won  INTEGER,
  lost   INTEGER,
  played INTEGER,
  scoredPoint INTEGER,
  lostPoint INTEGER,

  PRIMARY KEY (user)
);

CREATE TABLE SCORECORD (
  user  VARCHAR(32),
  won  BOOLEAN,
  scoredPoint INTEGER,
  lostPoint INTEGER,
  gameID VARCHAR(64),

  PRIMARY KEY (user, gameID)
);