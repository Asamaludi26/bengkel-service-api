package com.bengkelservice.controller;

import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.service.PenjualanProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/penjualan")
public class PenjualanProdukController {

    private static final String UPLOAD_DIR = "src/main/resources/static/images/uploads";

    @Autowired
    private PenjualanProdukService penjualanProdukService;

    @GetMapping("/all")
    public String getAllProduk(Model model) {
        List<PenjualanProduk> produkList = penjualanProdukService.getAllProduk();
        model.addAttribute("produkList", produkList);
        model.addAttribute("produk", new PenjualanProduk());
        return "penjualan";
    }

    @PostMapping("/save")
    public String saveProduk(
            @ModelAttribute PenjualanProduk penjualanProduk,
            @RequestParam("fotoProduk") MultipartFile fotoProduk,
            RedirectAttributes redirectAttributes) {
        try {
            if (!fotoProduk.isEmpty()) {
                // Tentukan folder penyimpanan
                Path folderPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(folderPath);

                // Tentukan nama file unik
                String fileName = System.currentTimeMillis() + "_" + fotoProduk.getOriginalFilename();
                Path filePath = folderPath.resolve(fileName);

                // Simpan file ke lokasi penyimpanan
                Files.write(filePath, fotoProduk.getBytes());

                // Set nama file ke properti fotoProduk
                penjualanProduk.setFotoProduk(fileName);
            }

            // Simpan produk ke database
            penjualanProdukService.saveProduk(penjualanProduk);
            redirectAttributes.addFlashAttribute("success", "Produk berhasil disimpan!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Gagal menyimpan file!");
        }
        return "redirect:/penjualan";
    }

    @GetMapping("/edit/{id}")
    public String editProduk(@PathVariable Long id, Model model) {
        PenjualanProduk produk = penjualanProdukService.getProdukById(id);
        model.addAttribute("produk", produk);
        List<PenjualanProduk> produkList = penjualanProdukService.getAllProduk();
        model.addAttribute("produkList", produkList);
        return "penjualan";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduk(@PathVariable Long id) {
        penjualanProdukService.deleteProduk(id);
        return "redirect:/penjualan/all";
    }

    @GetMapping("/search")
    public String searchProduk(@RequestParam(value = "searchQuery", required = false) String searchQuery, Model model) {
        List<PenjualanProduk> produkList = penjualanProdukService.searchProduk(searchQuery);
        model.addAttribute("produkList", produkList);
        model.addAttribute("produk", new PenjualanProduk());
        return "penjualan";
    }
}