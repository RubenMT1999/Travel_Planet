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

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public List<Hotel> buscarPorFiltros(String ciudad, Date fecha_inicio, Date fecha_fin, Integer capacidad, boolean wifi1,
                                        boolean terraza1, boolean tv1, boolean aire1, boolean banio_privado, boolean cocina1,
                                        boolean caja_fuerte, Integer precioBase) {
        List<Integer> wifi = new ArrayList<>();
        List<Integer> terraza = new ArrayList<>();
        List<Integer> tv = new ArrayList<>();
        List<Integer> cocina = new ArrayList<>();
        List<Integer> aire = new ArrayList<>();
        List<Integer> banio = new ArrayList<>();
        List<Integer> caja = new ArrayList<>();
        Integer precioFinal = 0;
        if(precioBase == 0){
            precioFinal = 50;
        }
        if(precioBase == 50){
            precioFinal = 100;
        }
        if(precioBase == 100){
            precioFinal = 150;
        }
        if(precioBase == 150){
            precioFinal = 200;
        }
        if(precioBase == 200){
            precioFinal = 5000;
        }
        if(!wifi1){
            wifi.add(0);
            wifi.add(1);
        }else{
            wifi.add(1);
        }
        if(!terraza1){
            terraza.add(0);
            terraza.add(1);
        }else{
            terraza.add(1);
        }
        if(!tv1){
            tv.add(0);
            tv.add(1);
        }else{
            tv.add(1);
        }
        if(!cocina1){
            cocina.add(0);
            cocina.add(1);
        }else{
            cocina.add(1);
        }
        if(!aire1){
            aire.add(0);
            aire.add(1);
        }else{
            aire.add(1);
        }
        if(!banio_privado){
            banio.add(0);
            banio.add(1);
        }else{
            banio.add(1);
        }
        if(!caja_fuerte){
            caja.add(0);
            caja.add(1);
        }else{
            caja.add(1);
        }
        return hotelRepository.buscarfiltros(ciudad, fecha_inicio, fecha_fin, capacidad, wifi, terraza, tv, aire,
                                             banio, cocina, caja, precioBase, precioFinal);
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



