package com.example.booking.services;

import com.example.booking.models.Habitacion;
import com.example.booking.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public Habitacion encontrarPorId(Integer id){
        return habitacionRepository.findById(id).orElse(null);
    }


    public void borrarHabitacion(Habitacion habitacion){
        habitacionRepository.delete(habitacion);
    }


    public void guardarPersonalizado(Integer idHotel,Integer numHab,String extTel, Integer capacidad,
                                     String imagen, String descripcion){
        habitacionRepository.guardarPersonalizado(idHotel,numHab,extTel,capacidad,imagen,descripcion);
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



    public List<Habitacion> buscarporoidHabitacion(Integer id_hotel, Integer capacidad, Date fecha_inicio, Date fecha_fin) {
        List<Habitacion> habitacion = habitacionRepository.buscarporidhab(id_hotel, capacidad, fecha_inicio, fecha_fin);
        return habitacion;

    }


}
