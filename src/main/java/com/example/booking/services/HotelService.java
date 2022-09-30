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
    private List<Hotel> hotelvacio;
    public List<Hotel> listAll(String ciudades, Date fechaInicio, Date fechaFin, Integer capacidad) {

                if (ciudades != null && fechaInicio != null && fechaFin != null && capacidad != null) {
                    return hotel.search(ciudades, fechaInicio, fechaFin, capacidad);
                }
                return hotelvacio;
            }

}



