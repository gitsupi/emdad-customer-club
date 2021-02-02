INSERT IGNORE INTO role(name) VALUES('ROLE_COMPANY');
INSERT IGNORE INTO role(name) VALUES('ROLE_ADMIN_GL');


insert ignore into group_level (id, created_at, updated_at, minscore, maxscore, level_name)
values (1, '2021-01-30 19:55:48', '2021-01-30 19:55:48', 0, 30, '2آبی');
insert ignore into group_level (id, created_at, updated_at, minscore, maxscore, level_name)
values (2, '2021-01-30 19:55:48', '2021-01-30 19:55:48', 30,80, 'قرمز');
insert ignore into group_level (id, created_at, updated_at, minscore, maxscore, level_name)
values (3, '2021-01-30 19:55:48', '2021-01-30 19:55:48',80, 120, 'بنفش');
insert ignore into group_level (id, created_at, updated_at, minscore, maxscore, level_name)
values (4, '2021-01-30 19:55:48', '2021-01-30 19:55:48', 120,180, 'سبز');
insert ignore into group_level (id, created_at, updated_at, minscore, maxscore, level_name)
values (5, '2021-01-30 19:55:48', '2021-01-30 19:55:48', 180,28000000000, 'sdssd');
