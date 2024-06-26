INSERT INTO users (name, email, version) VALUES ('User 1', 'user1@example.org', 0);
INSERT INTO users (name, email, version) VALUES ('User 2', 'user2@example.org', 0);
INSERT INTO users (name, email, version) VALUES ('User 3', 'user3@example.org', 0);
INSERT INTO users (name, email, version) VALUES ('User 4', 'user4@example.org', 0); 

-- Einfügen von Herausforderungen
INSERT INTO challenges (title, description, unit, target_progress, deadline, score_reward, score_penalty, user_id, challenge_visibility, version)
VALUES ('Challenge 1', 'Description 1', 'SECONDS', 100.0, '2021-12-31', 10, 5, 1, 'PUBLIC', 0);
INSERT INTO challenges (title, description, unit, target_progress, deadline, score_reward, score_penalty, user_id, challenge_visibility, version)
VALUES ('Challenge 2', 'Description 2', 'MINUTES', 100.0, '2021-12-31', 10, 5, 2, 'PRIVATE', 0);
INSERT INTO challenges (title, description, unit, target_progress, deadline, score_reward, score_penalty, user_id, challenge_visibility, version)
VALUES ('Challenge 3', 'Description 3', 'HOURS', 100.0, '2021-12-31', 10, 5, 3, 'PUBLIC', 0);
INSERT INTO challenges (title, description, unit, target_progress, deadline, score_reward, score_penalty, user_id, challenge_visibility, version)
VALUES ('Challenge 4', 'Description 4', 'DAYS', 100.0, '2021-12-31', 10, 5, 4, 'PRIVATE', 0);

-- Einfügen von Challenge Progress
INSERT INTO challenge_progress (user_fk, challenge_fk, current_progress, progress_visibility, version)
VALUES (1, 1, 50.0, 'PUBLIC', 0);
INSERT INTO challenge_progress (user_fk, challenge_fk, current_progress, progress_visibility, version)
VALUES (2, 2, 75.0, 'PRIVATE', 0);
INSERT INTO challenge_progress (user_fk, challenge_fk, current_progress, progress_visibility, version)
VALUES (3, 3, 100.0, 'PUBLIC', 0);

-- Einfügen von Challenge Reports
INSERT INTO challenge_report (status, challenge_fk, start_date, end_date, user_fk, version)
VALUES ('OPEN', 1, '2021-12-01', null, 1, 0);
INSERT INTO challenge_report (status, challenge_fk, start_date, end_date, user_fk, version)
VALUES ('DONE', 2, '2021-12-01', null, 2, 0);
INSERT INTO challenge_report (status, challenge_fk, start_date, end_date, user_fk, version)
VALUES ('OVERDUE', 3, '2021-12-01', null, 3, 0);

-- Einfügen von Challenge Summary
INSERT INTO challenge_summary (challenge_count, consecutive_days, done_count, longest_streak, overdue_count, pending_count, version, last_active, user_fk)
VALUES (1, 0, 0,  0, 0, 1, 0, CURRENT_TIMESTAMP, 1);
INSERT INTO challenge_summary (challenge_count, consecutive_days, done_count, longest_streak, overdue_count, pending_count, version, last_active, user_fk)
VALUES (1, 1, 1, 1, 1, 1, 0, CURRENT_TIMESTAMP, 2);
INSERT INTO challenge_summary (challenge_count, consecutive_days, done_count, longest_streak, overdue_count, pending_count, version, last_active, user_fk)
VALUES (2, 2, 2, 2, 2, 2, 0, CURRENT_TIMESTAMP, 3);
