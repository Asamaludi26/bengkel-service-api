package com.bengkelservice.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Layanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String jenisLayanan;
    private String layananType;
    private int totalBiaya;
    private LocalDate tanggal;

    @OneToMany(mappedBy = "layanan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LayananProduk> layananProdukList;

    // Constructor, Getter, dan Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public void setJenisLayanan(String jenisLayanan) {
        this.jenisLayanan = jenisLayanan;
    }

    public String getLayananType() {
        return layananType;
    }

    public void setLayananType(String layananType) {
        this.layananType = layananType;
    }

    public int getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(int totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public List<LayananProduk> getLayananProdukList() {
        return layananProdukList;
    }

    public void setLayananProdukList(List<LayananProduk> layananProdukList) {
        this.layananProdukList = layananProdukList;
    }
}