package com.bengkelservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Layanan {

    @Id
    @GeneratedValue
    private Long id;
    private String layananName;
    private String layananCategory;
    private String layananType;
    private int harga; // Tambahkan field harga

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLayananName() {
        return layananName;
    }

    public void setLayananName(String layananName) {
        this.layananName = layananName;
    }

    public String getLayananCategory() {
        return layananCategory;
    }

    public void setLayananCategory(String layananCategory) {
        this.layananCategory = layananCategory;
    }

    public String getLayananType() {
        return layananType;
    }

    public void setLayananType(String layananType) {
        this.layananType = layananType;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
