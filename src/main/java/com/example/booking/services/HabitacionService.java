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
import java.nio.file.InvalidPathException;
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

    public void borrarHabitacion(Habitacion habitacion){
        habitacionRepository.delete(habitacion);
    }


    public void guardarPersonalizado(Integer idHotel,Integer numHab,String extTel, Integer capacidad,
                                     String imagen, String descripcion, Double precioBase,Boolean cajaFuerte,
                                     Boolean cocina,Boolean banioPrivado,Boolean aireAcondicionado,Boolean tv,
                                     Boolean terraza,Boolean wifi, Boolean disponibilidad){
        habitacionRepository.guardarPersonalizado(idHotel,numHab,extTel,capacidad,imagen,descripcion,precioBase,
                cajaFuerte,cocina,banioPrivado,aireAcondicionado,tv,terraza,wifi, disponibilidad);
    }


    public void cargarImagen(MultipartFile imagen, RedirectAttributes flash, Habitacion habitacion){
        if(!imagen.isEmpty() && (imagen.getOriginalFilename().endsWith(".jpg") || imagen.getOriginalFilename().endsWith(".png"))){

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

        Boolean prueba = false;
        if (imagen.getOriginalFilename().endsWith(".jpg") || imagen.getOriginalFilename().endsWith(".png")){
            prueba = true;
        }

        if(imagen.isEmpty() || prueba==false){
            habitacion.setImagen("https://t3.ftcdn.net/jpg/01/06/85/86/360_F_106858608_EuOWeiATyMOD6b9cXNzcJDSZufLbojQs.jpg");
        }
    }


    public void borrarImagen(Habitacion habitacion){

        try{
            Path rootPath = Paths.get("uploads").resolve(habitacion.getImagen()).toAbsolutePath();
            File archivo = rootPath.toFile();
            if(archivo.length() >0 && archivo.canRead()){
                archivo.delete();
            }
        }catch (InvalidPathException e){
        }
    }



    public void init() throws IOException{

        if(Files.notExists(Paths.get("uploads"))){
            Files.createDirectory(Paths.get("uploads"));
        }


    }


    public Page<Habitacion> habitacionfiltro(Integer id_hotel, Date fecha_inicio, Date fecha_fin, Integer capacidad, boolean wifi1,
                                        boolean terraza1, boolean tv1, boolean aire1, boolean banio_privado, boolean cocina1,
                                        boolean caja_fuerte, Double precioBase, Integer puntuacion,Pageable pageable) {
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
            precioBase = 5.0;
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
                banio, cocina, caja, precioBase, precioFinal, puntuacion, estrellamax, pageable);
    }
    public List<Habitacion> habitacionfiltroprecio(Integer id_hotel, Date fecha_inicio, Date fecha_fin, Integer capacidad, boolean wifi1,
                                             boolean terraza1, boolean tv1, boolean aire1, boolean banio_privado, boolean cocina1,
                                             boolean caja_fuerte, Double precioBase, Integer puntuacion) {
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
            precioBase = 5.0;
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
        return habitacionRepository.buscarfiltrosidprecio(id_hotel, fecha_inicio, fecha_fin, capacidad, wifi, terraza, tv, aire,
                banio, cocina, caja, precioBase, precioFinal, puntuacion, estrellamax);
    }

    public List<Habitacion> buscarHabitaciones(Integer id_hotel, Integer capacidad, Date fecha_inicio, Date fecha_fin) {
        return habitacionRepository.buscarHabitacionesrepo(id_hotel, capacidad, fecha_inicio, fecha_fin);
    }



    public Page<Habitacion> listarHabitacionesPages(Pageable pageable,Integer id) {
        return pageRepositoryHab.listarHabitacionesPages(pageable,id);
    }

    public Page<Habitacion> buscarHabitacionesPages(Integer id_hotel, Integer capacidad, Date fecha_inicio, Date fecha_fin,Pageable pageable) {
        return pageRepositoryHab.buscarHabitacionesPages(id_hotel, capacidad, fecha_inicio, fecha_fin,pageable);
    }




    public Double establecerPrecioHabitacion(Double precioBase, Habitacion habitacion){

        if (habitacion.isCajaFuerte() == true){
            precioBase += habitacion.getHotel().getTarifa().getPrecioCajaFuerte();
            habitacion.setCajaFuerte(true);
        }
        if (habitacion.isTv() == true){
            precioBase += habitacion.getHotel().getTarifa().getPrecioTV();
            habitacion.setTv(true);
        }
        if (habitacion.isCocina() == true){
            precioBase += habitacion.getHotel().getTarifa().getPrecioCocina();
            habitacion.setCocina(true);
        }
        if (habitacion.isTerraza() == true){
            precioBase += habitacion.getHotel().getTarifa().getPrecioTerraza();
            habitacion.setTerraza(true);
        }
        if (habitacion.isBanioPrivado() == true){
            precioBase += habitacion.getHotel().getTarifa().getPrecioBanio();
            habitacion.setBanioPrivado(true);
        }
        if (habitacion.isWifi() == true){
            precioBase += habitacion.getHotel().getTarifa().getPrecioWifi();
            habitacion.setWifi(true);
        }
        if (habitacion.isAireAcondicionado() == true){
            precioBase += habitacion.getHotel().getTarifa().getPrecioAire();
            habitacion.setAireAcondicionado(true);
        }

        return precioBase;
    }

    public void listarIdHotelesPorHabitacion() { habitacionRepository.totalIdHotelesHabitacion(); }


    public Integer comprobarNumHab(Integer numHab,Integer idHotel){
        return habitacionRepository.comprobarNumHab(numHab,idHotel);
    }

    public void editarDisponibilidad(Boolean disponibilidad, Integer id){ habitacionRepository.editarDisponibilidad(disponibilidad,id);}

    public Habitacion obtenerHabitacionReserva(Integer id){return habitacionRepository.obtenerHabitacionReserva(id);}
}
