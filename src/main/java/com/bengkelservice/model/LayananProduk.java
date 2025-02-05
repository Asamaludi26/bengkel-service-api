package com.bengkelservice.model;

import jakarta.persistence.*;

@Entity
public class LayananProduk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "layanan_id", nullable = false)
    private Layanan layanan;

    @ManyToOne
    @JoinColumn(name = "produk_id", nullable = false)
    private PenjualanProduk produk;

    private int jumlah; // Menyimpan jumlah produk yang dibeli dalam layanan

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Layanan getLayanan() {
        return layanan;
    }

    public void setLayanan(Layanan layanan) {
        this.layanan = layanan;
    }

    public PenjualanProduk getProduk() {
        return produk;
    }

    public void setProduk(PenjualanProduk produk) {
        this.produk = produk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}