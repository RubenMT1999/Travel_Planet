package com.example.booking.services;

import com.example.booking.models.Hotel;
import com.example.booking.repository.RegistroHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelRegistroService {

    @Autowired
    private RegistroHotelRepository registroHotelRepository;

    public void guardarRegistroHotel(Hotel hotel){
        registroHotelRepository.save(hotel);
    }

//    public String hotelYaRegistrado(Hotel hotel){
//        if (hotel.getNombre() == registroHotelRepository.getHotelByNombre()){
//
//        }
//        return "Este hotel ya existe";
//    }

}
