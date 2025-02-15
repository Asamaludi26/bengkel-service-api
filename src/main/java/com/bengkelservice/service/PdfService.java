package com.bengkelservice.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.properties.TextAlignment;

import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    public byte[] generatePdf(String title, List<String> headers, List<List<String>> data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // ==============================
        // 1️⃣ Menambahkan Logo Bengkel
        // ==============================
        String logoPath = "src/main/resources/static/images/logo_bengkel4.png"; // Sesuaikan path logo
        ImageData imageData = ImageDataFactory.create(logoPath);
        Image logo = new Image(imageData);
        logo.setWidth(120); // Sesuaikan ukuran logo
        logo.setHeight(70);
        logo.setFixedPosition(430, 760); // Pojok kanan atas (X, Y)

        document.add(logo);

        // ==============================
        // 2️⃣ Kop Perusahaan
        // ==============================
        document.add(new Paragraph("BENGKEL SAHABAT MOTOR").setBold().setFontSize(24));
        document.add(new Paragraph("Jl. Meruya Ilir Raya No.14, RT.1/RW.1, Srengseng, Jakarta Barat, Jakarta 11630").setFontSize(10));
        document.add(new Paragraph("______________________________________________________________________________"));
        document.add(new Paragraph("\n"));

        // ==============================
        // 3️⃣ Judul Laporan
        // ==============================
        document.add(new Paragraph(title).setBold().setFontSize(14));

        // ==============================
        // 4️⃣ Tabel Data
        // ==============================
        Table table = new Table(headers.size());
        table.setWidth(UnitValue.createPercentValue(100)); // Sesuaikan tabel dengan lebar halaman

        // Tambahkan header dengan ukuran font lebih kecil
        headers.forEach(header ->
                table.addCell(new Cell().add(new Paragraph(header).setBold().setFontSize(10)))
        );

            // Tambahkan data dengan ukuran font lebih kecil
        for (List<String> row : data) {
            row.forEach(cell ->
                    table.addCell(new Cell().add(new Paragraph(cell).setFontSize(9))) // Ukuran font untuk isi tabel
            );
        }

        document.add(table);
        document.add(new Paragraph("\n"));

        // ==============================
        // 5️⃣ Menambahkan Tanggal Cetak & Tanda Tangan
        // ==============================
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));

        // Membuat tabel tanda tangan dengan 2 kolom (Kepala Bengkel & Admin)
        Table tandaTanganTable = new Table(2);
        tandaTanganTable.setWidth(UnitValue.createPercentValue(100));

        // Baris pertama: "Mengetahui," di atas tanda tangan Kepala Bengkel
        tandaTanganTable.addCell(new Cell().add(new Paragraph("Mengetahui,").setTextAlignment(TextAlignment.CENTER)).setBorder(null));
        tandaTanganTable.addCell(new Cell().add(new Paragraph("Jakarta, " + todayDate).setTextAlignment(TextAlignment.CENTER)).setBorder(null));

        // Baris kedua: Jabatan
        tandaTanganTable.addCell(new Cell().add(new Paragraph("Kepala Bengkel").setBold().setTextAlignment(TextAlignment.CENTER)).setBorder(null));
        tandaTanganTable.addCell(new Cell().add(new Paragraph("Admin").setBold().setTextAlignment(TextAlignment.CENTER)).setBorder(null));

        // Baris ketiga: Garis tanda tangan
        tandaTanganTable.addCell(new Cell().add(new Paragraph("\n\n\n\n_________________").setTextAlignment(TextAlignment.CENTER)).setBorder(null));
        tandaTanganTable.addCell(new Cell().add(new Paragraph("\n\n\n\n_________________").setTextAlignment(TextAlignment.CENTER)).setBorder(null));

        // Baris keempat: Nama
        tandaTanganTable.addCell(new Cell().add(new Paragraph("Edy Pamungkas").setTextAlignment(TextAlignment.CENTER)).setBorder(null));
        tandaTanganTable.addCell(new Cell().add(new Paragraph("Rina Safitri").setTextAlignment(TextAlignment.CENTER)).setBorder(null));

        document.add(tandaTanganTable);

        // ==============================
        // 6️⃣ Selesai
        // ==============================
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
