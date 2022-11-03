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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Set;

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
                                     String imagen, String descripcion, Double precioBase,Boolean cajaFuerte,
                                     Boolean cocina,Boolean banioPrivado,Boolean aireAcondicionado,Boolean tv,
                                     Boolean terraza,Boolean wifi){
        habitacionRepository.guardarPersonalizado(idHotel,numHab,extTel,capacidad,imagen,descripcion,precioBase,
                cajaFuerte,cocina,banioPrivado,aireAcondicionado,tv,terraza,wifi);
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

        if(imagen.isEmpty()){
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



    public List<Habitacion> buscarporoidHabitacion(Integer id_hotel, Integer capacidad, Date fecha_inicio, Date fecha_fin) {
        List<Habitacion> habitacion = habitacionRepository.buscarporidhab(id_hotel, capacidad, fecha_inicio, fecha_fin);
        return habitacion;

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


    public Integer comprobarNumHab(Integer numHab){
        return habitacionRepository.comprobarNumHab(numHab);
    }



}
