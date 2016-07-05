/** team_mgr drop schema */


/** CLUBS */
INSERT INTO clubs (name, club_key, created)
VALUES    ('Bombastic FC', 'BOMBASTIC', CURRENT_TIMESTAMP);


/** TEAMS */
INSERT INTO teams (club_id, name, team_key, created) (
    SELECT    club_id, 'Mens A', 'BOMBASTIC_MENS_A', CURRENT_TIMESTAMP
    FROM    clubs WHERE club_key = 'BOMBASTIC'
);

INSERT INTO teams (club_id, name, team_key, created) (
    SELECT    club_id, 'Mens B', 'BOMBASTIC_MENS_B', CURRENT_TIMESTAMP
    FROM    clubs WHERE club_key = 'BOMBASTIC'
);

INSERT INTO teams (club_id, name, team_key, created) (
    SELECT    club_id, 'Mens Classics', 'BOMBASTIC_MENS_CLASSICS', CURRENT_TIMESTAMP
    FROM    clubs WHERE club_key = 'BOMBASTIC'
);

INSERT INTO teams (club_id, name, team_key, created) (
    SELECT    club_id, 'Mens Jurassic', 'BOMBASTIC_MENS_JURASSIC', CURRENT_TIMESTAMP
    FROM    clubs WHERE club_key = 'BOMBASTIC'
);

INSERT INTO teams (club_id, name, team_key, created) (
    SELECT    club_id, 'Womens Jurassic', 'BOMBASTIC_WOMENS', CURRENT_TIMESTAMP
    FROM    clubs WHERE club_key = 'BOMBASTIC'
);

INSERT INTO seasons (name, season_key, created)
VALUES ('Summer 2015', 'SUMMER_2015', CURRENT_TIMESTAMP);

INSERT INTO seasons (name, season_key, created)
VALUES ('Winter 2015/2016', 'WINTER_2015', CURRENT_TIMESTAMP);
INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 1, 'Rinos Fury A', 7, 6, 1, 0, 15, 6, 19, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 2, 'Gastown FC', 7, 4, 1, 2, 14, 12, 13, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 3, 'Vancouver Strikers', 6, 4, 1, 1, 12, 7, 13, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 4, 'HK Hrvat A', 7, 4, 0, 3, 12, 10, 12, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 5, 'Harps FC A', 7, 3, 2, 2, 13, 9, 11, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 6, 'Bombastic FC A', 7, 3, 1, 3, 11, 11, 10, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 7, 'Squamish FC', 6, 3, 1, 2, 19, 9, 10, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 8, 'Euro FC', 7, 2, 2, 3, 6, 9, 8, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 9, 'Vancouver United FC', 7, 2, 2, 3, 11, 13, 8, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 10, '13th Legion FC', 7, 1, 3, 3, 6, 10, 6, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 11, 'GFC United Lions', 7, 1, 1, 5, 8, 21, 4, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO standings (team_id, season_id, position, team, gamesPlayed, wins, ties, losses, goalsFor, goalsAgainst, points, created) (
    SELECT    team_id, season_id, 12, 'America FC', 7, 0, 1, 6, 6, 16, 1, CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO team_season_map (team_id, season_id, data_key, standings_uri, fixtures_uri, description, created) (
    SELECT    team_id, season_id, 'VMSL', 'http://vmslsoccer.com/division-2b/', 'http://vmslsoccer.com/division-2b/', 'VMSL Division 2', CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_A'
);

INSERT INTO team_season_map (team_id, season_id, data_key, standings_uri, fixtures_uri, description, created) (
    SELECT    team_id, season_id, 'VMSL', 'http://vmslsoccer.com/cat-division/', 'http://vmslsoccer.com/cat-division/', 'VMSL Division CAT', CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_B'
);

INSERT INTO team_season_map (team_id, season_id, data_key, standings_uri, fixtures_uri, description, created) (
    SELECT    team_id, season_id, 'VMSL', 'http://vmslsoccer.com/masters-division-1/', 'http://vmslsoccer.com/masters-division-1/', 'VMSL Masters Division 1', CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_CLASSICS'
);

INSERT INTO team_season_map (team_id, season_id, data_key, standings_uri, fixtures_uri, description, created) (
    SELECT    team_id, season_id, 'VMSL', 'http://vmslsoccer.com/masters-over-40s-division-1/', 'http://vmslsoccer.com/masters-over-40s-division-1/', 'VMSL Over 40s Division 1', CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_JURASSIC'
);

INSERT INTO team_season_map (team_id, season_id, data_key, standings_uri, fixtures_uri, description, created) (
    SELECT    team_id, season_id, 'MWSL', 'http://mwsl.com/webapps/spappz_live/team_page_mwsl?id=1920', 'http://mwsl.com/webapps/spappz_live/team_page_mwsl?id=1920', 'MWSL Division 1C', CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'WINTER_2015'
    WHERE    T.team_key = 'BOMBASTIC_WOMENS'
);

INSERT INTO team_season_map (team_id, season_id, data_key, standings_uri, fixtures_uri, description, created) (
    SELECT    team_id, season_id, 'CESL', 'http://maxusis.com/cesl/', 'http://maxusis.com/cesl/schedule.aspx', 'CESL Summer League', CURRENT_TIMESTAMP
    FROM    teams T
    JOIN    seasons S ON S.season_key = 'SUMMER_2015'
    WHERE    T.team_key = 'BOMBASTIC_MENS_CLASSICS'
);