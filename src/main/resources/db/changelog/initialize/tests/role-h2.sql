INSERT INTO role (r_id, r_name, r_display_name, r_is_free_access)
VALUES (RANDOM_UUID(), 'ADMIN', 'Администратор', FALSE),
       (RANDOM_UUID(), 'STUDENT', 'Студент', FALSE),
       (RANDOM_UUID(), 'ABITURIENT', 'Абитуриент', TRUE),
       (RANDOM_UUID(), 'TEACHER', 'Преподаватель', FALSE),
       (RANDOM_UUID(), 'DEP_HEAD', 'Заведующий кафедры', FALSE),
       (RANDOM_UUID(), 'VICE-RECTOR', 'Проректор', FALSE)