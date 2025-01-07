create table logs(
    id bigint not null auto_increment,
    data datetime not null,
    log TEXT not null,

    primary key(id)
);