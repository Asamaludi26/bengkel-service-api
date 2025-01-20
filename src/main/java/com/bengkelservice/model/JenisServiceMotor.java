package com.bengkelservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
public class JenisServiceMotor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaService;
    private double harga;

    // Relasi dengan Customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Relasi dengan Mekanik
    @ManyToOne
    @JoinColumn(name = "mekanik_id", nullable = false)
    private Mekanik mekanik;

    private LocalDate tanggalPengerjaan;
    private double totalBiaya;

    // List Produk Terpilih (map produk dan jumlahnya)
    @ElementCollection
    @CollectionTable(name = "jenis_service_produk", joinColumns = @JoinColumn(name = "jenis_service_id"))
    @MapKeyJoinColumn(name = "produk_id")
    @Column(name = "jumlah_barang")
    private Map<PenjualanProduk, Integer> produkTerpakai = new HashMap<>();

    // Getter dan setter untuk produkTerpakai
    public Map<PenjualanProduk, Integer> getProdukTerpakai() {
        return produkTerpakai;
    }

    public void setProdukTerpakai(Map<PenjualanProduk, Integer> produkTerpakai) {
        this.produkTerpakai = produkTerpakai;
    }

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaService() {
        return namaService;
    }

    public void setNamaService(String namaService) {
        this.namaService = namaService;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Mekanik getMekanik() {
        return mekanik;
    }

    public void setMekanik(Mekanik mekanik) {
        this.mekanik = mekanik;
    }

    public LocalDate getTanggalPengerjaan() {
        return tanggalPengerjaan;
    }

    public void setTanggalPengerjaan(LocalDate tanggalPengerjaan) {
        this.tanggalPengerjaan = tanggalPengerjaan;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(double totalBiaya) {
        this.totalBiaya = totalBiaya;
    }
}