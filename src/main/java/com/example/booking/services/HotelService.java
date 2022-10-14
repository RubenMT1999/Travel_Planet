package com.example.booking.services;

import com.example.booking.models.Hotel;
import com.example.booking.repository.DetalleHotelRepository;
import com.example.booking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> Buscar(String ciudades, Date fecha_inicio, Date fecha_fin) {



        return hotelRepository.buscador(ciudades, fecha_inicio, fecha_fin);
            }

   }






    public List<Hotel> verTodosHoteles(){
        Hotel hotelNuevo = new Hotel();
        return hotelRepository.findAll();
    }
    public List<Hotel> hotelPorCiudad(String ciudad) {

        return hotelRepository.hotelesPorCiudad(ciudad);
    }

    public Hotel hotelID(int id){
        return hotelRepository.findById(id).orElse(null);
    }


    public Hotel hotelGuardar(Hotel hotel){
        return hotelRepository.save(hotel);
    }
}




