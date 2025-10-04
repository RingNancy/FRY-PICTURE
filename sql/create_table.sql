create database if not exists rin_picture;

use rin_picture;

-- 用户表，updateTime 和 editTime之间的区别在于，前者由数据库自动更新， 该条记录任何字段发生变化的时候，都会发生改变，editTime 则是用户修改信息，由业务代码实现
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(236)                           null comment '用户名',
    userAvatar   varchar(1024)                          null comment '头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色 user/admin',
    editTime     datetime     default current_timestamp not null comment '编辑时间',
    createTime   datetime     default current_timestamp not null comment '创建时间',
    updateTime   datetime     default current_timestamp not null on update current_timestamp comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除， 1为删除',
    UNIQUE KEY uk_userAccount (userAccount),
    INDEX idx_userName (userName)
) comment '用户' collate = utf8mb4_unicode_ci;
