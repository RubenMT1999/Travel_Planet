package com.example.booking.controllers;

import com.example.booking.models.*;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HotelService;
//import com.example.booking.services.PensionService;
import com.example.booking.services.TarifaService;
import com.example.booking.services.UsuarioService;
import net.bytebuddy.asm.Advice;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tarifa")
public class TarifaController {
    @Autowired
    TarifaService tarifaService;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelService hotelService;
    @Autowired
    UsuarioService usuarioService;
//    @Autowired
//    PensionService pensionService;

    @GetMapping("/nuevo/{id}")
    public String mostrarTarifaNueva(@PathVariable Integer id, Model model){
        if ( tarifaService.verTarifa(id) != null){
            model.addAttribute("error", "Ya existen características");
            return "redirect:/tarifa/editar/{id}";
        }else{

        PensionHotel pension1 = new PensionHotel();
        PensionHotel pension2 = new PensionHotel();
        PensionHotel pension3 = new PensionHotel();
        PensionHotel pension4 = new PensionHotel();
        Tarifa tarifa = new Tarifa();
        Tarifa tari = new Tarifa();
        Integer id_hotel = id;

            List<PensionHotel> pensiones = new ArrayList<>();
            pensiones.add(pension1);
            pensiones.add(pension2);
            pensiones.add(pension3);
            pensiones.add(pension4);

            model.addAttribute("pension1", pension1);
            model.addAttribute("pension2", pension2);
            model.addAttribute("pension3", pension3);
            model.addAttribute("pension4", pension4);
        model.addAttribute("tarifa",tarifa);
        model.addAttribute("id_hotel", id_hotel);

        return "tarifaNueva";
        }
    }

    @PostMapping("/nuevo")
    public String tarifaNueva(@Valid Tarifa tarifa, Model model, @RequestParam ("id_hotel")Integer id_hotel,
                               @RequestParam(name = "precio_completa") Double pension1,
                               @RequestParam(name = "precio_media_pension") Double pension2,
                               @RequestParam(name = "precio_desayuno") Double pension3,
                               @RequestParam(name = "precio_ninguna") Double pension4,
                              @RequestParam(name = "fecha_inicio_alta") String inicioAlta,
                              @RequestParam(name = "fecha_fin_alta") String finAlta,
                              @RequestParam(name = "precio_temporada_alta") Double precioAlta,
                              @RequestParam(name = "fecha_inicio_media") String inicioMedia,
                              @RequestParam(name = "fecha_fin_media") String finMedia,
                              @RequestParam(name = "precio_temporada_media") Double precioMedia,
                              @RequestParam(name = "fecha_inicio_baja") String inicioBaja,
                              @RequestParam(name = "fecha_fin_baja") String finBaja,
                              @RequestParam(name = "precio_temporada_baja") Double precioBaja
    ) throws ParseException {


            tarifaService.guardarTarifa(tarifa.getPrecioBanio(),tarifa.getPrecioCajaFuerte(),tarifa.getPrecioCocina(),tarifa.getPrecioTV(),tarifa.getPrecioTerraza(),tarifa.getPrecioWifi(),tarifa.getPrecioAire(),id_hotel);
            Tarifa tarifaHotel = tarifaService.verTarifa(id_hotel);
            Integer id_tarifa = tarifaHotel.getId();




            tarifaService.guardarPension(EPension.AlojamientoDesayuno.ordinal(), pension1, id_tarifa);
            tarifaService.guardarPension(EPension.MediaPension.ordinal(), pension2, id_tarifa);
            tarifaService.guardarPension(EPension.PensionCompleta.ordinal(), pension3, id_tarifa);
            tarifaService.guardarPension(EPension.TodoIncluido.ordinal(), pension4, id_tarifa);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = formato.parse(inicioAlta);
        Date fecha_Fin = formato.parse(finAlta);

            tarifaService.guardarTemporada(Temporada.Alta.ordinal(),fechaInicio,fecha_Fin,precioAlta,id_tarifa);

        Date fechaInicioMedia = formato.parse(inicioMedia);
        Date fecha_FinMedia = formato.parse(finMedia);

        tarifaService.guardarTemporada(Temporada.Media.ordinal(),fechaInicioMedia,fecha_FinMedia,precioMedia,id_tarifa);

        Date fechaInicioBaja = formato.parse(inicioBaja);
        Date fecha_FinNaja = formato.parse(finBaja);

        tarifaService.guardarTemporada(Temporada.Baja.ordinal(),fechaInicioBaja,fechaInicioBaja,precioBaja,id_tarifa);


            return "redirect:/perfil/mis-hoteles";




    }

//    @GetMapping("/pension/{id}")
//    public  String mostrarTarifa(@PathVariable Integer id, Model model){
//        Tarifa tarifa = new Tarifa();
//        Pension pension = tarifa.getPension();
//        Integer id_tarifa = id;
//        model.addAttribute("pension", tarifa);
//        model.addAttribute("id_hotel", id_tarifa);
//        return "pensionNueva";

//    }

