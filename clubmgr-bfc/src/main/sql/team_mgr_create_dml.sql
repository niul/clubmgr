/** team_mgr drop schema */


/** CLUBS */
INSERT INTO clubs (name, club_key, created)
VALUES	('Bombastic FC', 'BOMBASTIC', CURRENT_TIMESTAMP);


/** TEAMS */
INSERT INTO teams (club_id, name, team_key, created) (
	SELECT	club_id, 'Mens A', 'MENS_A', CURRENT_TIMESTAMP
	FROM	clubs WHERE club_key = 'BOMBASTIC'
);

INSERT INTO teams (club_id, name, team_key, created) (
	SELECT	club_id, 'Mens B', 'MENS_B', CURRENT_TIMESTAMP
	FROM	clubs WHERE club_key = 'BOMBASTIC'
);

INSERT INTO teams (club_id, name, team_key, created) (
	SELECT	club_id, 'Mens Classics', 'MENS_CLASSICS', CURRENT_TIMESTAMP
	FROM	clubs WHERE club_key = 'BOMBASTIC'
);

INSERT INTO teams (club_id, name, team_key, created) (
	SELECT	club_id, 'Mens Jurassic', 'MENS_JURASSIC', CURRENT_TIMESTAMP
	FROM	clubs WHERE club_key = 'BOMBASTIC'
);

INSERT INTO teams (club_id, name, team_key, created) (
	SELECT	club_id, 'Womens Jurassic', 'WOMENS_A', CURRENT_TIMESTAMP
	FROM	clubs WHERE club_key = 'BOMBASTIC'
);