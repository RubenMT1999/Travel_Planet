package com.example.booking.services;
import com.example.booking.models.Hotel;
import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.IPageRepositoryHab;
import com.example.booking.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class HabitacionService {

   @Autowired
    HabitacionRepository habitacionRepository;

    @Autowired
    IPageRepositoryHab pageRepositoryHab;

    public Habitacion findById(Integer id){
        return habitacionRepository.findById(id).orElse(null);
    }

    public List<Habitacion> listarHabitaciones(Integer id){
        return habitacionRepository.listarHabitaciones(id);
    }

    public void guardarHabitacion(Habitacion habitacion){

        habitacionRepository.save(habitacion);
    }

    public Habitacion encontrarPorId(Integer id){
        return habitacionRepository.findById(id).orElse(null);
    }


    public void borrarHabitacion(Habitacion habitacion){
        habitacionRepository.delete(habitacion);
    }


    public void guardarPersonalizado(Integer idHotel,Integer numHab,String extTel, Integer capacidad,
                                     String imagen, String descripcion, Integer precioBase){
        habitacionRepository.guardarPersonalizado(idHotel,numHab,extTel,capacidad,imagen,descripcion,precioBase);
    }


    public void cargarImagen(MultipartFile imagen, RedirectAttributes flash, Habitacion habitacion){
        if(!imagen.isEmpty()){

            String uniqueFilename = UUID.randomUUID().toString()+ "_" +imagen.getOriginalFilename();
            //Path relativo al proyecto
            Path rootPath = Paths.get("uploads").resolve(uniqueFilename);
            //Path absoluto
            Path rootAbsolutePath = rootPath.toAbsolutePath();

            try {
                Files.copy(imagen.getInputStream(),rootAbsolutePath);
                flash.addFlashAttribute("info","Has subido correctamente '"+ uniqueFilename+"'");

                habitacion.setImagen(uniqueFilename);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public void borrarImagen(Habitacion habitacion){
        Path rootPath = Paths.get("uploads").resolve(habitacion.getImagen()).toAbsolutePath();
        File archivo = rootPath.toFile();

        if(archivo.length() >0 && archivo.canRead()){
            archivo.delete();
        }
    }



    public void init() throws IOException{

        if(Files.notExists(Paths.get("uploads"))){
            Files.createDirectory(Paths.get("uploads"));
        }


    }



    public List<Habitacion> buscarporoidHabitacion(Integer id_hotel, Integer capacidad, Date fecha_inicio, Date fecha_fin) {
        List<Habitacion> habitacion = habitacionRepository.buscarporidhab(id_hotel, capacidad, fecha_inicio, fecha_fin);
        return habitacion;

    }

    public List<Habitacion> habitacionfiltro(Integer id_hotel, Date fecha_inicio, Date fecha_fin, Integer capacidad, boolean wifi1,
                                        boolean terraza1, boolean tv1, boolean aire1, boolean banio_privado, boolean cocina1,
                                        boolean caja_fuerte, Integer precioBase, Integer puntuacion) {
        List<Integer> wifi = new ArrayList<>();
        List<Integer> terraza = new ArrayList<>();
        List<Integer> tv = new ArrayList<>();
        List<Integer> cocina = new ArrayList<>();
        List<Integer> aire = new ArrayList<>();
        List<Integer> banio = new ArrayList<>();
        List<Integer> caja = new ArrayList<>();
        Integer precioFinal = 0;
        Double estrellamax = 0.0;
        if(puntuacion == null){
            puntuacion = 0;
            estrellamax = 5.1;
        }
        if(puntuacion == 1){
            estrellamax = 1.1;
        }
        if(puntuacion == 2){
            estrellamax = 2.1;
        }
        if(puntuacion == 3){
            estrellamax = 3.1;
        }
        if(puntuacion == 4){
            estrellamax = 4.1;
        }
        if(puntuacion == 5){
            estrellamax = 5.1;
        }
        if(precioBase == null){
            precioBase = 5;
            precioFinal = 10000;

        }
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
        return habitacionRepository.buscarfiltrosid(id_hotel, fecha_inicio, fecha_fin, capacidad, wifi, terraza, tv, aire,
                banio, cocina, caja, precioBase, precioFinal, puntuacion, estrellamax);
    }


    public Page<Habitacion> listarHabitacionesPages(Pageable pageable,Integer id) {
        return pageRepositoryHab.listarHabitacionesPages(pageable,id);
    }



//    public Integer establecerPrecioHabitacion(Integer precioBase, Habitacion habitacion){
//
//        if (habitacion.isCajaFuerte() == true){
//            precioBase +=
//        }
//
//    }

    public void listarIdHotelesPorHabitacion() { habitacionRepository.totalIdHotelesHabitacion(); }



}
