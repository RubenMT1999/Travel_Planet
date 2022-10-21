package com.example.booking.services;

import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.repository.HotelRepository;
import com.example.booking.repository.PageRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private PageRepository pageRepository;

    public List<Hotel> Buscar(String ciudades, Date fecha_inicio, Date fecha_fin, Integer capacidad) {
        return hotelRepository.buscador(ciudades, fecha_inicio, fecha_fin, capacidad);
            }

    public List<Hotel> verTodosHoteles(){
        Hotel hotelNuevo = new Hotel();
        return hotelRepository.findAll();
    }
    public List<Hotel> hotelPorCiudad(String ciudad) {return hotelRepository.hotelesPorCiudad(ciudad);}

    public Hotel hotelID(int id){
        return hotelRepository.findById(id).orElse(null);
    }

    public Hotel hotelGuardar(Hotel hotel){
        return hotelRepository.save(hotel);
    }


    public Hotel hotelEditar(Hotel hotel){return hotelRepository.save(hotel);}

    public void hotelEliminar(int id){ hotelRepository.deleteById(id);}


    public List<Hotel> hotelesMail (String auth) { return hotelRepository.hotelPorMail(auth);}


    public void crearHotel(String nombre, String puntuacion, String precio, String comentario, String imagen, String lugar, String telefono, String cif, Integer num_hab, String ciudad, Integer id_usuario){
        hotelRepository.crearHotel(nombre, puntuacion, precio, comentario, imagen, lugar, telefono, cif, num_hab, ciudad, id_usuario);
    }

    public Page<Hotel> findAll(Pageable pageable) {return pageRepository.findAll(pageable);}

}



