package com.bengkelservice.service;

import com.bengkelservice.model.Layanan;
import com.bengkelservice.repository.LayananRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LayananService {

    @Autowired
    private LayananRepository layananRepository;

    public void saveLayanan(List<Layanan> layananList) {
        layananRepository.saveAll(layananList);
    }
}

