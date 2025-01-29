package com.bengkelservice.service;

import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.repository.PenjualanProdukRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PenjualanProdukService {

    private static final Logger logger = LoggerFactory.getLogger(PenjualanProdukService.class);

    @Autowired
    private PenjualanProdukRepository penjualanProdukRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public void uploadFile(MultipartFile file) {
        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File destinationFile = new File(uploadDir, file.getOriginalFilename());
            file.transferTo(destinationFile);
        } catch (IOException e) {
            logger.error("Failed to upload file: {}", e.getMessage());
        }
    }

    // Get all products
    public List<PenjualanProduk> getAllProduk() {
        return penjualanProdukRepository.findAll();
    }

    // Save or update a product
    public void saveProduk(PenjualanProduk penjualanProduk) {
        penjualanProdukRepository.save(penjualanProduk);
    }

    // Get a product by ID
    public PenjualanProduk getProdukById(Long id) {
        return penjualanProdukRepository.findById(id).orElse(null);
    }

    // Delete a product by ID
    public void deleteProduk(Long id) {
        PenjualanProduk produk = penjualanProdukRepository.findById(id).orElse(null);
        if (produk != null && produk.getFoto() != null) {
            deleteFile(produk.getFoto());
        }
        penjualanProdukRepository.deleteById(id);
    }

    private void deleteFile(String fileName) {
        Path filePath = Paths.get(uploadDir, fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            logger.error("Failed to delete file: {}", e.getMessage());
        }
    }

    // Search products
    public List<PenjualanProduk> searchProduk(String searchQuery) {
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            return penjualanProdukRepository.findByNamaContainingIgnoreCase(searchQuery);
        }
        return getAllProduk();
    }

    // Get harga produk berdasarkan ID
    public int getHarga(Long produkId) {
        PenjualanProduk produk = getProdukById(produkId);
        return produk != null ? produk.getHarga() : 0;
    }
}