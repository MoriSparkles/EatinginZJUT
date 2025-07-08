create table dish
(
    dish_id     int auto_increment
        primary key,
    campus      varchar(100)                       not null,
    canteen     varchar(100)                       not null,
    stall       varchar(100)                       not null,
    dish_name   varchar(100)                       not null,
    price       varchar(255)                       null,
    image_url   varchar(255)                       null,
    created_at  datetime default CURRENT_TIMESTAMP null,
    description varchar(500)                       null
);

INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (1, '朝晖校区', '毓秀堂', '轻食自选', '土豆泥', '18.80元/500克', '/images/1.jpg', '2025-06-28 19:26:28', '土豆泥口感细腻，味道温和，富含淀粉，营养丰富。这道土豆泥可能添加了适量的奶油和盐调味，使其更加美味。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (2, '朝晖校区', '精弘食堂', '黄焖鸡', '土豆黄焖鸡', '15元', '/images/2.jpg', '2025-06-28 19:30:41', '这道菜以鸡肉为主料，配以土豆炖煮而成，汤汁浓郁，鸡肉鲜嫩，土豆软糯，口味适中，非常适合搭配米饭食用。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (3, '屏峰校区', '家和堂', '同窗聚自选菜', '糖醋里脊', '20元/500克', '/images/3.jpg', '2025-06-28 19:32:44', '糖醋里脊是中国传统名菜之一，外皮酥脆，肉质鲜嫩，酸甜可口，色泽红亮，十分开胃。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (4, '莫干山校区', '德馨府', '民族风味', '红烧牛肉面', '15元', '/images/4.jpg', '2025-06-28 19:34:11', '红烧牛肉面以其丰富的味道和香气著称，面条劲道，牛肉块大而入味，汤头浓郁，带有淡淡的香料气息，让人回味无穷。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (5, '莫干山校区', '德馨府', '民族风味', '兰州牛肉面', '12元', '/images/5.jpg', '2025-06-28 22:17:18', '口味独特，对于喜欢重口味的朋友来说是个好选择。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (6, '莫干山校区', '德馨府', '风味小吃', '热干面', '10元', '/images/6.jpg', '2025-06-28 22:20:07', '包子皮柔软，馅料充足，肉汁饱满，每一口都是享受。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (7, '屏峰校区', '养贤府', '早点', '葱油拌面', '2.5元', '/images/7.jpg', '2025-06-28 22:22:08', '葱油拌面是一道简单却极具风味的传统中式面食，面条爽滑，葱油香味扑鼻，咸香适口，经济实惠。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (8, '朝晖校区', '博文园', '烤盘饭', '烤培根', '8.8元/300克', '/images/8.jpg', '2025-06-28 22:25:49', '包子皮柔软，馅料充足，肉汁饱满，每一口都是享受。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (9, '朝晖校区', '新教科楼售卖点', '西点部', '培根煎蛋土司', '6元', '/images/9.jpg', '2025-06-28 22:27:45', '口味独特，对于喜欢重口味的朋友来说是个好选择。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (10, '屏峰校区', '西9楼下', '早点摊', '葱油拌面', '2.5元', '/images/10.jpg', '2025-06-28 22:30:04', '葱油拌面是一道简单却极具风味的传统中式面食，面条爽滑，葱油香味扑鼻，咸香适口，经济实惠。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (11, '屏峰校区', '生活区', '芭比馒头', '鲜汁肉包', '2元', '/images/11.jpg', '2025-06-28 22:30:56', '烤得恰到好处，肉质鲜嫩多汁，配上适当的调味品更加美味。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (12, '朝晖校区', '精弘食堂', '夜宵', '卤鸭腿', '8元', '/images/12.jpg', '2025-06-29 21:19:19', '经典的组合，早餐的最佳选择之一，营养均衡又美味。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (13, '朝晖校区', '毓秀堂', '新昌炒年糕', '鸡蛋炒年糕', '9元', '/images/13.png', '2025-06-29 23:14:22', '经典的组合，早餐的最佳选择之一，营养均衡又美味。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (14, '朝晖校区', '综合楼售卖点', '关东煮', '烤肠', '3.8元', '/images/14.jpg', '2025-06-29 23:51:30', '烤得恰到好处，肉质鲜嫩多汁，配上适当的调味品更加美味。');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (15, '朝晖校区', '精弘食堂', '兰溪手擀面', '青椒肉丝面', '13元', '/images/15.png', '2025-07-01 15:56:54', '手擀面');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (16, '朝晖校区', '精弘食堂', '西北风味', '鸡丝拌面', '14元', '/images/16.jpeg', '2025-07-01 22:11:58', '鸡丝、面食、素食');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (17, '朝晖校区', '精弘食堂', '毓秀鸭腿饭', '鸭腿饭', '12元', '/images/17.jpg', '2025-07-01 22:17:37', '鸭腿、饭类、微辣');
INSERT INTO mybatis.dish (dish_id, campus, canteen, stall, dish_name, price, image_url, created_at, description) VALUES (18, '朝晖校区', '博文园', '味妈妈小碗菜', '咸肉蒸蛋', '8元', '/images/18.jpeg', '2025-07-01 22:24:00', '咸、肉类、蛋类、清蒸、不辣');
