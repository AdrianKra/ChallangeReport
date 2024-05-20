INSERT INTO users DEFAULT VALUES;
INSERT INTO users DEFAULT VALUES;
INSERT INTO users DEFAULT VALUES;

INSERT INTO challenge (name, description, start_date, end_date) VALUES ('Challenge 1', 'Description 1', '2020-01-01', '2020-01-02');
INSERT INTO challenge (name, description, start_date, end_date) VALUES ('Challenge 2', 'Description 2', '2020-01-03', '2020-01-04');
INSERT INTO challenge (name, description, start_date, end_date) VALUES ('Challenge 3', 'Description 3', '2020-01-05', '2020-01-06');
INSERT INTO challenge (name, description, start_date, end_date) VALUES ('Challenge 4', 'Description 4', '2020-01-07', '2020-01-08');

INSERT INTO challenge_report (challenge_FK, users_FK, name, start_date, created_by, description, status) VALUES (1, 1, 'Report 1', '2020-01-01', 'admin', 'Description 1', 0);
INSERT INTO challenge_report (challenge_FK, users_FK, name, start_date, created_by, description, status) VALUES (2, 2, 'Report 2', '2020-01-03', 'admin', 'Description 2', 0);