package com.example.booking.services;

import com.example.booking.models.Hotel;
import com.example.booking.repository.DetalleHotelRepository;
import com.example.booking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DetalleHotelService {
    @Autowired
    private DetalleHotelRepository detallehotel;

    public Hotel buscarporid(Integer id) {
        Hotel hotel = detallehotel.buscarporid(id);
        return hotel;
    }
}

