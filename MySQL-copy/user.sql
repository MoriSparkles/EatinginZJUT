create table user
(
    user_id  int auto_increment
        primary key,
    name     varchar(255) not null,
    password varchar(255) not null,
    phone    varchar(20)  null,
    constraint id
        unique (user_id)
);

INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (1, '01', 'password', '11111111111');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (2, '02', 'password', '11111111112');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (3, '033', 'password', '12345678901');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (4, '7890', '111111', '15411545428');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (5, 'Mori', '202105140414', '13586022542');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (6, '666', '666666', '66666666666');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (7, '333', '33333333', '33333333333');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (8, '234', '123456', '23423423423');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (9, 'err', 'errerr', '12312312312');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (10, '1222', '122222', '12222222222');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (11, 'abc', 'abcccc', '12345666666');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (12, 'dsf', 'dddddd', '22222222222');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (13, 'fff', 'ffffff', '11122233345');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (14, 'ffff', 'ffffff', '78978978999');
INSERT INTO mybatis.user (user_id, name, password, phone) VALUES (15, 'qqqvhj', '4585vgg/*', '16656561111');
