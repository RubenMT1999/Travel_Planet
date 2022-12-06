package com.example.booking.controllers;

import Paginador.PageRender;
import com.example.booking.models.*;
import com.example.booking.models.Hotel;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import com.example.booking.services.UsuarioService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.valueOf;

@RequestMapping("/habitaciones")
@Controller
@SessionAttributes({"habitacion","id"})
public class HabitacionController {

    @Autowired
    HabitacionService habitacionService;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelService hotelService;

    /**
     * Este método maneja una solicitud GET a la ruta /habitaciones/listar/{id}.
     * Acepta un parámetro de consulta llamado "page" que se utiliza para paginar los resultados.
     *
     * @param model El objeto Spring Model utilizado para almacenar atributos del modelo
     * @param id El id de la entidad para la cual se listan las habitaciones
     * @param page El número de página a recuperar, el valor predeterminado es 0
     * @return El nombre de la plantilla Thymeleaf a renderizar
     */
    @GetMapping({"/listar/{id}"})
    public String listar(Model model, @PathVariable Integer id, @RequestParam(name="page", defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Habitacion> habitaciones = habitacionService.listarHabitacionesPages(pageRequest, id);
        PageRender<Habitacion> pageRender = new PageRender<>("/habitaciones/listar/"+id, habitaciones);
        model.addAttribute("page", pageRender);
        model.addAttribute("habitaciones", habitaciones);
        model.addAttribute("id", id);
        return "habitaciones";
    }

    /**
     * Este método maneja una solicitud GET a la ruta /habitaciones/crear/{id}.
     *
     * @param model El objeto Spring Model utilizado para almacenar atributos del modelo
     * @param id El id del Hotel al que se asociará la nueva Habitacion
     * @return El nombre de la plantilla Thymeleaf a renderizar
     */
    @GetMapping("/crear/{id}")
    public String crear(Model model, @PathVariable Integer id) {
        Habitacion habitacion = new Habitacion();
        Hotel hotel = hotelRepository.findById(id).get();
        habitacion.setHotel(hotel);
        Tarifa tarifa = hotel.getTarifa();

        model.addAttribute("titulo", "Crear Habitación");
        model.addAttribute("habitacion", habitacion);
        model.addAttribute("tarifa", tarifa);
        return "crearHabitacion";
    }


    /**
     * Este método maneja una solicitud POST a la ruta /habitaciones/crear.
     * Valida el objeto Habitacion proporcionado y comprueba si existe una Habitacion con el mismo
     * número en el mismo Hotel. Si hay errores de validación o si existe una Habitacion con el
     * mismo número en el mismo Hotel, se renderiza la plantilla "crearHabitacion" de nuevo.
     * Si la validación es exitosa, se establece el precio de la Habitacion en función de la Tarifa
     * del Hotel y de los atributos de la Habitacion. También se carga la imagen proporcionada y se
     * guarda la Habitacion en la base de datos. Finalmente, se redirige a la ruta
     * /habitaciones/listar/{idHotel}.
     *
     * @param habitacion El objeto Habitacion a validar y guardar
     * @param result El objeto BindingResult que contiene los resultados de la validación
     * @param model El objeto Spring Model utilizado para almacenar atributos del modelo
     * @param imagen La imagen a cargar para la Habitacion
     * @param status El objeto SessionStatus que se utiliza para marcar la sesión como completada
     * @param idHotel El id del Hotel al que pertenece la Habitacion
     * @param flash El objeto RedirectAttributes utilizado para agregar atributos flash al modelo
     * @return El nombre de la plantilla Thymeleaf a renderizar o la ruta a la que se redirige
     */
    @PostMapping("/crear")
    public String procesar(@Valid Habitacion habitacion, BindingResult result, Model model, @RequestParam(value = "file") MultipartFile imagen,
                           SessionStatus status, @ModelAttribute("id") Integer idHotel, RedirectAttributes flash){

        Integer comprobarNumHab = habitacionService.comprobarNumHab(habitacion.getNumeroHabitacion(),idHotel);

        if(result.hasErrors() || comprobarNumHab != null){
            Tarifa tarifa = habitacion.getHotel().getTarifa();
            model.addAttribute("titulo", "Ha habido algún error");
            model.addAttribute("comprobarNumHab",comprobarNumHab);
            model.addAttribute("tarifa",tarifa);
            return "crearHabitacion";
        }

        habitacion.setPrecioBase(habitacionService.establecerPrecioHabitacion(habitacion.getPrecioBase(), habitacion));
        habitacionService.editarDisponibilidad(true,habitacion.getId());
        habitacionService.cargarImagen(imagen,flash,habitacion);

        habitacionService.guardarPersonalizado(habitacion.getHotel().getId(),habitacion.getNumeroHabitacion(),habitacion.getExtensionTelefonica(),
                habitacion.getCapacidad(),habitacion.getImagen(),habitacion.getDescripcion(),habitacion.getPrecioBase(),habitacion.isCajaFuerte(),
                habitacion.isCocina(),habitacion.isBanioPrivado(),habitacion.isAireAcondicionado(),habitacion.isTv(),habitacion.isTerraza(),
                habitacion.isWifi(), true);
        model.addAttribute("success","La habitación ha sido creada con éxito!");
        return "redirect:/habitaciones/listar/"+idHotel;
    }


    /**
     * Este método maneja una solicitud GET a la ruta /habitaciones/editar/{id}.
     * Busca un objeto Habitacion en la base de datos utilizando el id proporcionado como parámetro
     * de la ruta. Si no se encuentra el objeto, se agrega un mensaje de error al modelo y se
     * renderiza la plantilla "index".
     * Si se encuentra el objeto, se agrega como un atributo del modelo y se renderiza la plantilla
     * "editarHabitacion".
     *
     * @param model El objeto Spring Model utilizado para almacenar atributos del modelo
     * @param id El id de la Habitacion a buscar y editar
     * @return El nombre de la plantilla Thymeleaf a renderizar
     */
    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable Integer id){
        Habitacion habitacion = habitacionService.findById(id);

        if(habitacion==null){
            model.addAttribute("error","La habitación no existe");
            return "index";
        }

        model.addAttribute("habitacion",habitacion);
        model.addAttribute("titulo","Editar Habitación");
        return "editarHabitacion";
    }



