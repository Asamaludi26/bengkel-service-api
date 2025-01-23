package com.bengkelservice.controller;

import com.bengkelservice.model.Mekanik;
import com.bengkelservice.service.MekanikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mekanik") // Base URL untuk Mekanik
public class MekanikController {

    @Autowired
    private MekanikService mekanikService;

    // Menampilkan halaman semua mekanik
    @GetMapping("/all")
    public String getAllMekanik(Model model) {
        List<Mekanik> mekaniklist = mekanikService.getAllMekanik();
        model.addAttribute("mekaniklist", mekaniklist);
        model.addAttribute("mekanik", new Mekanik()); // Untuk form tambah/edit
        return "mekanik"; // Menampilkan mekanik.html
    }

    // Menyimpan data mekanik baru atau update
    @PostMapping("/save")
    public String saveMekanik(@ModelAttribute Mekanik mekanik) {
        mekanikService.addMekanik(mekanik);
        return "redirect:/mekanik/all"; // Kembali ke halaman daftar
    }

    // Menghapus mekanik berdasarkan ID
    @GetMapping("/delete/{id}")
    public String deleteMekanik(@PathVariable Long id) {
        mekanikService.deleteMekanik(id);
        return "redirect:/mekanik/all"; // Kembali ke halaman daftar
    }

    @GetMapping("/search")
    public String searchMekanik(@RequestParam(value = "searchQuery", required = false) String searchQuery, Model model) {
        List<Mekanik> mekanik = mekanikService.searchMekanik(searchQuery);
        model.addAttribute("mekaniklist", mekanik);

        // Tambahkan objek mekanik kosong untuk form input
        model.addAttribute("mekanik", new Mekanik());

        return "mekanik"; // Nama template Thymeleaf
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Mekanik> mekanik = mekanikService.findById(id);
        if (mekanik.isPresent()) {
            List<Mekanik> mekaniklist = mekanikService.getAllMekanik();
            model.addAttribute("mekaniklist", mekaniklist);
            model.addAttribute("mekanik", mekanik.get()); // Mengisi model dengan data mekanik yang akan diedit
            return "mekanik"; // Mengembalikan nama template untuk form edit
        }
        return "redirect:/mekanik/all"; // Kembali ke halaman daftar jika tidak ditemukan
    }
}
