create table user_favorite_dish
(
    id         int auto_increment
        primary key,
    user_id    int                                not null,
    dish_id    int                                not null,
    created_at datetime default CURRENT_TIMESTAMP null,
    constraint user_id
        unique (user_id, dish_id),
    constraint user_favorite_dish_ibfk_1
        foreign key (user_id) references user (user_id),
    constraint user_favorite_dish_ibfk_2
        foreign key (dish_id) references dish (dish_id)
);

create index dish_id
    on user_favorite_dish (dish_id);

INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (1, 4, 15, '2025-07-01 18:13:34');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (2, 4, 14, '2025-07-01 18:15:50');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (4, 6, 16, '2025-07-01 22:12:30');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (5, 6, 15, '2025-07-01 22:12:31');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (6, 6, 12, '2025-07-01 22:12:32');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (7, 6, 9, '2025-07-01 22:12:34');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (8, 6, 8, '2025-07-01 22:12:35');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (9, 6, 7, '2025-07-01 22:12:36');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (10, 6, 17, '2025-07-01 22:18:02');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (11, 9, 17, '2025-07-01 22:18:39');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (12, 9, 14, '2025-07-01 22:18:41');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (13, 9, 13, '2025-07-01 22:18:41');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (14, 9, 10, '2025-07-01 22:18:42');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (15, 9, 9, '2025-07-01 22:18:44');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (16, 9, 8, '2025-07-01 22:18:45');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (17, 9, 6, '2025-07-01 22:21:07');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (18, 8, 18, '2025-07-01 22:24:39');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (19, 8, 14, '2025-07-01 22:24:40');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (20, 8, 12, '2025-07-01 22:24:41');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (21, 8, 8, '2025-07-01 22:24:42');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (22, 8, 7, '2025-07-01 22:24:43');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (23, 8, 9, '2025-07-01 22:24:44');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (24, 8, 10, '2025-07-01 22:24:45');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (25, 8, 3, '2025-07-01 22:24:52');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (26, 8, 5, '2025-07-01 22:24:53');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (27, 8, 6, '2025-07-01 22:24:53');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (28, 8, 4, '2025-07-01 22:24:56');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (29, 3, 18, '2025-07-01 22:27:42');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (30, 3, 15, '2025-07-01 22:27:43');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (31, 3, 14, '2025-07-01 22:27:45');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (32, 3, 13, '2025-07-01 22:27:45');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (33, 3, 10, '2025-07-01 22:27:46');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (34, 3, 11, '2025-07-01 22:27:47');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (35, 3, 2, '2025-07-01 22:27:57');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (36, 3, 3, '2025-07-01 22:27:58');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (37, 3, 16, '2025-07-01 22:28:05');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (38, 3, 12, '2025-07-01 22:28:48');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (40, 10, 15, '2025-07-01 22:32:59');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (41, 10, 17, '2025-07-01 22:33:01');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (42, 10, 16, '2025-07-01 22:33:01');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (43, 10, 13, '2025-07-01 22:33:02');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (44, 10, 14, '2025-07-01 22:33:03');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (45, 10, 11, '2025-07-01 22:33:05');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (46, 10, 12, '2025-07-01 22:33:06');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (47, 10, 8, '2025-07-01 22:33:07');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (48, 10, 9, '2025-07-01 22:33:08');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (49, 10, 2, '2025-07-01 22:33:10');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (50, 10, 3, '2025-07-01 22:33:11');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (51, 10, 1, '2025-07-01 22:33:11');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (52, 10, 10, '2025-07-01 22:38:37');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (53, 10, 7, '2025-07-01 23:00:41');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (54, 11, 18, '2025-07-01 23:20:36');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (55, 11, 17, '2025-07-01 23:20:40');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (56, 11, 16, '2025-07-01 23:20:41');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (57, 11, 13, '2025-07-01 23:20:42');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (58, 11, 14, '2025-07-01 23:20:43');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (59, 11, 12, '2025-07-01 23:20:44');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (60, 11, 11, '2025-07-01 23:20:45');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (61, 11, 10, '2025-07-01 23:20:45');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (62, 11, 8, '2025-07-01 23:20:47');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (63, 11, 9, '2025-07-01 23:20:47');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (64, 11, 3, '2025-07-01 23:20:49');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (65, 11, 2, '2025-07-01 23:20:50');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (66, 11, 5, '2025-07-01 23:20:52');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (67, 11, 6, '2025-07-01 23:20:52');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (68, 11, 4, '2025-07-01 23:20:53');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (69, 5, 5, '2025-07-01 23:55:57');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (70, 5, 15, '2025-07-02 00:06:38');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (71, 5, 17, '2025-07-02 00:50:30');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (72, 5, 11, '2025-07-02 00:50:33');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (73, 5, 8, '2025-07-02 00:50:34');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (74, 5, 7, '2025-07-02 00:50:35');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (75, 5, 10, '2025-07-02 00:50:36');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (76, 5, 9, '2025-07-02 00:50:37');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (77, 12, 18, '2025-07-02 08:30:01');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (78, 12, 17, '2025-07-02 08:30:02');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (79, 12, 14, '2025-07-02 08:30:03');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (80, 12, 15, '2025-07-02 08:30:04');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (81, 12, 12, '2025-07-02 08:30:05');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (82, 12, 11, '2025-07-02 08:30:06');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (83, 12, 8, '2025-07-02 08:30:07');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (84, 12, 2, '2025-07-02 08:30:10');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (85, 12, 3, '2025-07-02 08:30:11');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (86, 12, 5, '2025-07-02 08:30:12');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (87, 12, 6, '2025-07-02 08:30:12');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (88, 12, 13, '2025-07-02 08:30:17');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (89, 13, 17, '2025-07-02 09:37:23');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (90, 13, 18, '2025-07-02 09:37:24');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (91, 13, 13, '2025-07-02 09:37:25');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (92, 13, 14, '2025-07-02 09:37:26');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (93, 13, 15, '2025-07-02 09:37:26');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (94, 13, 11, '2025-07-02 09:37:28');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (95, 13, 10, '2025-07-02 09:37:29');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (96, 13, 8, '2025-07-02 09:37:30');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (97, 13, 7, '2025-07-02 09:37:31');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (98, 13, 3, '2025-07-02 09:37:32');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (99, 13, 2, '2025-07-02 09:37:33');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (100, 13, 5, '2025-07-02 09:37:34');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (101, 5, 14, '2025-07-03 11:26:41');
INSERT INTO mybatis.user_favorite_dish (id, user_id, dish_id, created_at) VALUES (102, 3, 8, '2025-07-03 12:07:03');
