package com.bengkelservice.controller;

import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.service.PenjualanProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
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
//        model.addAttribute("produk", new PenjualanProduk());
        return "penjualan";
    }

    @PostMapping("/save")
    public String saveProduk(
            @Valid @ModelAttribute PenjualanProduk penjualanProduk,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/penjualan/all"; // Kembali ke halaman jika ada error
        }

        try {
            MultipartFile fotoProduk = penjualanProduk.getFoto();
            if (fotoProduk != null && !fotoProduk.isEmpty()) {
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
            redirectAttributes .addFlashAttribute("error", "Gagal menyimpan foto produk!");
        }

        return "redirect:/penjualan/all";
    }
}