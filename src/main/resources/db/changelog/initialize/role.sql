INSERT INTO role (r_id, r_name, r_display_name, r_is_free_access) VALUES
  (uuid_generate_v4(), 'ADMIN', 'Администратор', FALSE),
  (uuid_generate_v4(), 'STUDENT', 'Студент', FALSE),
  (uuid_generate_v4(), 'ABITURIENT', 'Абитуриент', TRUE),
  (uuid_generate_v4(), 'TEACHER', 'Преподаватель', FALSE),
  (uuid_generate_v4(), 'DEP_HEAD', 'Заведующий кафедры', FALSE),
  (uuid_generate_v4(), 'VICE-RECTOR', 'Проректор', FALSE)