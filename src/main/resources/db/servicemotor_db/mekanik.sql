create table mekanik
(
    id            bigint auto_increment
        primary key,
    nama          varchar(255) not null,
    keahlian      varchar(255) not null,
    status        varchar(255) null,
    nomor_telepon varchar(255) null
);

