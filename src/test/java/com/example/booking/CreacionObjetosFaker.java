package com.example.booking;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.models.Usuario;
import com.example.booking.repository.HotelRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.PrePersist;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Component
public class CreacionObjetosFaker {

    Faker faker = new Faker();


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

    public Hotel fakerunhotel() throws ParseException {
        Usuario usuario = new Usuario();
            Hotel hotelfaker = new Hotel();
            hotelfaker.setId(faker.number().numberBetween(1,100));
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
        return hotelfaker;
    }


    public List<Habitacion> fakerHabitaciones(Integer numero, Integer idHotel) throws ParseException {
        List<Habitacion> misHabitaciones = new ArrayList<>();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = formato.parse("2022-11-30");
        Date fecha_Fin = formato.parse("2022-12-11");

       List<Reserva> misReservas = new ArrayList<>();
        for(Reserva r: misReservas){
            r.setFechaFin(fechaInicio);
            r.setFechaFin(fecha_Fin);
            misReservas.add(r);
        }

        for (int i=0; i<numero; i++){
            Hotel hotel = fakerunhotel();
            Habitacion habitacion = new Habitacion();
            habitacion.setId(faker.number().numberBetween(100,999));
            habitacion.setHotel(hotel);
            habitacion.setNumeroHabitacion(faker.number().numberBetween(1,999));
            habitacion.setExtensionTelefonica(String.valueOf(faker.number().numberBetween(100,999)));
            habitacion.setCapacidad(2);
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
            habitacion.setReserva(misReservas);
            misHabitaciones.add(habitacion);
            hotel.setHabitaciones(misHabitaciones);
        }
        return misHabitaciones;
    }

    public List<Reserva> fakerReserva() throws ParseException {
        Reserva reserva = new Reserva();
       Usuario usuario = new Usuario();

        List<Reserva> reservas = new ArrayList<>();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = formato.parse("2022-11-30");
        Date fecha_Fin = formato.parse("2022-12-11");

        List<Habitacion> habitacionCreada = fakerHabitaciones(10,10);
        for (Habitacion h: habitacionCreada){
            reserva.setId(faker.number().numberBetween(1,100));
            reserva.setUsuario(usuario);
            reserva.setHabitacion(h);
            reserva.setFechaInicio(fechaInicio);
            reserva.setFechaFin(fecha_Fin);
            reserva.setPagado(faker.bool().bool());
            reserva.setPrecio_total(faker.number().randomDouble(1,40,200));
            reservas.add(reserva);
        }
        return reservas;
    }


    public List<Hotel> fakerHotelBusqueda() throws ParseException {
//        List<Hotel> hotelesCreado = fakerHotel(10, 10);
//        List<Habitacion> habitacionCreado = fakerHabitaciones(10,10);
        List<Hotel> hoteles = new ArrayList<>();
        List<Reserva> reservaCreada = fakerReserva();
        for(Reserva r: reservaCreada){
            Habitacion habitacion = r.getHabitacion();
            habitacion.setReserva(reservaCreada);
            Hotel hotel = habitacion.getHotel();
            hoteles.add(hotel);
        }
        return hoteles;

    }


}






