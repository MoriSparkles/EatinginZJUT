create table comment
(
    comment_id int auto_increment
        primary key,
    user_id    int                                not null,
    dish_id    int                                not null,
    rating     tinyint                            null,
    content    text                               null,
    created_at datetime default CURRENT_TIMESTAMP null,
    constraint comment_ibfk_1
        foreign key (user_id) references user (user_id),
    constraint comment_ibfk_2
        foreign key (dish_id) references dish (dish_id),
    check (`rating` between 1 and 5)
);

create index dish_id
    on comment (dish_id);

create index user_id
    on comment (user_id);

INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (1, 1, 1, 4, '这道土豆泥真的很好吃，口感细腻，奶香味十足，强烈推荐！', '2025-06-28 19:40:54');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (2, 2, 1, 5, '价格合理，量也很足，作为一道配菜非常不错。', '2025-06-28 19:41:18');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (3, 1, 3, 3, '味道偏淡了些，希望可以再加点调料提味。', '2025-06-28 19:42:27');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (4, 2, 4, 2, '鸡肉很嫩，土豆炖得很烂，汤汁浓郁，非常适合搭配米饭一起吃。', '2025-06-28 19:42:27');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (5, 4, 3, 5, '价格公道，质量上乘，是早餐的理想之选。', '2025-06-28 21:01:41');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (8, 5, 1, 5, '很好吃不错很实惠很管饱', '2025-06-28 21:30:47');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (9, 5, 3, 5, '外酥里嫩，酸甜适中，每次来都会点这道菜。', '2025-06-28 21:35:30');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (11, 5, 3, 4, '分量足够大，适合分享，是一道不错的开胃菜。', '2025-06-28 21:43:27');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (12, 5, 6, 4, '分量足够大，适合分享，是一道不错的开胃菜。', '2025-06-28 22:20:20');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (13, 4, 1, 3, '面条筋道，牛肉块大且入味，汤头鲜美，值得一试。', '2025-06-28 23:45:46');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (14, 4, 1, 3, '牛肉略显少了一点，不过整体味道还是不错的。', '2025-06-29 16:16:33');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (15, 5, 6, 4, '正宗的兰州风味，汤头清亮，牛肉片薄而香，非常地道。', '2025-06-29 17:20:33');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (16, 5, 11, 4, '性价比高，早餐或午餐的好选择。', '2025-06-29 17:23:19');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (17, 5, 12, 5, '酱料丰富，面条劲道，芝麻酱的味道十分浓郁。', '2025-06-29 21:19:56');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (19, 4, 14, 4, '口味独特，对于喜欢重口味的朋友来说是个好选择。', '2025-06-30 14:55:57');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (20, 4, 13, 3, '经典的组合，早餐的最佳选择之一，营养均衡又美味。', '2025-06-30 15:02:55');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (21, 5, 15, 4, '面条口感还不错，跟我在台州吃的是同一种。分量很多，很大碗，差不多两个巴掌大一点。味道总体还是偏清淡一些的。', '2025-07-01 16:22:49');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (22, 4, 15, 3, '汤底较浓，青椒味重一点，不是那种骨汤，更像是拌川的汤汁加水稀释了，但也很好喝。', '2025-07-01 16:23:30');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (23, 2, 15, 4, '鸡蛋带着点油香，吃起来不是预制蛋。嗯，是溏心蛋。猪油渣软软的，油香很足，又泡了汤汁，跟博文早上葱油拌面的猪油渣不一样。', '2025-07-01 16:24:07');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (24, 3, 15, 2, '面条是现成的面条，感觉没什么味道，可能时间比较短，或者是汤面分开煮的，不够耐吃。', '2025-07-01 16:24:31');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (25, 6, 16, 4, '鸡丝拌面，是冷拌面，酸、咸、微辣，整体香气靠辣椒油和麻酱提味。', '2025-07-01 22:13:06');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (26, 6, 16, 5, '配料新鲜丰富，层次分明。有黄瓜丝、萝卜丝、洋葱丝，花生米和千张，很像是凉皮的配置。鸡丝带一点筋，不是那种吃起来干巴巴没肉味的纯粹的鸡胸肉。还有土豆丝，有点酸辣土豆丝的感觉。', '2025-07-01 22:13:57');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (27, 6, 16, 3, '面条是圆圆的细面，口感像拉面，还有点粗细不一。我愿称之为鸡丝凉皮的拉面版。', '2025-07-01 22:14:27');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (28, 9, 17, 5, '量大管饱还实惠，一个鸭腿加两个素菜只要12块，师傅还会送你一勺牛腩汤，yyds', '2025-07-01 22:20:00');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (29, 9, 6, 4, '热干面如其名又热又干，不过拌的芝麻酱很香', '2025-07-01 22:21:01');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (30, 8, 2, 4, '胃口不好的时候吃起来很舒服', '2025-07-01 22:25:14');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (31, 8, 18, 4, '咸肉蒸蛋猪肉有点像卤肉，口感偏软，咸香的一个味道，是我比较喜欢的调味', '2025-07-01 22:25:43');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (32, 8, 18, 4, '炒空心菜挺清爽的，应该用的不是荤油。空心菜很新鲜，恰到好处的咸味，满满都是空心菜自身的清香，很有家常菜的感觉', '2025-07-01 22:26:24');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (33, 8, 18, 3, '蒸蛋有那种自己蒸蛋的小气孔，加了少量酱油，呈现淡棕色，也是很有家常菜的感觉', '2025-07-01 22:26:35');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (34, 8, 18, 5, '炒菜心我是看着厨师刚出锅装碗的，清爽多汁，甜甜的，很新鲜。我朋友也说比博文大众窗口的炒菜心好吃', '2025-07-01 22:26:47');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (35, 8, 18, 1, '蒸娃娃菜是生娃娃菜上面放几小片咸猪肉，娃娃菜本身没有加入任何调味', '2025-07-01 22:26:58');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (36, 8, 18, 2, '白切肉感觉是水煮肉块，汤汁不是卤肉汤而是调制酱油', '2025-07-01 22:27:09');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (37, 3, 17, 5, '量大管饱还实惠+1+1+1', '2025-07-01 22:28:23');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (38, 3, 8, 4, '原味的五香调味料很不错，培根很薄很入味', '2025-07-01 22:29:19');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (39, 10, 8, 5, '烤盘饭种类丰富自由挑选，熟制品会下锅再过一遍料，个人感觉比精弘二楼的自选要好吃很多', '2025-07-01 22:34:20');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (40, 10, 12, 5, '夜宵的鸭腿yydsyyds', '2025-07-01 22:35:08');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (41, 10, 9, 4, 'sdfhioweahoiweahg', '2025-07-01 22:35:23');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (42, 10, 3, 5, '酸甜适中，每次来都会点这道菜，太好吃了', '2025-07-01 22:35:59');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (43, 10, 11, 4, '早上赶路早八的首选早餐', '2025-07-01 22:36:33');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (44, 10, 18, 3, '味道一般但是价格实惠', '2025-07-01 22:37:06');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (45, 10, 7, 5, '赶早八的时候在西9楼下来一碗葱油拌面边走边吃，太有生活了', '2025-07-01 23:01:18');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (46, 10, 7, 5, '不知道为什么学校里好几家葱油拌面就觉得这家最好吃', '2025-07-01 23:02:09');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (47, 5, 17, 4, 'wefewfwefwefwef', '2025-07-02 00:31:21');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (48, 12, 13, 4, '经典的组合，早餐的最佳选择之一，营养均衡又美味。', '2025-07-02 08:30:26');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (49, 13, 17, 4, '量大管饱还实惠，一个鸭腿加两个素菜只要12块，师傅还会送你一勺牛腩汤，yyds', '2025-07-02 09:38:29');
INSERT INTO mybatis.comment (comment_id, user_id, dish_id, rating, content, created_at) VALUES (50, 5, 12, 4, 'kjbhoihoiuhoi', '2025-07-02 09:49:01');
