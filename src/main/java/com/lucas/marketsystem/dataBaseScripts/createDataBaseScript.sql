create table t_fornecedor
(
    id              bigint auto_increment
        primary key,
    nome            varchar(100) not null,
    inicio_contrato date         not null,
    final_contrato  date         not null,
    descricao       varchar(100) null,
    logo            varchar(200) null
);

create table t_produtos
(
    id             bigint auto_increment
        primary key,
    nome           varchar(100) not null,
    observacoes    varchar(100) null,
    preco_unidade  double       not null,
    quantidade     bigint       not null,
    categoria      varchar(100) not null,
    cod_fornecedor bigint       not null,
    constraint t_produtos_FK
        foreign key (cod_fornecedor) references t_fornecedor (id)
);

