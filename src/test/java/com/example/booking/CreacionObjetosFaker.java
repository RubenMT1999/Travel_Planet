package com.example.booking;

import com.example.booking.models.*;
import com.example.booking.repository.HotelRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.PrePersist;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class CreacionObjetosFaker {

    Faker faker = new Faker();

    public List<Habitacion> fakerHabitaciones(Integer numero){
        List<Habitacion> misHabitaciones = new ArrayList<>();
        Hotel hotel = new Hotel();
        for (int i=0; i<numero; i++){
            Habitacion habitacion = new Habitacion();
            habitacion.setId(faker.number().numberBetween(100,999));
            habitacion.setHotel(hotel);
            habitacion.setNumeroHabitacion(faker.number().numberBetween(1,999));
            habitacion.setExtensionTelefonica(String.valueOf(faker.number().numberBetween(100,999)));
            habitacion.setCapacidad(faker.number().numberBetween(1, 9));
            habitacion.setDescripcion(faker.lorem().fixedString(100));
            habitacion.setDisponibilidad(faker.bool().bool());
            habitacion.setImagen("https://www.cataloniahotels.com/es/blog/wp-content/uploads/2016/05/habitaci%C3%B3n-doble-catalonia-620x412.jpg");
            habitacion.setCajaFuerte(faker.bool().bool());
            habitacion.setCocina(faker.bool().bool());
            habitacion.setBanioPrivado(faker.bool().bool());
            habitacion.setAireAcondicionado(faker.bool().bool());
            habitacion.setTv(faker.bool().bool());
            habitacion.setTerraza(faker.bool().bool());
            habitacion.setWifi(faker.bool().bool());
            habitacion.setPrecioBase((double) faker.number().numberBetween(50, 400));
            misHabitaciones.add(habitacion);
            hotel.setHabitaciones(misHabitaciones);

        }

        return misHabitaciones;
    }


    public List<Reserva> fakerReservas(Integer numero){
        List<Reserva> misReservas = new ArrayList<>();
        Habitacion habitacion = new Habitacion();
        Usuario usuario = new Usuario();
        for (int i=0;i<=numero;i++){
            Reserva reserva = new Reserva();
            reserva.setId(faker.number().numberBetween(100,999));
            reserva.setHabitacion(habitacion);
            reserva.setUsuario(usuario);
            reserva.setPagado(faker.bool().bool());
            reserva.setFechaFin(faker.date().future(2, TimeUnit.DAYS));
            reserva.setFechaInicio(faker.date().past(2,TimeUnit.DAYS));
            reserva.setPrecio_total((double) faker.number().numberBetween(50, 400));
            misReservas.add(reserva);
            habitacion.setReserva(new HashSet<>(misReservas));
            usuario.setReservas(new HashSet<>(misReservas));
        }
        return misReservas;
    }


    public List<Tarifa> fakerTarifa(Integer numero){
        List<Tarifa> misTarifas = new ArrayList<>();
        Hotel hotel = new Hotel();
        for (int i=0;i<=numero;i++){
            Tarifa tarifa = new Tarifa();
            tarifa.setPrecioAire((double) faker.number().numberBetween(2, 20));
            tarifa.setHotel(hotel);
            tarifa.setId(faker.number().numberBetween(100,999));
            tarifa.setPrecioCocina((double) faker.number().numberBetween(2, 20));
            tarifa.setPrecioBanio((double) faker.number().numberBetween(2, 20));
            tarifa.setPrecioTV((double) faker.number().numberBetween(2, 20));
            tarifa.setPrecioTerraza((double) faker.number().numberBetween(2, 20));
            tarifa.setPrecioWifi((double) faker.number().numberBetween(2, 20));
            tarifa.setPrecioCajaFuerte((double) faker.number().numberBetween(2, 20));
            tarifa.setTemporada(Temporada.Media);
            tarifa.setPensionHoteles(null);
            tarifa.setTemporadaHoteles(null);
            misTarifas.add(tarifa);
            hotel.setTarifa(tarifa);
        }
        return misTarifas;
    }




    public List<Hotel> fakerHotel(Integer numero, Integer id_usuario) {
        Usuario usuario = new Usuario();
        List<Hotel> hotellista = new ArrayList<>();

        for (int i=0; i<numero; i++){
            Hotel hotelfaker = new Hotel();
            hotelfaker.setId(numero);
            hotelfaker.setCiudad("Sevilla");
            hotelfaker.setNombre("Alameda");
            hotelfaker.setPrecio(faker.number().digit());
            hotelfaker.setComentario(faker.lorem().characters());
            hotelfaker.setImagen("https://www.cataloniahotels.com/es/blog/wp-content/uploads/2016/05/habitaci%C3%B3n-doble-catalonia-620x412.jpg");
            hotelfaker.setLugar("Sevilla");
            hotelfaker.setTelefono("123456789");
            hotelfaker.setCif("123");
            hotelfaker.setNumero_habitaciones(faker.number().numberBetween(1, 20));
            hotelfaker.setLocalidad("Sevilla");
            hotelfaker.setPais("España");
            hotelfaker.setEstrellas(faker.number().numberBetween(1, 5));
            hotelfaker.setUsuario(usuario);
            hotellista.add(hotelfaker);
        }

        return hotellista;
    }


}






