package com.example.booking.services;

import com.example.booking.models.Hotel;
import com.example.booking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotel;
    public List<Hotel> Buscar(String ciudades) {
        return hotel.findAll();

            }

}



