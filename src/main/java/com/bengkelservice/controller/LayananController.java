package com.bengkelservice.controller;

import com.bengkelservice.model.Layanan;
import com.bengkelservice.model.Customer;
import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.service.LayananService;
import com.bengkelservice.repository.LayananRepository;
import com.bengkelservice.service.CustomerService;
import com.bengkelservice.service.MekanikService;
import com.bengkelservice.service.PenjualanProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.NumberFormat;
import java.util.Locale;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/layanan")
public class LayananController {

    @Autowired
    private LayananService layananService;

    @Autowired
    private PenjualanProdukService penjualanProdukService;

    @Autowired
    private MekanikService mekanikService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public String showAllLayanan(Model model) {
        // Retrieve all customers from the database
        List<Customer> customerList = customerService.getAllCustomer();
        model.addAttribute("customerList", customerList);  // Send customerList to the view
        // Ambil semua layanan dari service
        List<Layanan> layananList = layananService.getAllLayanan();
        System.out.println("Layanan List: " + layananList); // Debug data
        // Tambahkan layananList ke model
        model.addAttribute("layananList", layananList);
        model.addAttribute("layanan", new Layanan()); // Tambahkan atribut 'layanan'
        // Kembalikan nama view yang sesuai (layanan.html)
        return "layanan";
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

        // Menyimpan data layanan dan menghitung total biaya
        @PostMapping("/save")
        public String addLayanan(@RequestParam("layananType") String layananType,
                                 @RequestParam("customerId") Long customerId,
                                 @RequestParam(value = "layananDetails[]", required = false) String[] layananDetails,
                                 @RequestParam(value = "produkIds[]", required = false) List<Long> produkIds,
                                 Model model) {

            List<Layanan> layananList = new ArrayList<>();
            int totalPrice = 0;

            // Menentukan harga layanan berdasarkan pilihan
            if (layananDetails != null) {
                for (String layananDetail : layananDetails) {
                    Layanan layanan = new Layanan();
                    layanan.setJenisLayanan(layananDetail);
                    layanan.setLayananType(layananType);

                    int harga = 0;
                    switch (layananDetail) {
                        case "Pemasangan Aksesoris":
                            harga = 100000;
                            break;
                        case "Pengecekan Kelistrikan":
                            harga = 150000;
                            break;
                        case "Pengecekan Transmisi":
                            harga = 200000;
                            break;
                        case "Pengecekan Rem":
                            harga = 120000;
                            break;
                        case "Pengecekan Mesin":
                            harga = 250000;
                            break;
                        case "Perawatan Kendaraan":
                            harga = 300000;
                            break;
                        case "Pengecekan & Perbaikan Kelistrikan":
                            harga = 500000;
                            break;
                        case "Pengecekan & Perbaikan Transmisi":
                            harga = 600000;
                            break;
                        case "Pengecekan & Perbaikan Sistem Pengereman":
                            harga = 450000;
                            break;
                        case "Pengecekan & Perbaikan Mesin":
                            harga = 800000;
                            break;
                    }
                    totalPrice += harga;
                    layanan.setTotalBiaya(harga);
                    layananList.add(layanan);
                }
            }

            // Menambahkan harga produk jika ada
            PenjualanProduk produk = null;
            if (produkIds != null) {
                for (Long produkId : produkIds) {
                    produk = penjualanProdukService.getProdukById(produkId);
                    totalPrice += produk.getHarga();
                }
            }

            // Format totalPrice sebagai mata uang (Rp)
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String formattedTotalPrice = currencyFormat.format(totalPrice);

            // Retrieve customer dari ID
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalArgumentException("Customer tidak ditemukan!");
            }

            // Set customer ke layanan
            for (Layanan layanan : layananList) {
                layanan.setCustomer(customer); // MekanikId bisa diakses melalui relasi ke Customer
            }

            layananService.saveLayanan(layananList); // Simpan layanan ke database

            // Tambahkan atribut ke model untuk view
            model.addAttribute("customers", customerService.getAllCustomer());
            model.addAttribute("jenisLayanan", List.of(
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
            ));
            model.addAttribute("produkList", penjualanProdukService.getAllProduk());
            model.addAttribute("customer", customer);
            model.addAttribute("totalPrice", formattedTotalPrice);
            model.addAttribute("layananList", layananList);

            // Proses data yang masuk
            System.out.println("Jenis Layanan: " + layananType);
            System.out.println("Customer ID: " + customerId);
            System.out.println("Produk: " + produk); // Produk dan jumlahnya

            return "redirect:/layanan/all";
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

    // Endpoint untuk mendapatkan layanan berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Layanan> getLayananById(@PathVariable Long id) {
        Layanan layanan = layananService.getLayananById(id);
        if (layanan != null) {
            return ResponseEntity.ok(layanan);
        } else {
            return ResponseEntity.notFound().build();
        }
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
