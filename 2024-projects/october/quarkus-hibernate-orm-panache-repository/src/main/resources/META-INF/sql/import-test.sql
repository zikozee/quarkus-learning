
INSERT INTO movie(id, title, description, director, country, version)
VALUES (1, 'FirstMovie', 'MyFirstMovie', 'Me', 'Planet', 0);

INSERT INTO movie(id, title, description, director, country, version)
VALUES (2, 'SecondMovie', 'MySecondMovie', 'Me', 'Planet', 0);


-- since we have used 1 and 2 the next should start with 3
ALTER SEQUENCE hibernate_sequence RESTART WITH 3;