create table clients(
    id bigint not null auto_increment,
    client_id varchar(100) not null,
    client_secret varchar(100) not null,

    primary key(id)
);