package com.example.booking.services;

import com.example.booking.repository.IPensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PensionService {

    @Autowired
    IPensionRepository pensionRepository;


    public Double precioPension(Integer pension,Integer idTarifa){
        return pensionRepository.precioPension(pension,idTarifa);
    }



}
