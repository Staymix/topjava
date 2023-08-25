DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (user_name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES  (100000, '2023-01-01 10:23:00', 'завтрак', '500'),
        (100000, '2023-01-01 13:23:00', 'обед', '800'),
        (100000, '2023-01-01 17:23:00', 'ужин', '1000'),
        (100001, '2023-01-01 09:23:00', 'завтрак', '700'),
        (100001, '2023-01-01 12:23:00', 'обед', '900'),
        (100001, '2023-01-01 16:23:00', 'ужин', '800');

