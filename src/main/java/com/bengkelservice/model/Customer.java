package com.bengkelservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama;
    private String alamat;
    private String telepon;

    @ManyToOne
    private Mekanik mekanik; // Relasi dengan mekanik

    // Konstruktor tanpa argumen (default constructor)
    public Customer() {
    }

    // Konstruktor dengan 3 parameter
    public Customer(String nama, String alamat, String telepon, Mekanik mekanik) {
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
        this.mekanik = mekanik;
    }

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public Mekanik getMekanik() {
        return mekanik;
    }

    public void setMekanik(Mekanik mekanik) {
        this.mekanik = mekanik;
    }

    // Getter untuk mekanikId yang digunakan di form
    public Long getMekanikId() {
        return mekanik != null ? mekanik.getId() : null;
    }

    public void setMekanikId(Long mekanikId) {
        if (mekanikId != null) {
            this.mekanik = new Mekanik(); // Assuming a setter for Mekanik exists
            this.mekanik.setId(mekanikId);
        }
    }
}
