package com.bengkelservice.controller;

import com.bengkelservice.model.Layanan;
import com.bengkelservice.model.LayananProduk;
import com.bengkelservice.model.Customer;
import com.bengkelservice.model.Mekanik;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bengkelservice.service.PdfService;
import com.bengkelservice.service.CustomerService;
import com.bengkelservice.service.MekanikService;
import com.bengkelservice.service.PenjualanProdukService;
import com.bengkelservice.service.LayananService;
import com.bengkelservice.service.LayananProdukService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfService pdfService;
    private final CustomerService customerService;
    private final MekanikService mekanikService;
    private final PenjualanProdukService penjualanProdukService;
    private final LayananService layananService;
    private final LayananProdukService layananProdukService;

    @Autowired
    public PdfController(PdfService pdfService, CustomerService customerService,
                         MekanikService mekanikService, PenjualanProdukService penjualanProdukService,
                         LayananService layananService, LayananProdukService layananProdukService) {
        this.pdfService = pdfService;
        this.customerService = customerService;
        this.mekanikService = mekanikService;
        this.penjualanProdukService = penjualanProdukService;
        this.layananService = layananService;
        this.layananProdukService = layananProdukService;
    }

    @GetMapping("/pelanggan")
    public ResponseEntity<byte[]> generatePelangganPdf() {
        String title = "Laporan Data Pelanggan";
        List<String> headers = Arrays.asList("ID", "Nama", "Alamat", "No. Telepon");

        // Mengambil data pelanggan dari database
        List<List<String>> data = customerService.getAllCustomer().stream()
                .map(customer -> Arrays.asList(
                        String.valueOf(customer.getId()),
                        customer.getNama(),
                        customer.getAlamat(),
                        customer.getTelepon()
                ))
                .toList();

        return generatePdfResponse(title, headers, data, "Data-Pelanggan.pdf");
    }

    @GetMapping("/mekanik")
    public ResponseEntity<byte[]> generateMekanikPdf() {
        String title = "Laporan Data Mekanik";
        List<String> headers = Arrays.asList("ID", "Nama", "Spesialis", "Nomor Telepon");

        // Mengambil data mekanik dari database
        List<List<String>> data = mekanikService.getAllMekanik().stream()
                .map(mekanik -> Arrays.asList(
                        String.valueOf(mekanik.getId()),
                        mekanik.getNama(),
                        mekanik.getKeahlian(),
                        mekanik.getNomorTelepon()
                ))
                .toList();

        return generatePdfResponse(title, headers, data, "Data-Mekanik.pdf");
    }

    @GetMapping("/sparepart")
    public ResponseEntity<byte[]> generateSparepartPdf() {
        String title = "Laporan Data Sparepart";
        List<String> headers = Arrays.asList("ID", "Nama", "Harga", "Stok");

        // Mengambil data sparepart dari database
        List<List<String>> data = penjualanProdukService.getAllProduk().stream()
                .map(produk -> Arrays.asList(
                        String.valueOf(produk.getId()),
                        produk.getNama(),
                        String.valueOf(produk.getHarga()),
                        String.valueOf(produk.getStok())
                ))
                .toList();

        return generatePdfResponse(title, headers, data, "Data-Sparepart.pdf");
    }

    @GetMapping("/layanan")
    public ResponseEntity<byte[]> generateLayananPdf(@RequestParam String startDate, @RequestParam String endDate) {
        String title = "Laporan Layanan (" + startDate + " - " + endDate + ")";
        List<String> headers = Arrays.asList("ID", "Nama Customer", "Jenis Layanan", "Produk yang Dibeli", "Mekanik", "Total Biaya", "Tanggal");

        // Konversi String ke LocalDate
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // Ambil data layanan dari database berdasarkan rentang tanggal
        List<Layanan> layananList = layananService.findByTanggalBetween(start, end);

        // Konversi data ke List<List<String>>
        List<List<String>> data = layananList.stream()
                .map(layanan -> Arrays.asList(
                        layanan.getId().toString(),
                        layanan.getCustomer().getNama(),
                        layanan.getJenisLayanan(),
                        getProdukYangDibeli(layanan),
                        layanan.getCustomer().getMekanik() != null ? layanan.getCustomer().getMekanik().getNama() : "-", // Ambil mekanik dari customer
                        layananService.formatCurrency(layanan.getTotalBiaya()),
                        layanan.getTanggal().toString()
                ))
                .collect(Collectors.toList());

        return generatePdfResponse(title, headers, data, "Laporan-Service.pdf");
    }

    private String getProdukYangDibeli(Layanan layanan) {
        List<LayananProduk> layananProdukList = layananProdukService.getByLayanan(layanan);
        return layananProdukList.stream()
                .map(lp -> lp.getProduk().getNama() + " (" + lp.getJumlah() + ")")
                .reduce((p1, p2) -> p1 + ", " + p2) // Gabungkan produk yang dibeli
                .orElse("-");
    }

    private ResponseEntity<byte[]> generatePdfResponse(String title, List<String> headers, List<List<String>> data, String filename) {
        try {
            byte[] pdfBytes = pdfService.generatePdf(title, headers, data);
            HttpHeaders headersResponse = new HttpHeaders();
            headersResponse.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
            return new ResponseEntity<>(pdfBytes, headersResponse, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
