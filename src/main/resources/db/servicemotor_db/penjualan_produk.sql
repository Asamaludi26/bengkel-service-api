create table penjualan_produk
(
    id    bigint auto_increment
        primary key,
    harga double       not null,
    stok  int          not null,
    foto  varchar(255) null,
    nama  varchar(100) null
);

