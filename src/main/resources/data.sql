-- Einfügen von Benutzern
INSERT INTO users (email, version) VALUES ('example1@gmail.com', 0);
INSERT INTO users (email, version) VALUES ('example2@gmail.com', 0);
INSERT INTO users (email, version) VALUES ('example3@gmail.com', 0);

-- Einfügen von Herausforderungen
INSERT INTO challenges (title, description, unit, target_progress, deadline, score_reward, score_penalty, user_id, challenge_visibility, version)
VALUES ('Challenge 1', 'Description 1', 'SECONDS', 100.0, '2021-12-31', 10, 5, 1, 'PUBLIC', 0);
INSERT INTO challenges (title, description, unit, target_progress, deadline, score_reward, score_penalty, user_id, challenge_visibility, version)
VALUES ('Challenge 2', 'Description 2', 'MINUTES', 100.0, '2021-12-31', 10, 5, 2, 'PRIVATE', 0);
INSERT INTO challenges (title, description, unit, target_progress, deadline, score_reward, score_penalty, user_id, challenge_visibility, version)
VALUES ('Challenge 3', 'Description 3', 'HOURS', 100.0, '2021-12-31', 10, 5, 3, 'PUBLIC', 0);

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
INSERT INTO challenge_summary (challenge_count, consecutive_days, daily_challenges, done_count, longest_streak, overdue_count, pending_count, last_active, user_fk, version)
VALUES (1, 0, 0, 0, 0, 0, 1, CURRENT_TIMESTAMP, 1, 0);
INSERT INTO challenge_summary (challenge_count, consecutive_days, daily_challenges, done_count, longest_streak, overdue_count, pending_count, last_active, user_fk, version)
VALUES (1, 1, 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 2, 0);
INSERT INTO challenge_summary (challenge_count, consecutive_days, daily_challenges, done_count, longest_streak, overdue_count, pending_count, last_active, user_fk, version)
VALUES (2, 2, 2, 2, 2, 2, 2, CURRENT_TIMESTAMP, 3, 0);
