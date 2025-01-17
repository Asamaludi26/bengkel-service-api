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

    private static final String UPLOAD_DIR = "src/main/resources/static/images/uploads"; // Folder for storing images

    @Autowired
    private PenjualanProdukService penjualanProdukService;

    // Show all products
    @GetMapping("/all")
    public String getAllProduk(Model model) {
        List<PenjualanProduk> produkList = penjualanProdukService.getAllProduk();
        model.addAttribute("produkList", produkList);
        model.addAttribute("penjualanProduk", new PenjualanProduk()); // For creating new product
        return "penjualan"; // Page for displaying the list of products
    }

    // Save or update a product
    @PostMapping("/save")
    public String saveProduk(
            @Valid @ModelAttribute PenjualanProduk penjualanProduk,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please check your input.");
            return "redirect:/penjualan/all";
        }

        try {
            // Handle file upload
            MultipartFile fotoFile = penjualanProduk.getFotoFile();

            if (fotoFile != null && !fotoFile.isEmpty()) {
                // Create folder if it doesn't exist
                Path folderPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(folderPath);

                // Generate a unique filename for the uploaded image
                String fileName = System.currentTimeMillis() + "_" + fotoFile.getOriginalFilename();
                Path filePath = folderPath.resolve(fileName);

                // Save the file
                fotoFile.transferTo(filePath);

                // Save the file name in the database
                penjualanProduk.setFoto(fileName);
            }

            // Save the product to the database
            penjualanProdukService.saveProduk(penjualanProduk);
            redirectAttributes.addFlashAttribute("success", "Product saved successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to save the product image.");
        }

        return "redirect:/penjualan/all"; // Redirect to product list after saving
    }

    // Edit product by ID
    @GetMapping("/edit/{id}")
    public String editProduk(@PathVariable("id") Long id, Model model) {
        PenjualanProduk penjualanProduk = penjualanProdukService.getProdukById(id);
        if (penjualanProduk != null) {
            model.addAttribute("penjualanProduk", penjualanProduk);
            return "penjualan"; // Use the same page to edit the product
        } else {
            return "redirect:/penjualan/all"; // Redirect to product list if not found
        }
    }

    // Delete product by ID
    @GetMapping("/delete/{id}")
    public String deleteProduk(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            penjualanProdukService.deleteProduk(id);
            redirectAttributes.addFlashAttribute("success", "Product deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete the product.");
        }
        return "redirect:/penjualan/all"; // Redirect to product list after deletion
    }

}
