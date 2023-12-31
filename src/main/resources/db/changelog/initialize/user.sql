INSERT INTO users(u_id, u_email, u_password, r_id, u_parameters, u_activated)
VALUES
(uuid_generate_v4(), 'admin@gmail.com', '$2a$10$FjQEc.BnNg413yN/oQuMK.SiMWztfc8ypILw6215GE4/vZFm1vO8O', (SELECT r_id FROM role WHERE r_name = 'ADMIN'), null, TRUE),
(uuid_generate_v4(), 'Rineyskiy_cit@vit.vstu.by', '$2a$10$iZskHUoV3TEYt5gq07y2fu0ucUN/f.tSK1/9MJbzo.9MFfYJarxgm', (SELECT r_id FROM role WHERE r_name = 'ADMIN'), null, TRUE),
(uuid_generate_v4(), 'Kazakov_isap@vit.vstu.by', '$2a$10$kZTe8IBV3RO7UXdPS4cdCuLaRIpw8IkDsPU.8BLI7OSccgJoc71IC', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"ISAP108"}', TRUE),
(uuid_generate_v4(), 'Abramovich_dz@vit.vstu.by', '$2a$10$PcFHmexfeJ3XEnVlbMX48.Rg6a22eUrskf8olFSauxulboXYZcOaK', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"DiM1"}', TRUE),
(uuid_generate_v4(), 'Stepanov_in@vit.vstu.by', '$2a$10$bERMU9sKB0L.G894zcVQhur7Unm3UH5ChfWy8IIor8yY1P0sd.ZQ.', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"InYaz270"}', TRUE),
(uuid_generate_v4(), 'Prokofeva_kd@vit.vstu.by', '$2a$10$HG5wQwNZJZmPL9WBq5e0qODOraGREwykCaIWjkUkPs6td/3hoha4m', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"FiKD223"}', TRUE),
(uuid_generate_v4(), 'Kirillov_malp@vit.vstu.by', '$2a$10$ywXiuKcJ6Y.jr6ggp4R8ku/es44Vh0PWX5ziIswgb/RslFaunIdtO', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"MALP121"}', TRUE),
(uuid_generate_v4(), 'Savitskaya_mg@vit.vstu.by', '$2a$10$FOUp1sNB1B9ZdVnuSy2hleajBkKv.mkC9cZA7sMEzFqrEBA8DtFoS', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"M243"}', TRUE),
(uuid_generate_v4(), 'Burkin_trt@vit.vstu.by', '$2a$10$COzCQX/0U4dr9yH2ywOC2uHNqKmlq0UkPAU8V7thoetfNudJvr0by', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"TRiT32"}', TRUE),
(uuid_generate_v4(), 'Olshanskiy_tiomp@vit.vstu.by', '$2a$10$sw2UU4zFZtdAlcVrAx8MT.MfcK35KbKjqefMScKz1LFwMVvTP.6vK', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"TiOMP207"}', TRUE),
(uuid_generate_v4(), 'Musatov_fkis@vit.vstu.by', '$2a$10$JYUBsmRUqNwYGinHJ/Mu3uhJCZM8XNnheSgJmtvCcq4Pa6Qed8qLe', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"FKiS190"}', TRUE),
(uuid_generate_v4(), 'Kasaeva_ec@vit.vstu.by', '$2a$10$phqeHZKYc7kntZ.mWTa7buHz7Ia8pKP6riyOLAuFqkoQ9dTpyyx3C', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"E114"}', TRUE),
(uuid_generate_v4(), 'Yasheva_etm@vit.vstu.by', '$2a$10$p9trrDYzjzTuHW41UswvBexVhrU91RlE/YbkdOoa0N6TQ0Xqn29qS', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"ETiM313"}', TRUE),
(uuid_generate_v4(), 'Luchenkova_sgd@vit.vstu.by', '$2a$10$vtnQA2vyKzXIvwMrw8g9Ouy1iDS2uZGk5C78No91jeRjx73hGn9jG', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"SozGum169"}', TRUE),
(uuid_generate_v4(), 'Dzhezhora_miit@vit.vstu.by', '$2a$10$0QIsqAsL1PNwUPVnJTZESuXlYy0Gn2VCb6.gz9Fq8VUs1/jom7HAK', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"MiIT74"}', TRUE),
(uuid_generate_v4(), 'Yasinskaya_eht@vit.vstu.by', '$2a$10$Mb6Psm1/ir6dIrECEYWIVeQTbP9sLtHPdBSCVVuLn6AZHdgwar/EK', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"EiHT312"}', TRUE),
(uuid_generate_v4(), 'Butkevich_ftm@vit.vstu.by', '$2a$10$vv/vbq/GYYTGwGn6NFfOcO1QDfXnk6ADEwoGU.wvieX.ZhS8jzcja', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"FiTM33"}', TRUE),
(uuid_generate_v4(), 'Ryiklin_ttm@vit.vstu.by', '$2a$10$kARAp7BgbbLNgXTEE0luOeXypBbjdDVtwmiJ9UIu.07vxQu5fEhny', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"TTM240"}', TRUE),
(uuid_generate_v4(), 'Bodyalo_kitoo@vit.vstu.by', '$2a$10$3Ae7OkQ.yx7RZl0vh2DkC.KqN.MciOqg9rCsQNKgxl6KzhSfYD3le', (SELECT r_id FROM role WHERE r_name = 'DEP_HEAD'), '{"tabel":"KiTOiO21"}', TRUE),

(uuid_generate_v4(), 'Kuznetsov_rtr@vit.vstu.by', '$2a$10$ZIITUt0IrXu8Fmq9xzJPteooBmz3FKtc0QKqfzszqURgy10ZFV1T2', (SELECT r_id FROM role WHERE r_name = 'VICE-RECTOR'), '{"tabel":"rector1"}', TRUE),
(uuid_generate_v4(), 'Petyul_rtr@vit.vstu.by', '$2a$10$b6X9YiB0ui3TORbdhw.YVuIARsrE6wLiSTtQw/Yyqh47FhYFh.fvO', (SELECT r_id FROM role WHERE r_name = 'VICE-RECTOR'), '{"tabel":"rector2"}', TRUE),
(uuid_generate_v4(), 'Vankevich_rtr@vit.vstu.by', '$2a$10$zqp7dOmtX7Vcn3xD/Dl/Guu8d/xaqqeUsRtyebcGK7pQGZYab63B6', (SELECT r_id FROM role WHERE r_name = 'VICE-RECTOR'), '{"tabel":"rector3"}', TRUE),
(uuid_generate_v4(), 'Zhiznevskiy_rtr@vit.vstu.by', '$2a$10$0NUKIvMsfm/mIpD6hLPxzeRTmlISAq0i7Ff9.6ymbwkGRps9CauGG', (SELECT r_id FROM role WHERE r_name = 'VICE-RECTOR'), '{"tabel":"rector4"}', TRUE),
(uuid_generate_v4(), 'Hadanyonak_rtr@vit.vstu.by', '$2a$10$jq1xWXrjMybjhtuFtwWUN.rOfSVs2V6v7T.0dER4H23Rc08FXnSHe', (SELECT r_id FROM role WHERE r_name = 'VICE-RECTOR'), '{"tabel":"rector5"}', TRUE);
