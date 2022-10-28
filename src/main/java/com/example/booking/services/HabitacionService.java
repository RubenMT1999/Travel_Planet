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

    public List<Habitacion> habitacionfiltro(Integer id_hotel, Date fecha_inicio, Date fecha_fin, Integer capacidad, boolean wifi,
                                        boolean terraza, boolean tv, boolean aire, boolean banio_privado, boolean cocina,
                                        boolean caja_fuerte) {
        return habitacionRepository.buscarfiltros(id_hotel, fecha_inicio, fecha_fin, capacidad, wifi, terraza, tv, aire,
                banio_privado, cocina, caja_fuerte);
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
