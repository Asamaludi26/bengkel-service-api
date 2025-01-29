package com.bengkelservice.service;

import com.bengkelservice.model.Layanan;
import com.bengkelservice.repository.LayananRepository;
import com.bengkelservice.repository.CustomerRepository;
import com.bengkelservice.repository.MekanikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class LayananService {

    @Autowired
    private LayananRepository layananRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MekanikRepository mekanikRepository;

    // Mengambil semua data layanan
    public List<Layanan> getAllLayanan() {
        return layananRepository.findAll();
    }

    // Menyimpan daftar layanan baru
    public void saveLayanan(List<Layanan> layananList) {
        layananRepository.saveAll(layananList);
    }

    // Mengambil layanan berdasarkan ID
    public Layanan getLayananById(Long id) {
        Optional<Layanan> layanan = layananRepository.findById(id);
        return layanan.orElse(null);
    }

    // Memperbarui data layanan
    public boolean updateLayanan(Long id, Layanan updatedLayanan) {
        Optional<Layanan> existingLayananOpt = layananRepository.findById(id);
        if (existingLayananOpt.isPresent()) {
            Layanan existingLayanan = existingLayananOpt.get();
            existingLayanan.setCustomer(updatedLayanan.getCustomer());
            existingLayanan.setJenisLayanan(updatedLayanan.getJenisLayanan());
            existingLayanan.setProdukList(updatedLayanan.getProdukList());
            existingLayanan.setTotalBiaya(updatedLayanan.getTotalBiaya());
            existingLayanan.setTanggal(updatedLayanan.getTanggal());
            layananRepository.save(existingLayanan);
            return true;
        }
        return false;
    }

    // Menghapus data layanan berdasarkan ID
    public void deleteLayananById(Long id) {
        layananRepository.deleteById(id);
    }

    // Mengatur jenis layanan
    public void setLayananType(Layanan layanan, String layananType) {
        layanan.setLayananType(layananType);
    }

    // Format harga ke bentuk mata uang Indonesia (Rupiah)
    public String formatCurrency(int amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return currencyFormat.format(amount);
    }
}
