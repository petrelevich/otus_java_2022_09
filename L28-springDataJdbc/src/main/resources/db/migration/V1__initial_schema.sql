create table Manager
(
    id    varchar(50) not null primary key,
    label varchar(50)
);

create table client
(
    id           bigserial   not null primary key,
    order_column int         not null,
    name         varchar(50) not null,
    manager_id   varchar(50) not null references Manager (id)
);
create index idx_client_manager_id on client (manager_id);


create table client_details
(
    client_id bigint      not null references client (id),
    info      varchar(50) not null
);