create table ceps(
    id bigint not null auto_increment,
    numero bigint not null,
    data datetime not null,
    cep varchar(100) not null,
    logradouro varchar(100) not null,
    complemento varchar(100),
    unidade varchar(100),
    bairro varchar(100),
    localidade varchar(100),
    uf varchar(100),
    estado varchar(100),
    regiao varchar(100),
    ibge varchar(100),
    gia varchar(100),
    ddd varchar(100),
    siafi varchar(100),

    primary key(id)
);