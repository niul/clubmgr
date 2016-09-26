CREATE TABLE roles (
    role_id	serial PRIMARY KEY,
    role_key	varchar(20) NOT NULL,
    description	varchar(80) NOT NULL,
    created	timestamp
);

CREATE TABLE users (
    user_id	serial PRIMARY KEY,
    uuid		uuid,
    club_id	serial NOT NULL,
    username	varchar(80),
    password	varchar(100),
    first_name  varchar(40),
    last_name	varchar(40),
    email	varchar(80),
    enabled	boolean NOT NULL DEFAULT TRUE,
    created	timestamp,
    CONSTRAINT FK_users_club_id FOREIGN KEY (club_id)
    REFERENCES clubs (club_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE user_roles (
    user_role_id	serial PRIMARY KEY,
    user_id		serial,
    role_id		serial,
    created		timestamp,
    CONSTRAINT FK_user_roles_user_id FOREIGN KEY (user_id)
    REFERENCES users (user_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_user_roles_role_id FOREIGN KEY (role_id)
    REFERENCES roles (role_id) MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
);