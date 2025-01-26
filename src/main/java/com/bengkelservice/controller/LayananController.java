package com.bengkelservice.controller;

import com.bengkelservice.model.Layanan;
import com.bengkelservice.model.Customer;
import com.bengkelservice.service.LayananService;
import com.bengkelservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/layanan")
public class LayananController {

    @Autowired
    private LayananService layananService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public String showForm(Model model) {
        // Retrieve all customers from the database
        List<Customer> customerList = customerService.getAllCustomer();
        model.addAttribute("customerList", customerList);  // Send customerList to the view
        return "layanan";  // Return to the view to render the form
    }

    @PostMapping("/save")
    public String addLayanan(@RequestParam("layananType") String layananType,
                             @RequestParam("customerId") Long customerId,  // Use Long for customerId
                             @RequestParam(value = "layananDetails[]", required = false) String[] layananDetails,
                             Model model) {

        List<Layanan> layananList = new ArrayList<>();
        int totalPrice = 0;

        // Menentukan harga layanan berdasarkan pilihan
        for (String layananDetail : layananDetails) {
            Layanan layanan = new Layanan();
            layanan.setLayananName(layananDetail);
            layanan.setLayananType(layananType);

            int harga = 0;
            switch (layananDetail) {
                case "Pemasangan Aksesoris":
                    harga = 100000;
                    break;
                case "Pengecekan Kelistrikan":
                    harga = 150000;
                    break;
                case "Pengecekan Transmisi":
                    harga = 200000;
                    break;
                case "Pengecekan Rem":
                    harga = 120000;
                    break;
                case "Pengecekan Mesin":
                    harga = 250000;
                    break;
                case "Perawatan Kendaraan":
                    harga = 300000;
                    break;
                case "Pengecekan & Perbaikan Kelistrikan":
                    harga = 500000;
                    break;
                case "Pengecekan & Perbaikan Transmisi":
                    harga = 600000;
                    break;
                case "Pengecekan & Perbaikan Sistem Pengereman":
                    harga = 450000;
                    break;
                case "Pengecekan & Perbaikan Mesin":
                    harga = 800000;
                    break;
            }
            totalPrice += harga;
            layanan.setHarga(harga);  // Set the price for each service
            layananList.add(layanan);
        }

        // Format totalPrice as currency (Rp)
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String formattedTotalPrice = currencyFormat.format(totalPrice);

        // Retrieve the customer by ID
        Customer customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);  // Add customer to model

        layananService.saveLayanan(layananList);  // Save the services

        model.addAttribute("totalPrice", formattedTotalPrice);  // Add total price to the model
        model.addAttribute("layananList", layananList);  // Add layanan list to the model

        return "layanan";  // Render the form again with updated data
    }
}
