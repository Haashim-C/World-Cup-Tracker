CREATE TABLE team (
                      teamId LONG PRIMARY KEY AUTO_INCREMENT,
                      teamName VARCHAR(30) NOT NULL,
                      teamContinent VARCHAR(30),
                      gamesPlayed INTEGER(30),
                      gamesWon INTEGER(30),
                      gamesDrawn INTEGER(30),
                      gamesLost INTEGER(30)
);
