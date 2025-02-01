package com.bengkelservice.service;

import com.bengkelservice.model.Layanan;
import com.bengkelservice.model.LayananProduk;
import com.bengkelservice.repository.LayananRepository;
import com.bengkelservice.repository.LayananProdukRepository;
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
    private LayananProdukRepository layananProdukRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MekanikRepository mekanikRepository;

    public List<Layanan> getAllLayanan() {
        return layananRepository.findAll();
    }

    public Layanan saveLayanan(Layanan layanan) {
        return layananRepository.save(layanan);
    }

    public Layanan getLayananById(Long id) {
        Optional<Layanan> layanan = layananRepository.findById(id);
        return layanan.orElse(null);
    }

    public List<LayananProduk> getProdukByLayanan(Long layananId) {
        return layananProdukRepository.findByLayananId(layananId);
    }

    public boolean updateLayanan(Long id, Layanan updatedLayanan) {
        Optional<Layanan> existingLayananOpt = layananRepository.findById(id);
        if (existingLayananOpt.isPresent()) {
            Layanan existingLayanan = existingLayananOpt.get();
            existingLayanan.setCustomer(updatedLayanan.getCustomer());
            existingLayanan.setJenisLayanan(updatedLayanan.getJenisLayanan());
            existingLayanan.setTotalBiaya(updatedLayanan.getTotalBiaya());
            existingLayanan.setTanggal(updatedLayanan.getTanggal());
            layananRepository.save(existingLayanan);
            return true;
        }
        return false;
    }

    public void deleteLayananById(Long id) {
        layananRepository.deleteById(id);
    }

    public String formatCurrency(int amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return currencyFormat.format(amount);
    }
}