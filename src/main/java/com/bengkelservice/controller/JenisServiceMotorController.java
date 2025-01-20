package com.bengkelservice.controller;

import com.bengkelservice.model.Customer;
import com.bengkelservice.model.JenisServiceMotor;
import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.service.CustomerService;
import com.bengkelservice.model.Mekanik;
import com.bengkelservice.service.MekanikService;
import com.bengkelservice.service.JenisServiceMotorService;
import com.bengkelservice.service.PenjualanProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/jenis-service")
public class JenisServiceMotorController {

    @Autowired
    private JenisServiceMotorService service;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PenjualanProdukService penjualanProdukService;

    @Autowired
    private MekanikService mekanikService;

    @GetMapping("/all")
    public String getAllJenisService(Model model) {
        List<JenisServiceMotor> jenisServiceList = service.getAllJenisService();
        List<Customer> customerList = customerService.getAllCustomer();
        List<PenjualanProduk> produkList = penjualanProdukService.getAllProduk();
        List<Mekanik> mekanikList = mekanikService.getAllMekanik(); // Ambil data mekanik

        model.addAttribute("jenisServiceList", jenisServiceList);
        model.addAttribute("jenisServiceMotor", new JenisServiceMotor());
        model.addAttribute("customerList", customerList);
        model.addAttribute("produkList", produkList);
        model.addAttribute("mekanikList", mekanikList); // Tambahkan mekanik ke model

        return "service";
    }

    @PostMapping("/save")
    public String saveJenisService(
            @ModelAttribute JenisServiceMotor jenisServiceMotor,
            @RequestParam("customerId") Long customerId,
            @RequestParam("jenisService") String jenisService,
            @RequestParam("hargaService") Double hargaService,
            @RequestParam(value = "produk", required = false) List<String> produk,
            @RequestParam(value = "jumlahBarang", required = false) List<Integer> jumlahBarang,
            @RequestParam("mekanikId") Long mekanikId,
            @RequestParam("tanggalPengerjaan") String tanggalPengerjaan,
            RedirectAttributes redirectAttributes) {

        try {
            // Ambil data customer dan mekanik
            Customer customer = customerService.getCustomerById(customerId);
            Mekanik mekanik = mekanikService.getMekanikById(mekanikId);

            if (customer == null || mekanik == null) {
                throw new IllegalArgumentException("Data tidak ditemukan.");
            }

            jenisServiceMotor.setCustomer(customer);
            jenisServiceMotor.setMekanik(mekanik);
            jenisServiceMotor.setNamaService(jenisService);
            jenisServiceMotor.setHarga(hargaService);
            jenisServiceMotor.setTanggalPengerjaan(LocalDate.parse(tanggalPengerjaan));

            double totalHarga = hargaService;

            // Menangani produk dan jumlah barang
            if (produk != null && jumlahBarang != null) {
                for (int i = 0; i < produk.size(); i++) {
                    String namaProduk = produk.get(i);
                    Integer jumlah = jumlahBarang.get(i);

                    if (jumlah == null || jumlah <= 0) {
                        throw new IllegalArgumentException("Jumlah barang harus lebih dari 0.");
                    }

                    PenjualanProduk produkObj = penjualanProdukService.getProdukByName(namaProduk);

                    if (produkObj == null) {
                        throw new IllegalArgumentException("Produk tidak ditemukan.");
                    }

                    // Masukkan produk dan jumlah ke dalam produkTerpakai (Map)
                    jenisServiceMotor.getProdukTerpakai().put(produkObj, jumlah);

                    // Kurangi stok produk
                    penjualanProdukService.kurangiStokProduk(namaProduk, jumlah);

                    // Hitung total biaya
                    totalHarga += produkObj.getHarga() * jumlah;
                }
            }

            jenisServiceMotor.setTotalBiaya(totalHarga);

            // Simpan ke database
            service.saveJenisService(jenisServiceMotor);

            redirectAttributes.addFlashAttribute("success", "Data layanan berhasil disimpan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menyimpan data layanan: " + e.getMessage());
        }

        return "redirect:/jenis-service/all";
    }
}