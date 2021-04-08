create database live default character set utf8;

create user 'live'@'localhost' identified by '1234';
create user 'live'@'%' identified by '1234';

grant all privileges on live.* to 'live'@'localhost';
grant all privileges on live.* to 'live'@'%';

create table live.member (
    memberid varchar(50) primary key,
    name varchar(50) not null,
    password varchar(10) not null
) engine=InnoDB default character set = utf8;

create table live.article(
article_no int auto_increment primary key,
writer_id varchar(50) not null,
writer_name varchar(50) not null,
title varchar(255) not null
) engine=InnoDB default character set = utf8;

create table live.article_content(
article_no int primary key,
content text
)engine=InnoDB default character set = utf8;