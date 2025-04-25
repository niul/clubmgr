/** auth drop schema */


/** ROLES */
INSERT INTO roles (role_key, description, created)
VALUES    ('ROLE_ADMIN', 'Global Administrator', CURRENT_TIMESTAMP);
INSERT INTO roles (role_key, description, created)
VALUES    ('ROLE_CLUB_MGR', 'Club Manager', CURRENT_TIMESTAMP);
INSERT INTO roles (role_key, description, created)
VALUES    ('ROLE_TEAM_MGR', 'Team Manager', CURRENT_TIMESTAMP);

/** USERS */
INSERT INTO users (uuid, club_id, username, password, first_name, last_name, email, created)
VALUES    (uuid_generate_v4(), 3, 'admin', '$2a$10$INrvgDoDtubWtDD2.91MUe5R7igK.CPRh1WGZoieV0z2V05JQI2Va', 'Admin', 'Admin', 'bombasticfc1@gmail.com', CURRENT_TIMESTAMP);
INSERT INTO users (uuid, club_id, username, password, first_name, last_name, email, created)
VALUES    (uuid_generate_v4(), 3, 'clubmgr', '$2a$10$INrvgDoDtubWtDD2.91MUe5R7igK.CPRh1WGZoieV0z2V05JQI2Va', 'Club', 'Mgr', 'bombasticfc1@gmail.com', CURRENT_TIMESTAMP);
INSERT INTO users (uuid, club_id, username, password, first_name, last_name, email, created)
VALUES    (uuid_generate_v4(), 3, 'teammgr', '$2a$10$INrvgDoDtubWtDD2.91MUe5R7igK.CPRh1WGZoieV0z2V05JQI2Va', 'Team', 'Mgr', 'bombasticfc1@gmail.com', CURRENT_TIMESTAMP);

/** USER_ROLES */
INSERT INTO user_roles (user_id, role_id, created) (
    SELECT	user_id, role_id, CURRENT_TIMESTAMP
    FROM	users U
    JOIN    roles R ON R.role_key = 'ROLE_ADMIN'
    WHERE	U.username = 'admin'
);
INSERT INTO user_roles (user_id, role_id, created) (
    SELECT	user_id, role_id, CURRENT_TIMESTAMP
    FROM	users U
    JOIN    roles R ON R.role_key = 'ROLE_CLUB_MGR'
    WHERE	U.username = 'clubmgr'
);
INSERT INTO user_roles (user_id, role_id, created) (
    SELECT	user_id, role_id, CURRENT_TIMESTAMP
    FROM	users U
    JOIN    roles R ON R.role_key = 'ROLE_TEAM_MGR'
    WHERE	U.username = 'teammgr'
);