    /**
     * Este método maneja una solicitud POST a la ruta /habitaciones/editar.
     * Valida el objeto Habitacion proporcionado y comprueba si hay errores de validación.
     * Si hay errores, se agrega un mensaje de error al modelo y se renderiza la plantilla
     * "editarHabitacion" de nuevo.
     * Si la validación es exitosa, se carga la imagen proporcionada y se guarda la Habitacion en la
     * base de datos. Finalmente, se agrega un mensaje de éxito al modelo y se redirige a la ruta
     * /habitaciones/listar/{idHotel}.
     *
     * @param habitacion El objeto Habitacion a validar y guardar
     * @param result El objeto BindingResult que contiene los resultados de la validación
     * @param model El objeto Spring Model utilizado para almacenar atributos del modelo
     * @param status El objeto SessionStatus que se utiliza para marcar la sesión como completada
     * @param idHotel El id del Hotel al que pertenece la Habitacion
     * @param imagen La imagen a cargar para la Habitacion
     * @param flash El objeto RedirectAttributes utilizado para agregar atributos flash al modelo
     * @return El nombre de la plantilla Thymeleaf a renderizar o la ruta a la que se redirige
     */
    @PostMapping("/editar")
    public String postEditar(@Valid Habitacion habitacion, BindingResult result, Model model, SessionStatus status,
                             @ModelAttribute("id") Integer idHotel, @RequestParam(value = "file") MultipartFile imagen,
                             RedirectAttributes flash) {
        if(result.hasErrors()) {
            model.addAttribute("titulo", "Ha habido algún error");
            return "editarHabitacion";
        }

        habitacionService.cargarImagen(imagen,flash,habitacion);

        habitacionService.guardarHabitacion(habitacion);
        model.addAttribute("success","La habitación ha sido actualizada con éxito!");
        return "redirect:/habitaciones/listar/"+idHotel;
    }




    /**
     * Este método maneja una solicitud GET a la ruta /habitaciones/borrar/{id}.
     * Busca un objeto Habitacion en la base de datos utilizando el id proporcionado como parámetro
     * de la ruta. Luego, borra la imagen asociada con la habitación y finalmente borra la habitación
     * de la base de datos. Después de borrar la habitación, se redirige a la ruta
     * /habitaciones/listar/{idHotel}.
     *
     * @param id El id de la Habitacion a buscar y borrar
     * @param model El objeto Spring Model utilizado para almacenar atributos del modelo
     * @param idHotel El id del Hotel al que pertenece la Habitacion
     * @param flash El objeto RedirectAttributes utilizado para agregar atributos flash al modelo
     * @return La ruta a la que se redirige después de borrar la Habitacion
     */
    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable("id") Integer id, Model model, @ModelAttribute("id") Integer idHotel,
                         RedirectAttributes flash) {

        Habitacion habitacion = habitacionService.findById(id);
        habitacionService.borrarImagen(habitacion);
        habitacionService.borrarHabitacion(habitacion);
        flash.addFlashAttribute("success","Habitación eliminada con éxito!");
        model.addAttribute("success","Habitación borrada con éxito!");
        return "redirect:/habitaciones/listar/"+idHotel;
    }


    //Faker para generar habitaciones aleatorio.
   /* @GetMapping("/generar")
    public void generarhabitacionesAleatorio() {

        Faker faker = new Faker();
        Integer contadorHabitacion = 1;

        for (Hotel h : hotelRepository.obtenerTodoshoteles()){
            List<Habitacion> habitaciones = new ArrayList<>();
            for (int x = 0; x < 20; x ++){
                Habitacion habitacion = new Habitacion();
                habitacion.setId(contadorHabitacion);
                habitacion.setHotel(h);
                habitacion.setNumeroHabitacion(faker.number().numberBetween(1,500));
                habitacion.setExtensionTelefonica(valueOf(faker.number().numberBetween(100,999)));
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
                habitacion.setPrecioBase(faker.number().numberBetween(50,400));
                contadorHabitacion ++;
                habitacionService.guardarHabitacion(habitacion);
                habitaciones.add(habitacion);
                h.setHabitaciones(habitaciones);
            }
            hotelRepository.save(h);
        }



    }

    */




}
