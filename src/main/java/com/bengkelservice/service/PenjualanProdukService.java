package com.bengkelservice.service;

import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.repository.PenjualanProdukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PenjualanProdukService {

    @Autowired
    private PenjualanProdukRepository penjualanProdukRepository;

    // Define the folder where images are stored
    private static final String UPLOAD_DIR = "src/main/resources/static/images/uploads"; // Folder for storing images

    // Get all products
    public List<PenjualanProduk> getAllProduk() {
        return penjualanProdukRepository.findAll(); // Fetch all products from the database
    }

    // Save or update a product
    public void saveProduk(PenjualanProduk penjualanProduk) {
        penjualanProdukRepository.save(penjualanProduk); // Save or update the product in the database
    }

    // Get a product by ID
    public PenjualanProduk getProdukById(Long id) {
        return penjualanProdukRepository.findById(id).orElse(null); // Find a product by ID
    }

    // Delete a product by ID, and remove the associated image file
    public void deleteProduk(Long id) {
        // Fetch the product to get the filename
        PenjualanProduk produk = penjualanProdukRepository.findById(id).orElse(null);
        if (produk != null && produk.getFoto() != null) {
            String fileName = produk.getFoto(); // Get the filename of the image
            deleteFile(fileName); // Call the deleteFile method to delete the image from the uploads folder
        }

        // Now delete the product from the database
        penjualanProdukRepository.deleteById(id); // Delete the product from the database
    }

    // Method to delete the file from the uploads folder
    private void deleteFile(String fileName) {
        // Define the path to the uploads folder
        Path filePath = Paths.get(UPLOAD_DIR + "/" + fileName);

        try {
            // Delete the file if it exists
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            // You may want to log the error here
        }
    }

    // Search products based on the search query (name, description, etc.)
    public List<PenjualanProduk> searchProduk(String searchQuery) {
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // Example of searching by name (you can customize this to search other fields)
            return penjualanProdukRepository.findByNamaContainingIgnoreCase(searchQuery); // Case-insensitive search
        }
        return getAllProduk(); // Return all products if no search query is provided
    }
}
