package com.bengkelservice.controller;

import com.bengkelservice.model.Mekanik;
import com.bengkelservice.service.MekanikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Menampilkan form edit dengan data mekanik terpilih
    @GetMapping("/edit/{id}")
    public String editMekanik(@PathVariable Long id, Model model) {
        Mekanik mekaniklist = mekanikService.getMekanikById(id);
        model.addAttribute("mekaniklist", mekaniklist); // Data yang di-edit
        List<Mekanik> mekanik = mekanikService.getAllMekanik();
        model.addAttribute("mekanik", mekanik); // Tetap tampilkan daftar
        return "mekanik";
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
        model.addAttribute("mekanik", mekanik);

        // Tambahkan objek mekanik kosong untuk form input
        model.addAttribute("mekanik", new Mekanik());

        return "mekanik"; // Nama template Thymeleaf
    }
}
