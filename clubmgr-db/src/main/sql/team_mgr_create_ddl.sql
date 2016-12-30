/** team_mgr drop schema */

CREATE TABLE clubs (
    club_id        serial PRIMARY KEY,
    uuid		uuid,
    name        varchar(40) NOT NULL,
    club_key    varchar(40) NOT NULL,
    domain      varchar(80) NOT NULL,
    created        timestamp
);

CREATE TABLE teams (
    team_id        serial PRIMARY KEY,
    uuid		uuid,
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
    uuid		uuid,
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
	active		boolean  NOT NULL DEFAULT TRUE,
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
    uuid		uuid,
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
    scheduled        boolean NOT NULL,
    created            timestamp,
    CONSTRAINT FK_team_season_map_teams_id FOREIGN KEY (team_id)
    REFERENCES teams (team_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_team_season_map_season_id FOREIGN KEY (season_id)
    REFERENCES seasons (season_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE user_teams (
    user_team_id	serial PRIMARY KEY,
    user_id		serial,
    team_id		serial,
    CONSTRAINT FK_user_teams_user_id FOREIGN KEY (user_id)
    REFERENCES users (user_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_user_teams_team_id FOREIGN KEY (team_id)
    REFERENCES teams (team_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE players (
	player_id	serial PRIMARY KEY,
	uuid		uuid,
	club_id		serial NOT NULL,
	first_name	varchar(40) NOT NULL,
	last_name	varchar(40) NOT NULL,
	email		varchar(80) NOT NULL,
	phone		varchar(20),
	dob			timestamp,
	address1	varchar(80),
	address2	varchar(80),
	city		varchar(40),
	state		varchar(10),
	zip		varchar(10),
	country		varchar(2),
	position_id	serial,
	enabled		boolean  NOT NULL DEFAULT TRUE,
    created            timestamp,
    CONSTRAINT FK_players_club_id FOREIGN KEY (club_id)
    REFERENCES clubs (club_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_players_position_id FOREIGN KEY (position_id)
    REFERENCES positions (position_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE positions (
    position_id		serial PRIMARY KEY,
    position_key    varchar(20) NOT NULL,
    position_desc   varchar(80) NOT NULL,
    created            timestamp
);

CREATE TABLE player_teams (
    player_team_id	serial PRIMARY KEY,
    player_id		serial,
    team_id		serial,
    position_id		serial,
    CONSTRAINT FK_player_teams_player_id FOREIGN KEY (player_id)
    REFERENCES players (player_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_player_teams_team_id FOREIGN KEY (team_id)
    REFERENCES teams (team_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_player_teams_team_id FOREIGN KEY (position_id)
    REFERENCES positions (position_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE player_team_season_map (
	player_team_season_map_id	serial PRIMARY KEY,
	player_id			serial,
	team_season_map_id	serial,
	CONSTRAINT FK_player_team_season_map_player_id FOREIGN KEY (player_id)
    REFERENCES players (player_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT FK_player_team_season_map_team_season_map_id FOREIGN KEY (team_season_map_id)
    REFERENCES team_season_map (team_season_map_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE player_fixture_stats (
	player_fixture_stat_id	serial PRIMARY KEY,
	player_id	serial NOT NULL,
	fixture_id	serial NOT NULL,
	started		boolean  NOT NULL DEFAULT FALSE,
	substitute	boolean  NOT NULL DEFAULT FALSE,
	assists		int,
	goals		int,
	yellow_card	boolean  NOT NULL DEFAULT FALSE,
	red_card	boolean  NOT NULL DEFAULT FALSE,
	rating		int,
	CONSTRAINT FK_player_fixture_stats_player_id FOREIGN KEY (player_id)
    REFERENCES players (player_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT FK_player_fixture_stats_fixture_id FOREIGN KEY (fixture_id)
    REFERENCES fixtures (fixture_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);