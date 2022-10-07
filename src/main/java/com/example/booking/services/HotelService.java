package com.example.booking.services;

import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotel;
    public List<Hotel> Buscar(String ciudades, Date fecha_inicio, Date fecha_fin) {
        return hotel.findHotelsByCiudadLike(ciudades, fecha_inicio, fecha_fin);
            }
 /* public Reserva search(Date fecha_inicio, Date fecha_fin){
        return hotel.buscar(fecha_inicio, fecha_fin);
   }

  */




}




