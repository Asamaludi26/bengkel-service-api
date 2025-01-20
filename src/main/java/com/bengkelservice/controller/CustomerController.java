package com.bengkelservice.controller;

import com.bengkelservice.model.Customer;
import com.bengkelservice.model.Mekanik;
import com.bengkelservice.service.CustomerService;
import com.bengkelservice.service.MekanikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MekanikService mekanikService;

    // Menampilkan halaman semua customer
    @GetMapping("/all")
    public String getAllCustomer(Model model) {
        List<Customer> customerList = customerService.getAllCustomer();
        List<Mekanik> mekanikList = mekanikService.getAllMekanik();

        // Cek apakah semua mekanik sibuk
        boolean allMekanikBusy = mekanikList.stream().allMatch(mekanik -> "Sedang Servis".equals(mekanik.getStatus()));

        model.addAttribute("mekanikList", mekanikList);
        model.addAttribute("customerList", customerList);
        model.addAttribute("customer", new Customer());
        model.addAttribute("allMekanikBusy", allMekanikBusy); // Flag to indicate if all mechanics are busy

        return "customer"; // Render customer.html
    }

    // Menyimpan data customer baru atau update
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        Mekanik mekanik = mekanikService.findById(customer.getMekanikId()).orElse(null); // Menemukan mekanik berdasarkan ID
        customer.setMekanik(mekanik); // Set mekanik pada customer
        customerService.addCustomer(customer); // Menyimpan customer
        return "redirect:/customer/all"; // Kembali ke halaman daftar
    }

    // Menghapus customer berdasarkan ID
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer/all"; // Kembali ke halaman daftar
    }

    // Menampilkan form edit customer
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id)
                .orElse(new Customer()); // Default to new Customer if not found
        List<Mekanik> mekanikList = mekanikService.getAllMekanik();
        boolean allMekanikBusy = mekanikList.stream().allMatch(mekanik -> "Sedang Servis".equals(mekanik.getStatus()));

        model.addAttribute("mekanikList", mekanikList);
        model.addAttribute("customer", customer);
        model.addAttribute("customerList", customerService.getAllCustomer());
        model.addAttribute("allMekanikBusy", allMekanikBusy);
        return "customer"; // Render customer.html
    }

    // Mencari customer berdasarkan nama
    @GetMapping("/search")
    public String searchCustomer(@RequestParam(value = "searchQuery", required = false) String searchQuery, Model model) {
        List<Customer> customerList = searchQuery != null && !searchQuery.isEmpty()
                ? customerService.searchCustomer(searchQuery)
                : customerService.getAllCustomer();

        List<Mekanik> mekanikList = mekanikService.getAllMekanik();
        boolean allMekanikBusy = mekanikList.stream().allMatch(mekanik -> "Sedang Servis".equals(mekanik.getStatus()));
        model.addAttribute("mekanikList", mekanikList); // Kirim mekanik dengan status Available
        model.addAttribute("customerList", customerList);
        model.addAttribute("customer", new Customer());
        model.addAttribute("allMekanikBusy", allMekanikBusy);
        return "customer"; // Render customer.html
    }
}
