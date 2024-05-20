INSERT INTO users DEFAULT VALUES;
INSERT INTO users DEFAULT VALUES;
INSERT INTO users DEFAULT VALUES;

INSERT INTO challenges (deadline, score_penalty, score_reward, target_progress, version, user_id, challenge_visibility, description, title, unit) 
  VALUES ('2021-12-31', 5, 10, 100.0, 0, 1, 'PUBLIC', 'Description 1', 'Challenge 1', 'SECONDS');
INSERT INTO challenges (deadline, score_penalty, score_reward, target_progress, version, user_id, challenge_visibility, description, title, unit)
  VALUES ('2021-12-31', 7, 12, 120.0, 0, 2, 'PUBLIC', 'Description 2', 'Challenge 2', 'SECONDS');
INSERT INTO challenges (deadline, score_penalty, score_reward, target_progress, version, user_id, challenge_visibility, description, title, unit)
  VALUES ('2021-12-31', 10, 15, 150.0, 1, 3, 'PUBLIC', 'Description 3', 'Challenge 3', 'SECONDS');

INSERT INTO challenge_report (status, challenge_fk, end_date, start_date, users_fk, created_by, description, name)
VALUES ('OPEN', 1, '2021-12-31', '2021-12-01', 1, 'A', 'Description 1', 'Report 1');
INSERT INTO challenge_report (status, challenge_fk, end_date, start_date, users_fk, created_by, description, name)
VALUES ('DONE', 2, '2021-12-31', '2021-12-01', 2, 'B', 'Description 2', 'Report 2');