    @GetMapping("/editar/{id}")
    public String mostrarTarifaEditar(@PathVariable int id, Model model){

        if ( tarifaService.verTarifa(id) == null){
            model.addAttribute("error", "No existen características");
            return "redirect:/hoteles/verHotelesUsuarios";
        }else{


        Integer id_hotel = id;
        model.addAttribute("tarifa", tarifaService.verTarifa(id));
        model.addAttribute("id_hotel", id_hotel);
        return "tarifaEditar";
        }
    }

    @PostMapping("/editar")
    public String mostrarTarifaEditar(@ModelAttribute("tarifa") Tarifa tarifa,Model model, @RequestParam ("id_hotel")Integer id_hotel,
                                      @Nullable @RequestParam(name = "precio_completa") Double pension1,
                                      @Nullable @RequestParam(name = "precio_media_pension") Double pension2,
                                      @Nullable @RequestParam(name = "precio_desayuno") Double pension3,
                                      @Nullable @RequestParam(name = "precio_ninguna") Double pension4,
                                      @Nullable @RequestParam(name = "fecha_inicio_alta") String inicioAlta,
                                      @Nullable @RequestParam(name = "fecha_fin_alta") String finAlta,
                                      @Nullable @RequestParam(name = "precio_temporada_alta") Double precioAlta,
                                      @Nullable @RequestParam(name = "fecha_inicio_media") String inicioMedia,
                                      @Nullable  @RequestParam(name = "fecha_fin_media") String finMedia,
                                      @Nullable @RequestParam(name = "precio_temporada_media") Double precioMedia,
                                      @Nullable @RequestParam(name = "fecha_inicio_baja") String inicioBaja,
                                      @Nullable @RequestParam(name = "fecha_fin_baja") String finBaja,
                                      @Nullable @RequestParam(name = "precio_temporada_baja") Double precioBaja) throws ParseException {



        Tarifa tarifaEditar = tarifaService.verTarifa(id_hotel);
        tarifaEditar.setPrecioWifi(tarifa.getPrecioWifi());
        tarifaEditar.setPrecioCajaFuerte(tarifa.getPrecioCajaFuerte());
        tarifaEditar.setPrecioCocina(tarifa.getPrecioCocina());
        tarifaEditar.setPrecioAire(tarifa.getPrecioAire());
        tarifaEditar.setPrecioTV(tarifa.getPrecioTV());
        tarifaEditar.setPrecioTerraza(tarifa.getPrecioTerraza());
        tarifaEditar.setPrecioBanio(tarifa.getPrecioBanio());



        tarifaService.tarifaEditar(tarifaEditar);

        if (pension1 != null){
            tarifaService.modificarPension(pension1, tarifaEditar.getId(),EPension.AlojamientoDesayuno.ordinal());
        }
        if (pension2 != null){
            tarifaService.modificarPension(pension2, tarifaEditar.getId(),EPension.MediaPension.ordinal());
        }
        if (pension3 != null){
            tarifaService.modificarPension(pension3, tarifaEditar.getId(),EPension.PensionCompleta.ordinal());

        }
        if (pension4 != null){
            tarifaService.modificarPension(pension4, tarifaEditar.getId(),EPension.TodoIncluido.ordinal());
        }


        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");



        if (precioAlta != null){
            Date fechaInicio = formato.parse(inicioAlta);
            Date fecha_Fin = formato.parse(finAlta);
            tarifaService.modificarTemporada(fechaInicio,fecha_Fin,precioAlta,tarifaEditar.getId(),Temporada.Alta.ordinal());

        }
        if (precioMedia != null){
            Date fechaInicioMedia = formato.parse(inicioMedia);
            Date fecha_FinMedia = formato.parse(finMedia);
            tarifaService.modificarTemporada(fechaInicioMedia,fecha_FinMedia,precioMedia,tarifaEditar.getId(),Temporada.Media.ordinal());

        }
        if (precioBaja != null){
            Date fechaInicioBaja = formato.parse(inicioBaja);
            Date fecha_FinNaja = formato.parse(finBaja);
            tarifaService.modificarTemporada(fechaInicioBaja,fecha_FinNaja,precioBaja,tarifaEditar.getId(),Temporada.Baja.ordinal());

        }




        return "redirect:/perfil/mis-hoteles";
    }






    //Faker para generar tarifas aleatorios.

    @GetMapping("/generar")
    public void generarhabitacionesAleatorio() {

        Faker faker = new Faker();

        for (Hotel h : hotelRepository.obtenertodoshoteles()) {
            Tarifa tarifa = new Tarifa();
                tarifa.setPrecioWifi(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioCajaFuerte(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioCocina(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioAire(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioTV(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioTerraza(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioBanio(faker.number().randomDouble(1, 5, 20));
            tarifa.setHotel(h);
            tarifaService.guardar(tarifa);
}
    }

    //Faker para generar estrella en hoteles
//   @GetMapping("/generarusuario")
//   public void generarhabitacionesAleatorio() {
//       Faker faker = new Faker();
//       for (Hotel h : hotelRepository.obtenerTodoshoteles()) {
//           h.setPuntuacion(faker.number().numberBetween(1,6));
//           hotelRepository.save(h);
//       }
//   }
//
//

}
