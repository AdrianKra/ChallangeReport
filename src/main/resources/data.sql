INSERT INTO users DEFAULT VALUES;
INSERT INTO users DEFAULT VALUES;
INSERT INTO users DEFAULT VALUES;

-- INSERT statements with completly different values
INSERT INTO challenges (deadline, score_penalty, score_reward, target_progress, version, user_id, challenge_visibility, description, title, unit) 
  VALUES ('2021-12-31', 5, 10, 100.0, 0, 1, 'PUBLIC', 'Description 1', 'Challenge 1', 'SECONDS');
INSERT INTO challenges (deadline, score_penalty, score_reward, target_progress, version, user_id, challenge_visibility, description, title, unit)
  VALUES ('2021-12-31', 5, 10, 100.0, 0, 2, 'PRIVATE', 'Description 2', 'Challenge 2', 'MINUTES');
INSERT INTO challenges (deadline, score_penalty, score_reward, target_progress, version, user_id, challenge_visibility, description, title, unit)
  VALUES ('2021-12-31', 5, 10, 100.0, 0, 3, 'PUBLIC', 'Description 3', 'Challenge 3', 'HOURS');

-- INSERT statements with different values
INSERT INTO challenge_report (status, challenge_fk, end_date, start_date, user_fk, description)
  VALUES ('OPEN', 1, '2021-12-31', '2021-12-01', 1,  'Description 1');
INSERT INTO challenge_report (status, challenge_fk, end_date, start_date, user_fk, description)
  VALUES ('OPEN', 2, '2021-12-31', '2021-12-01', 2,  'Description 2');

-- INSERT statements with different values
INSERT INTO challenge_summary (challenge_count, consecutive_days, daily_challenges, done_count, longest_streak, overdue_count, pending_count, last_active, user_fk)
  VALUES (0, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP, 1);
INSERT INTO challenge_summary (challenge_count, consecutive_days, daily_challenges, done_count, longest_streak, overdue_count, pending_count, last_active, user_fk)
  VALUES (1, 1, 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 2);
