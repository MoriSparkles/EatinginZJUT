create table user_like_comment
(
    id         int auto_increment
        primary key,
    user_id    int                                not null,
    comment_id int                                not null,
    created_at datetime default CURRENT_TIMESTAMP null,
    constraint user_id
        unique (user_id, comment_id),
    constraint user_like_comment_ibfk_1
        foreign key (user_id) references user (user_id),
    constraint user_like_comment_ibfk_2
        foreign key (comment_id) references comment (comment_id)
            on delete cascade
);

INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (1, 4, 24, '2025-07-01 18:13:36');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (3, 4, 23, '2025-07-01 18:13:39');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (4, 4, 19, '2025-07-01 18:15:53');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (7, 6, 25, '2025-07-01 22:13:10');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (8, 6, 27, '2025-07-01 22:14:30');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (9, 9, 28, '2025-07-01 22:20:12');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (10, 9, 20, '2025-07-01 22:20:17');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (12, 9, 12, '2025-07-01 22:20:22');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (13, 9, 29, '2025-07-01 22:21:03');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (14, 9, 15, '2025-07-01 22:21:05');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (15, 8, 30, '2025-07-01 22:25:17');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (16, 8, 35, '2025-07-01 22:27:12');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (17, 8, 34, '2025-07-01 22:27:13');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (18, 8, 33, '2025-07-01 22:27:13');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (19, 8, 32, '2025-07-01 22:27:14');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (20, 8, 31, '2025-07-01 22:27:15');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (21, 3, 13, '2025-07-01 22:28:00');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (22, 3, 8, '2025-07-01 22:28:01');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (23, 3, 2, '2025-07-01 22:28:01');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (24, 3, 1, '2025-07-01 22:28:02');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (25, 3, 26, '2025-07-01 22:28:07');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (26, 3, 27, '2025-07-01 22:28:07');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (27, 3, 28, '2025-07-01 22:28:13');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (28, 3, 37, '2025-07-01 22:28:25');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (29, 3, 19, '2025-07-01 22:28:44');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (30, 3, 38, '2025-07-01 22:29:22');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (32, 10, 34, '2025-07-01 22:32:54');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (33, 10, 33, '2025-07-01 22:32:54');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (34, 10, 31, '2025-07-01 22:32:55');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (35, 10, 32, '2025-07-01 22:32:56');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (36, 10, 13, '2025-07-01 22:33:16');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (37, 10, 8, '2025-07-01 22:33:16');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (38, 10, 2, '2025-07-01 22:33:17');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (40, 10, 1, '2025-07-01 22:33:19');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (41, 10, 38, '2025-07-01 22:33:23');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (42, 10, 39, '2025-07-01 22:34:24');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (43, 10, 26, '2025-07-01 22:34:28');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (44, 10, 27, '2025-07-01 22:34:29');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (45, 10, 23, '2025-07-01 22:34:36');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (46, 10, 22, '2025-07-01 22:34:37');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (47, 10, 21, '2025-07-01 22:34:37');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (48, 10, 17, '2025-07-01 22:34:54');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (49, 10, 40, '2025-07-01 22:35:10');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (50, 10, 41, '2025-07-01 22:35:26');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (51, 10, 11, '2025-07-01 22:35:35');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (52, 10, 9, '2025-07-01 22:35:36');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (53, 10, 5, '2025-07-01 22:35:37');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (54, 10, 3, '2025-07-01 22:35:38');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (56, 10, 16, '2025-07-01 22:36:22');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (57, 10, 43, '2025-07-01 22:36:35');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (59, 10, 36, '2025-07-01 22:36:53');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (60, 10, 35, '2025-07-01 22:36:55');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (61, 10, 44, '2025-07-01 22:37:08');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (62, 10, 42, '2025-07-01 22:38:31');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (63, 10, 45, '2025-07-01 23:02:13');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (64, 11, 44, '2025-07-01 23:20:34');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (65, 11, 36, '2025-07-01 23:20:35');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (66, 11, 34, '2025-07-01 23:20:37');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (67, 11, 33, '2025-07-01 23:20:37');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (68, 11, 32, '2025-07-01 23:20:38');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (69, 5, 24, '2025-07-02 00:06:39');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (72, 5, 19, '2025-07-02 00:50:47');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (73, 5, 29, '2025-07-02 00:50:57');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (74, 5, 15, '2025-07-02 00:50:58');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (75, 5, 12, '2025-07-02 00:50:59');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (76, 12, 48, '2025-07-02 08:30:29');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (77, 12, 47, '2025-07-02 08:40:54');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (78, 12, 37, '2025-07-02 08:40:55');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (79, 12, 28, '2025-07-02 08:40:55');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (80, 12, 36, '2025-07-02 08:43:20');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (81, 12, 35, '2025-07-02 08:43:21');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (82, 12, 44, '2025-07-02 08:43:22');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (83, 12, 34, '2025-07-02 08:43:23');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (84, 12, 33, '2025-07-02 08:43:24');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (85, 13, 42, '2025-07-02 09:37:40');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (86, 13, 11, '2025-07-02 09:37:41');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (87, 13, 9, '2025-07-02 09:37:42');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (88, 13, 3, '2025-07-02 09:37:43');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (89, 13, 49, '2025-07-02 09:38:32');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (91, 5, 50, '2025-07-02 09:49:11');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (92, 5, 40, '2025-07-02 09:49:12');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (93, 5, 17, '2025-07-02 09:49:13');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (101, 5, 21, '2025-07-02 09:51:27');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (103, 5, 23, '2025-07-02 09:52:00');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (104, 5, 37, '2025-07-03 11:34:18');
INSERT INTO mybatis.user_like_comment (id, user_id, comment_id, created_at) VALUES (105, 5, 28, '2025-07-03 11:34:18');
