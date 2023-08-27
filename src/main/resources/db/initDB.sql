DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS global_seq_meal;


CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               integer PRIMARY KEY DEFAULT nextval('global_seq'),
    name             varchar                           NOT NULL,
    email            varchar                           NOT NULL,
    password         varchar                           NOT NULL,
    registered       timestamp           DEFAULT now() NOT NULL,
    enabled          bool                DEFAULT TRUE  NOT NULL,
    calories_per_day integer             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    user_id integer NOT NULL,
    role    varchar NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE SEQUENCE global_seq_meal START WITH 1;

CREATE TABLE meals
(
    id          integer      PRIMARY KEY DEFAULT nextval('global_seq_meal') ,
    user_id     integer      REFERENCES users(id) ON DELETE CASCADE,
    date_time   timestamp    NOT NULL,
    description varchar(32)  NOT NULL,
    calories    integer      NOT NULL,
    UNIQUE (user_id, date_time)
);
CREATE UNIQUE INDEX user_meals_date_time_idx ON meals (date_time);
