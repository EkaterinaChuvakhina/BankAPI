create table clients
(
    client_id   bigint      not null primary key auto_increment,
    name        varchar(50) not null,
    middle_name varchar(50) not null,
    last_name   varchar(50) not null
);

create table accounts
(
    account_id     bigint         not null primary key auto_increment,
    account_number varchar(20)    not null unique,
    amount         decimal(19, 6) not null,
    client_id      bigint         not null,
    constraint fk_client foreign key (client_id) references clients (client_id)

);

create table cards
(
    cards_id    bigint      not null primary key auto_increment,
    card_number varchar(16) not null unique,
    account_id  bigint      not null,
    constraint fk_account foreign key (account_id)
        references accounts (account_id)
)