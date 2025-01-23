create table customer
(
    id         bigint auto_increment
        primary key,
    nama       varchar(255) not null,
    alamat     text         not null,
    telepon    varchar(20)  not null,
    mekanik_id bigint       null,
    constraint FKqy1rvrukl27sen0wo9sitt2tk
        foreign key (mekanik_id) references mekanik (id)
);

