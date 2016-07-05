/** team_mgr drop schema */

CREATE TABLE clubs (
    club_id        serial PRIMARY KEY,
    name        varchar(40) NOT NULL,
    club_key    varchar(40) NOT NULL,
    created        timestamp
);

CREATE TABLE teams (
    team_id        serial PRIMARY KEY,
    club_id        serial,
    name        varchar(40) NOT NULL,
    team_key    varchar(40) NOT NULL,
    created        timestamp,
    CONSTRAINT FK_teams_clubs_id FOREIGN KEY (club_id)
    REFERENCES clubs (club_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE seasons (
    season_id    serial PRIMARY KEY,
    name        varchar(40) NOT NULL,
    season_key    varchar(40) NOT NULL,
    created        timestamp
);

CREATE TABLE fixtures (
    fixture_id    serial PRIMARY KEY,
    team_id        serial,
    season_id    serial,
    home        VARCHAR(40) NOT NULL,
    away        VARCHAR(40) NOT NULL,
    home_score    VARCHAR(10),
    away_score    VARCHAR(10),
    field        VARCHAR(80),
    field_map_uri    VARCHAR(400),
    date        date,
    time        time,
    created        timestamp,
    CONSTRAINT FK_fixtures_team_id FOREIGN KEY (team_id)
    REFERENCES teams (team_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_fixtures_season_id FOREIGN KEY (season_id)
    REFERENCES seasons (season_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE standings (
    standing_id    serial PRIMARY KEY,
    team_id        serial,
    season_id    serial,
    position    int,
    team        VARCHAR(40) NOT NULL,
    gamesPlayed    int NOT NULL,
    wins        int NOT NULL,
    ties        int NOT NULL,
    losses        int NOT NULL,
    goalsFor    int NOT NULL,
    goalsAgainst    int NOT NULL,
    points        int NOT NULL,
    created        timestamp,
    CONSTRAINT FK_standings_teams_id FOREIGN KEY (team_id)
    REFERENCES teams (team_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_standings_season_id FOREIGN KEY (season_id)
    REFERENCES seasons (season_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE team_season_map (
    team_season_map_id    serial PRIMARY KEY,
    team_id            serial,
    season_id        serial,
    data_key        varchar(20) NOT NULL,
    fixtures_uri        varchar(80) NOT NULL,
    standings_uri        varchar(80) NOT NULL,
    description        varchar(80) NOT NULL,
    created            timestamp,
    CONSTRAINT FK_team_season_map_teams_id FOREIGN KEY (team_id)
    REFERENCES teams (team_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_team_season_map_season_id FOREIGN KEY (season_id)
    REFERENCES seasons (season_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);