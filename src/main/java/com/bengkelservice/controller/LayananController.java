package com.bengkelservice.controller;

import com.bengkelservice.model.Layanan;
import com.bengkelservice.model.Customer;
import com.bengkelservice.model.LayananProduk;
import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.service.LayananService;
import com.bengkelservice.repository.LayananRepository;
import com.bengkelservice.repository.LayananProdukRepository;
import com.bengkelservice.service.CustomerService;
import com.bengkelservice.service.MekanikService;
import com.bengkelservice.service.PenjualanProdukService;
import com.bengkelservice.service.LayananProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Controller
@RequestMapping("/layanan")
public class LayananController {

    @Autowired
    private LayananService layananService;

    @Autowired
    private LayananProdukService layananProdukService;

    @Autowired
    private PenjualanProdukService penjualanProdukService;

    @Autowired
    private MekanikService mekanikService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public String showAllLayanan(Model model) {
        List<Customer> customerList = customerService.getAllCustomer();
        model.addAttribute("customerList", customerList);

        List<Layanan> layananList = layananService.getAllLayanan();
        for (Layanan layanan : layananList) {
            List<LayananProduk> layananProdukList = layananService.getProdukByLayanan(layanan.getId());
            layanan.setLayananProdukList(layananProdukList);
        }

        model.addAttribute("layananList", layananList);
        model.addAttribute("layanan", new Layanan());
        return "layanan";
    }

    // Endpoint untuk mendapatkan layanan berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<List<Layanan>> getLayananById(@PathVariable Long id) {
        Layanan layanan = layananService.getLayananById(id);
        if (layanan != null) {
            return ResponseEntity.ok(List.of(layanan)); // Ubah ke List.of()
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Menampilkan form layanan
        @GetMapping
        public String showLayananForm(Model model) {
            // Fetch all customers
            List<Customer> customers = customerService.getAllCustomer();
            model.addAttribute("customers", customers);

            // Jenis layanan
            List<String> jenisLayanan = List.of(
                    "Pemasangan Aksesoris",
                    "Pengecekan Kelistrikan",
                    "Pengecekan Transmisi",
                    "Pengecekan Rem",
                    "Pengecekan Mesin",
                    "Perawatan Kendaraan",
                    "Pengecekan & Perbaikan Kelistrikan",
                    "Pengecekan & Perbaikan Transmisi",
                    "Pengecekan & Perbaikan Sistem Pengereman",
                    "Pengecekan & Perbaikan Mesin"
            );
            model.addAttribute("jenisLayanan", jenisLayanan);

            // Fetch all products
            List<PenjualanProduk> produkList = penjualanProdukService.getAllProduk();
            model.addAttribute("produkList", produkList);

            return "layanan";
        }

    @PostMapping("/save")
    public String addLayanan(@RequestParam("layananType") String layananType,
                             @RequestParam("customerId") Long customerId,
                             @RequestParam(value = "layananDetails[]", required = false) String[] layananDetails,
                             @RequestParam(value = "produkIds", required = false) String produkData,
                             @RequestParam("tanggal") String tanggalInput, // Tambahkan parameter untuk tanggal
                             Model model) {

        List<Layanan> layananList = new ArrayList<>();
        int totalBiaya = 0; // Total biaya yang akan dihitung

        // Parse tanggal dari input string menjadi objek Date
        LocalDate tanggal = LocalDate.parse(tanggalInput);

        // Step 1: Menghitung total biaya layanan berdasarkan jenis layanan
        if (layananDetails != null) {
            for (String layananDetail : layananDetails) {
                Layanan layanan = new Layanan();
                layanan.setJenisLayanan(layananDetail);
                layanan.setLayananType(layananType);
                layanan.setTanggal(tanggal); // Set tanggal pada layanan

                // Harga berdasarkan jenis layanan
                int hargaLayanan = switch (layananDetail) {
                    case "Pemasangan Aksesoris" -> 100000;
                    case "Pengecekan Kelistrikan" -> 150000;
                    case "Pengecekan Transmisi" -> 200000;
                    case "Pengecekan Rem" -> 120000;
                    case "Pengecekan Mesin" -> 250000;
                    case "Perawatan Kendaraan" -> 300000;
                    case "Pengecekan & Perbaikan Kelistrikan" -> 500000;
                    case "Pengecekan & Perbaikan Transmisi" -> 600000;
                    case "Pengecekan & Perbaikan Sistem Pengereman" -> 450000;
                    case "Pengecekan & Perbaikan Mesin" -> 800000;
                    default -> 0;
                };

                totalBiaya += hargaLayanan; // Tambahkan biaya layanan ke total biaya
                layanan.setTotalBiaya(hargaLayanan); // Set total biaya pada layanan

                // Simpan layanan terlebih dahulu
                layananService.saveLayanan(layanan);
                layananList.add(layanan);
            }
        }

        // Step 2: Menambahkan produk yang dibeli ke total biaya layanan
        if (produkData != null && !produkData.isEmpty()) {
            String[] produkItems = produkData.split(",");
            for (String item : produkItems) {
                String[] split = item.split(":");
                Long produkId = Long.parseLong(split[0]);
                int jumlah = Integer.parseInt(split[1]);

                // Ambil harga produk berdasarkan ID
                PenjualanProduk produk = penjualanProdukService.getProdukById(produkId);
                if (produk != null) {
                    totalBiaya += produk.getHarga() * jumlah; // Tambahkan harga produk yang dibeli
                }

                // Simpan data LayananProduk (produk yang dibeli)
                LayananProduk layananProduk = new LayananProduk();
                layananProduk.setLayanan(layananList.get(0)); // Set layanan untuk layananProduk
                layananProduk.setProduk(produk);
                layananProduk.setJumlah(jumlah);

                // Simpan LayananProduk setelah layanan disimpan
                layananProdukService.saveLayananProduk(layananProduk);
            }
        }

        // Step 3: Pastikan total biaya diperbarui dengan benar
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer tidak ditemukan!");
        }

        for (Layanan layanan : layananList) {
            layanan.setCustomer(customer);
            layanan.setTotalBiaya(totalBiaya); // Update total biaya layanan
            layanan.setTanggal(tanggal); // Set tanggal pada layanan
            layananService.saveLayanan(layanan); // Simpan layanan dengan total biaya dan tanggal yang benar
        }

        return "redirect:/layanan/all"; // Redirect ke halaman setelah berhasil menyimpan
    }

    // Endpoint untuk mendapatkan daftar produk
    @GetMapping("/produk/list")
    public ResponseEntity<List<PenjualanProduk>> getProdukList() {
        List<PenjualanProduk> produkList = penjualanProdukService.getAllProduk();
        return ResponseEntity.ok(produkList);
    }


    // Endpoint untuk mendapatkan data layanan
    @GetMapping("/list")
    public ResponseEntity<List<Layanan>> getAllLayanan() {
        List<Layanan> layananList = layananService.getAllLayanan();
        return ResponseEntity.ok(layananList);
    }

    // Endpoint untuk menghapus data layanan
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLayanan(@PathVariable Long id) {
        layananService.deleteLayananById(id);
        return ResponseEntity.ok("Layanan berhasil dihapus.");
    }

    // Endpoint untuk memperbarui data layanan
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateLayanan(@PathVariable Long id, @RequestBody Layanan updatedLayanan) {
        boolean isUpdated = layananService.updateLayanan(id, updatedLayanan);
        if (isUpdated) {
            return ResponseEntity.ok("Layanan berhasil diperbarui.");
        } else {
            return ResponseEntity.badRequest().body("Gagal memperbarui layanan.");
        }
    }
}
