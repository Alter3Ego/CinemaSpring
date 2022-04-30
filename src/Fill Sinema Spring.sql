
INSERT INTO cinema_spring.roles (id, name)
VALUES (1, 'ROLE_USER');

INSERT INTO cinema_spring.roles (id, name)
VALUES (2, 'ROLE_ADMIN');


INSERT INTO cinema_spring.users (first_name, last_name, email, password, balance)
VALUES ('Oleksandr', 'Omelchenko', 'alexmir98@ukr.net', '$2a$10$/H9GKTTQREdZgO1xYIs9i.GqoFcvei9xgY0yTuPOcYXxHgezekNDi', 0); #444qwertY

INSERT INTO cinema_spring.users (first_name, last_name, email, password, balance)
VALUES ('Admin', 'Test', 'admin@gmail.com', '$2a$10$6loPGfVdsmRAChcTl.5n9Oh39/v.OGzUeHyVOlLfWN4XLqsVjX//C', 0); #123qwertY

INSERT INTO cinema_spring.users (first_name, last_name, email, password, balance)
VALUES ('Alex', 'Meison', 'meison@gmail.com', '$2a$10$6loPGfVdsmRAChcTl.5n9Oh39/v.OGzUeHyVOlLfWN4XLqsVjX//C', 0); #1234qwertY

INSERT INTO cinema_spring.users (first_name, last_name, email, password, balance)
VALUES ('Іван', 'Іванов', 'ivan@gmail.com', '$2a$10$/1yez5gedxLJGNAx5R.SmeKQl5DK4LMZ6BB4cm7SxN5lGsFvt26cy', 0);  #Ivanov12

INSERT INTO cinema_spring.users (first_name, last_name, email, password, balance)
VALUES ('User', 'User', 'user@gmail.com', '$2a$10$doOVFcyXgz.goRdTDlOVlec4D3OecDAbtjhr5/1sD9bFOAayrYqPq', 0);  #User1234

INSERT INTO cinema_spring.users (first_name, last_name, email, password, balance)
VALUES ('Петро', 'Черешня', 'cheresh@ukr.net', '$2a$10$/H9GKTTQREdZgO1xYIs9i.GqoFcvei9xgY0yTuPOcYXxHgezekNDi', 0);  #444qwertY

INSERT INTO cinema_spring.users (first_name, last_name, email, password, balance)
VALUES ('Мікеланджело', 'Буонарроті', 'Miko98@ukr.net', '$2a$10$/H9GKTTQREdZgO1xYIs9i.GqoFcvei9xgY0yTuPOcYXxHgezekNDi', 0);  #444qwertY

INSERT INTO cinema_spring.users_roles (user_id, roles_id)
VALUES (1, 2);

INSERT INTO cinema_spring.users_roles (user_id, roles_id)
VALUES (2, 2);

INSERT INTO cinema_spring.users_roles (user_id, roles_id)
VALUES (3, 1);

INSERT INTO cinema_spring.users_roles (user_id, roles_id)
VALUES (4, 1);

INSERT INTO cinema_spring.users_roles (user_id, roles_id)
VALUES (5, 1);

INSERT INTO cinema_spring.users_roles (user_id, roles_id)
VALUES (6, 1);

INSERT INTO cinema_spring.users_roles (user_id, roles_id)
VALUES (7, 1);


INSERT INTO cinema_spring.films  (title,  release_year, price, genre, producer)
VALUES ('Das Boot', '1981', '300', 'Drama', 'Wolfgang Petersen');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('Braveheart','1995', '250', 'Drama', 'Mel Gibson');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('Coma','2019', '350', 'Action', 'Nikita Argunov');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('Mad Max: Fury Road','2015', '400', 'Action', 'George Miller');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('Deadpool','2016', '300', 'Action', 'Tim Miller');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('Avengers: Age of Ultron','2015', '350', 'Action', 'Joss Whedon');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('Jaws', 1975, '200', 'Thriller', 'Steven Spielberg');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('It', 2017, '300', 'Horror', 'Andy Muschietti');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('The Pyramid', 2014, '250', 'Thriller', 'Gregory Levasseur');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('The Witch', 2015, '350', 'Horror', 'Robert Eggers');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('Men in Black', 1997, '200', 'Comedy', 'Barry Sonnenfeld');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('Pixels', 2015, '300', 'Comedy', 'Chris Columbus');

INSERT INTO cinema_spring.films (title,  release_year, price, genre, producer)
VALUES ('The Wolf of Wall Street', 2013, '350', 'Comedy', 'Martin Scorsese');

#Sessions

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-14 09:00:00', DEFAULT, 1);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-14 11:00:00', 3,  2);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-14 14:00:00', 5,  3);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-14 17:00:00', DEFAULT,  4);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-14 20:00:00', DEFAULT,  5);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-15 08:00:00', DEFAULT,  1);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-15 12:00:00', DEFAULT,  3);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-15 15:00:00', DEFAULT,  4);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-15 16:00:00', DEFAULT,  9);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-15 21:00:00', DEFAULT,  4);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-16 08:00:00', DEFAULT,  8);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-16 12:00:00', DEFAULT, 3);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-16 15:00:00', DEFAULT, 7);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-06-17 16:00:00', DEFAULT, 9);

INSERT INTO cinema_spring.sessions (date_time, number_of_tickets,  film_id)
VALUES ('2022-07-18 21:00:00', DEFAULT, 10);

INSERT INTO cinema_spring.tickets (user_id, session_id, place)
VALUES (1, 3, 1);

INSERT INTO cinema_spring.tickets (user_id, session_id, place)
VALUES (2, 3, 3);

INSERT INTO cinema_spring.tickets (user_id, session_id, place)
VALUES (3, 3, 4);

INSERT INTO cinema_spring.tickets (user_id, session_id, place)
VALUES (4, 3, 7);

INSERT INTO cinema_spring.tickets (user_id, session_id, place)
VALUES (5, 3, 8);

INSERT INTO cinema_spring.tickets (user_id, session_id, place)
VALUES (1, 2, 3);

INSERT INTO cinema_spring.tickets (user_id, session_id, place)
VALUES (1, 2, 5);

INSERT INTO cinema_spring.tickets (user_id, session_id, place)
VALUES (2, 2, 8);


