INSERT INTO users(u_id, u_email, u_password, r_id, u_parameters, u_activated)
VALUES
(RANDOM_UUID(), 'admin@gmail.com', '$2a$10$6au6bFJcgb1dBMjnZbwhquCetHRpdbdMSA6xTqOvgMG9jh5bUQDnq', (SELECT r_id FROM role WHERE r_name = 'ADMIN'), null, TRUE),
(RANDOM_UUID(), 'dep_head@vstu.by', '$2a$10$wiWyLDLXMpunfpR4Zi/xLOsvF8VwRxtWzMSSkUipmOQozdeDah12y', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"depHead1"}', TRUE),
(RANDOM_UUID(), 'vice-rector@vstu.by', '$2a$10$NPJxxlQRN2EqBXlCNl53lO0MmoXQD98jOlHshn1Ffw9u5f2iabK8K', (SELECT r_id FROM role WHERE r_name = 'VICE-RECTOR'), '{"tabel":"rector1"}', TRUE);