package com.example.booking.services;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.repository.DetalleHabitacionRepository;
import com.example.booking.repository.DetalleHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DetalleHabitacionService {
    @Autowired
    private DetalleHabitacionRepository detalleHabitacionRepository;

    public List<Habitacion> buscarporoidHabitacion(Integer id_hotel) {
        List<Habitacion> habitacion = detalleHabitacionRepository.buscarporidhab(id_hotel);
        return habitacion;

    }
}
