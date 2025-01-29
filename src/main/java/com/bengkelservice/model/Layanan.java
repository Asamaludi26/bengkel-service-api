package com.bengkelservice.model;

import jakarta.persistence.*;

import java.util.List;
import com.bengkelservice.model.PenjualanProduk;


import java.time.LocalDate;

@Entity
public class Layanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    private String jenisLayanan;

    private String layananType;

    @ManyToMany
    private List<PenjualanProduk> produkList;

    private Integer totalBiaya;

    private LocalDate tanggal;

    // Getter dan Setter
    public Long getMekanikId() {
        // Mengambil mekanikId langsung dari customer
        return customer != null ? customer.getMekanikId() : null;
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

    public List<PenjualanProduk> getProdukList() {
        return produkList;
    }

    public void setProdukList(List<PenjualanProduk> produkList) {
        this.produkList = produkList;
    }

    public Integer getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(Integer totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }
}
