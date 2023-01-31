create table notice
(
    notice_id   varchar(36) primary key,
    title       varchar(255) null,
    writer_name varchar(255) null,
    writer_id   varchar(255) null,
    created_at  date         null,
    topic       varchar(255) not null
);