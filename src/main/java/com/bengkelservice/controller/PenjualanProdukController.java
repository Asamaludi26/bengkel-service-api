package com.bengkelservice.controller;

import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.service.PenjualanProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/penjualan")
public class PenjualanProdukController {

    // Define the folder where images will be uploaded (inside static folder)
    @Value("${upload.dir}") // Use a property from application.properties
    private String uploadDir;

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
            return "penjualan"; // Return to the same page to show errors
        }

        try {
            // Handle file upload
            MultipartFile fotoFile = penjualanProduk.getFotoFile();

            if (fotoFile != null && !fotoFile.isEmpty()) {
                // Create folder if it doesn't exist
                Path folderPath = Paths.get(uploadDir);
                Files.createDirectories(folderPath);

                // Generate a unique filename for the uploaded image
                String fileName = System.currentTimeMillis() + "_" + fotoFile.getOriginalFilename();
                Path filePath = folderPath.resolve(fileName);

                // Save the file
                fotoFile.transferTo(filePath);

                // Save the file name (only the file name, not the full path) in the database
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
    public String deleteProduk(@ PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // Call the service method to delete the product and associated image
            PenjualanProduk penjualanProduk = penjualanProdukService.getProdukById(id);
            if (penjualanProduk != null && penjualanProduk.getFoto() != null) {
                // Delete the associated image file
                File file = new File(uploadDir, penjualanProduk.getFoto());
                if (file.exists()) {
                    file.delete();
                }
            }
            penjualanProdukService.deleteProduk(id);
            redirectAttributes.addFlashAttribute("success", "Product deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete the product.");
        }
        return "redirect:/penjualan/all"; // Redirect to product list after deletion
    }

    @GetMapping("/search")
    public String searchProduk(@RequestParam(value = "searchQuery", required = false) String searchQuery, Model model) {
        List<PenjualanProduk> produkList;

        // If searchQuery is not null or empty, perform the search
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            produkList = penjualanProdukService.searchProduk(searchQuery); // Perform search
        } else {
            produkList = penjualanProdukService.getAllProduk(); // Fetch all products if no search term
        }

        // Add the search results to the model
        model.addAttribute("produkList", produkList);  // Corrected model attribute name
        model.addAttribute("penjualanProduk", new PenjualanProduk()); // Empty form for creating new products

        return "penjualan"; // Thymeleaf template name
    }
}