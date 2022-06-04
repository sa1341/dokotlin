create table board
(
    id bigint not null auto_increment,
    author varchar(255) not null,
    title  varchar(255) not null,
    content varchar(255) not null,
    `created_at` DATETIME NOT NULL COMMENT 'created time',
    `updated_at` DATETIME NOT NULL COMMENT 'last modified time',
    primary key (id)
) engine=InnoDB;

create table reply
(
    id bigint not null auto_increment,
    author varchar(255) not null,
    title varchar(255) not null,
    content varchar(255) not null,
    board_id bigint not null,
    `created_at` DATETIME NOT NULL COMMENT 'created time',
    `updated_at` DATETIME NOT NULL COMMENT 'last modified time',
    primary key (id),
    foreign key (board_id) references board(id)
) engine=InnoDB;

alter table reply
add constraint board_fk
foreign key (board_id)
references board (id)
