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
    public List<Hotel> listAll(String ciudades, Date fechaInicio, Date fechaFin, Integer capacidad) {

                if (ciudades != null && fechaInicio != null && fechaFin != null && capacidad != null) {
                    return hotel.search(ciudades, fechaInicio, fechaFin, capacidad);
                }

        return hotel.findAll();
            }


    public List<Hotel> obtenerHoteles(String ciudad){
        return hotel.buscarHoteles(ciudad);
    }

}



