package com.example.booking.services;

import com.example.booking.models.Habitacion;
import com.example.booking.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HabitacionService {

    @Autowired
    HabitacionRepository habitacionRepository;



    public List<Habitacion> listarHabitaciones(Integer id){
        return habitacionRepository.listarHabitaciones(id);
    }

    public void guardarHabitacion(Habitacion habitacion){
        habitacionRepository.save(habitacion);
    }



    public List<Habitacion> buscarporoidHabitacion(Integer id_hotel, Integer capacidad, Date fecha_inicio, Date fecha_fin) {
        List<Habitacion> habitacion = habitacionRepository.buscarporidhab(id_hotel, capacidad, fecha_inicio, fecha_fin);
        return habitacion;

    }


}
