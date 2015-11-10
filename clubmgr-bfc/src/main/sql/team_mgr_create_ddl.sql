/** team_mgr drop schema */

CREATE TABLE clubs (
	club_id		serial PRIMARY KEY,
	name		varchar(40) NOT NULL,
	club_key		varchar(20) NOT NULL,
	created		timestamp
);

CREATE TABLE teams (
	team_id		serial PRIMARY KEY,
	club_id		serial,
	name		varchar(40) NOT NULL,
	team_key	varchar(20) NOT NULL,
	created		timestamp,
	CONSTRAINT FK_teams_clubs_id FOREIGN KEY (club_id)
	REFERENCES clubs (club_id) MATCH SIMPLE
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE fixtures (
	fixture_id	serial PRIMARY KEY,
	team_id		serial,
	home		VARCHAR(40) NOT NULL,
	away		VARCHAR(40) NOT NULL,
	field		VARCHAR(60) NOT NULL,
	date		date NOT NULL,
	time		time NOT NULL,
	created		timestamp,
	CONSTRAINT FK_fixtures_teams_id FOREIGN KEY (team_id)
	REFERENCES teams (team_id) MATCH SIMPLE
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE standings (
	standing_id	serial PRIMARY KEY,
	team_id		serial,
	team		VARCHAR(40) NOT NULL,
	gamesPlayed	int NOT NULL,
	wins		int NOT NULL,
	ties		int NOT NULL,
	losses		int NOT NULL,
	goalsFor	int NOT NULL,
	goalsAgainst	int NOT NULL,
	points		int NOT NULL,
	created		timestamp,
	CONSTRAINT FK_standings_teams_id FOREIGN KEY (team_id)
	REFERENCES teams (team_id) MATCH SIMPLE
	ON UPDATE CASCADE ON DELETE CASCADE
);