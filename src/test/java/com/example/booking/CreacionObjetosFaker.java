package com.example.booking;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.repository.HotelRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Component
public class CreacionObjetosFaker {


//    @Autowired
//    HotelRepository hotelRepository;
//
//
//    Faker faker = new Faker();
//
//
//    public List<Habitacion> fakerHabitaciones(Integer numero, Integer idHotel){
//
//        Hotel hotel = hotelRepository.findById(idHotel).get();
//
//        List<Habitacion> misHabitaciones = null;
//
//        for (int i=0; i<numero; i++){
//
//            Habitacion habitacion = new Habitacion();
//            habitacion.setId(i);
//            habitacion.setHotel(hotel);
//            habitacion.setNumeroHabitacion(faker.number().numberBetween(1,500));
//            habitacion.setExtensionTelefonica(valueOf(faker.number().numberBetween(100,999)));
//            habitacion.setCapacidad(faker.number().numberBetween(1, 9));
//            habitacion.setDescripcion(faker.lorem().fixedString(100));
//            habitacion.setDisponibilidad(faker.bool().bool());
//            habitacion.setImagen("https://www.cataloniahotels.com/es/blog/wp-content/uploads/2016/05/habitaci%C3%B3n-doble-catalonia-620x412.jpg");
//            habitacion.setCajaFuerte(faker.bool().bool());
//            habitacion.setCocina(faker.bool().bool());
//            habitacion.setBanioPrivado(faker.bool().bool());
//            habitacion.setAireAcondicionado(faker.bool().bool());
//            habitacion.setTv(faker.bool().bool());
//            habitacion.setTerraza(faker.bool().bool());
//            habitacion.setWifi(faker.bool().bool());
//            habitacion.setPrecioBase(faker.number().numberBetween(50,400));
//            misHabitaciones.add(habitacion);
//            hotel.setHabitaciones(habitaciones);
//
//        }
//
//
//    }


}
