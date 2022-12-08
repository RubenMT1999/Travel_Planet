package com.example.booking.controllers;

import Paginador.PageRender;
import com.example.booking.models.*;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import com.example.booking.services.PensionService;
import com.example.booking.services.ReservaService;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

    @Controller
    @SessionAttributes({"habitacion","reserva", "hoteles", "habfiltro", "puntuacion", "precio"})

    public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HabitacionService habitacionService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private PensionService pensionService;


    /**
     * Este método maneja una solicitud GET a la ruta /hoteles/listar. Procesa una búsqueda de hoteles
     * utilizando los parámetros proporcionados en la solicitud HTTP GET. Primero, convierte las fechas
     * de inicio y fin proporcionadas en el formato adecuado. Luego, comprueba si la fecha de fin es
     * anterior a la fecha de inicio o si excede el límite de 30 días. Si se cumple alguna de estas
     * condiciones, agrega un mensaje de error al modelo y redirige a la página de inicio.
     *
     * Si la fecha de fin es válida, busca hoteles que cumplan con los criterios de búsqueda y calcula
     * el precio mínimo de las habitaciones disponibles en cada hotel. Finalmente, agrega los resultados
     * de la búsqueda al modelo y redirige a la página de búsqueda de hoteles.
     *
     * @param ciudades La ciudad donde se realiza la búsqueda
     * @param flash El objeto RedirectAttributes utilizado para agregar atributos flash al modelo
     * @param fecha_inicio La fecha de inicio de la reserva
     * @param fecha_fin La fecha de fin de la reserva
     * @param capacidad La capacidad máxima requerida para la reserva
     * @param model El objeto Spring Model utilizado para almacenar atributos del modelo
     * @param session El objeto HttpSession utilizado para almacenar atributos de sesión
     * @return La ruta a la que se redirige después de procesar la búsqueda de hoteles
     **/
    @GetMapping("/hoteles/listar")
    public String procesarBusqueda(@RequestParam(name = "ciudad") String ciudades,
                                   RedirectAttributes flash,
                                   @RequestParam(name = "fechaInicio") String fecha_inicio,
                                   @RequestParam(name = "fechaFin") String fecha_fin,
                                   @RequestParam(name = "capacidad") Integer capacidad,
                                   Model model, HttpSession session) throws ParseException {





        model.addAttribute("titulo", "Buscar - Travel Planet");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = formato.parse(fecha_inicio);
        Date fecha_Fin = formato.parse(fecha_fin);

        //Para utilizar tener un minimo de dias y maximo de dias que se puede pedir un hotel.
        long tiempo = fecha_Fin.getTime() - fechaInicio.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long diffrence = time.convert(tiempo, TimeUnit.MILLISECONDS);
        Integer diferencia = (int) (long) diffrence;
        //Errores de buscador por la fecha.
        if(diferencia < 0){
            flash
                    .addFlashAttribute("error", "La fecha fin es anterior a la fecha inicial");
//                    .addFlashAttribute("clase", "success");
            return "redirect:/";
        } else if (diferencia > 30) {
            flash
                    .addFlashAttribute("error", "La fecha fin excede los 30 días");
//                    .addFlashAttribute("clase", "success");
            return "redirect:/";
        }

        //Para utilizar el Sessionatribute para fecha inicio y fecha fin.
        Hotel hoteles = new Hotel();
        hoteles.setCiudad(ciudades);

        model.addAttribute("hoteles", hoteles);

        //Para utilizar el Sessionatribute para fecha inicio y fecha fin.
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fecha_Fin);
        model.addAttribute("reserva", reserva);


        List<Hotel> hotel = hotelService.buscar(ciudades, fechaInicio, fecha_Fin, capacidad);

       for(Hotel h: hotel){
            Integer id = h.getId();
            List<Habitacion> habitacions = habitacionService.buscarHabitaciones(id, capacidad, fechaInicio, fecha_Fin);
            List<Double> precioHabitaciones = new ArrayList<>();
            for (Habitacion habitacion : habitacions){
                precioHabitaciones.add(habitacion.getPrecioBase());
            }
            Double preciomin = Collections.min(precioHabitaciones);
            h.setPrecio(preciomin.toString());
        }
        model.addAttribute("hotel", hotel);
        //Para utilizar el SessionAtribute para capacidad.
        Habitacion habitacion = new Habitacion();
        habitacion.setCapacidad(capacidad);
        model.addAttribute("habitacion", habitacion);
        model.addAttribute("hotel", hotel);
        model.addAttribute("hotels", hoteles);
        session.setAttribute("fi", fechaInicio);
        session.setAttribute("ff", fecha_Fin);
        return "busquedahoteles";
    }

        /**
         * Este método procesa una búsqueda de hoteles en base a los filtros especificados.
         *
         * @param ciudad Un objeto {@link Hotel} que contiene información sobre la ciudad donde se encuentra el hotel.
         * @param capacidad Un objeto {@link Habitacion} que contiene información sobre la capacidad de las habitaciones que se desean buscar.
         * @param fecha Un objeto {@link Reserva} que contiene información sobre las fechas de inicio y fin de la reserva.
         * @param habitacion Un objeto {@link Habitacion} que contiene información adicional sobre las características de las habitaciones que se desean buscar (por ejemplo, si tienen WiFi, terraza, aire acondicionado, etc.).
         * @param puntuacion Un objeto {@link Hotel} que contiene información sobre la puntuación que se desea buscar.
         * @param model El objeto {@link Model} que se utiliza para agregar atributos que se utilizarán en la vista para mostrar los resultados de la búsqueda.
         * @return El nombre de la vista que mostrará los resultados de la búsqueda.
         */
        @GetMapping("/hoteles/listarfiltro")
        public String procesarBusqueda(@ModelAttribute("hoteles") Hotel ciudad,
                                       @ModelAttribute( "habitacion") Habitacion capacidad,
                                       @ModelAttribute("reserva") Reserva fecha,
                                       Habitacion habitacion,
                                       Hotel puntuacion,
                                       Model model) {

            List<Hotel> hotels = hotelService.buscarPorFiltros(ciudad.getCiudad(), fecha.getFechaInicio(), fecha.getFechaFin(),
                    capacidad.getCapacidad(), habitacion.isWifi(), habitacion.isTerraza(), habitacion.isTv(), habitacion.isAireAcondicionado(),
                    habitacion.isBanioPrivado(), habitacion.isCocina(), habitacion.isCajaFuerte(), habitacion.getPrecioBase(), puntuacion.getEstrellas());
            for(Hotel h: hotels){
                Integer id = h.getId();
                List<Habitacion> habitacions = habitacionService.habitacionfiltroprecio(id, fecha.getFechaInicio(), fecha.getFechaFin(), capacidad.getCapacidad(),habitacion.isWifi(), habitacion.isTerraza(), habitacion.isTv(), habitacion.isAireAcondicionado()
                        , habitacion.isBanioPrivado(), habitacion.isCocina(), habitacion.isCajaFuerte(), habitacion.getPrecioBase(), puntuacion.getEstrellas());
                List<Double> precioHabitaciones = new ArrayList<>();
                for (Habitacion habitaciones : habitacions){
                    precioHabitaciones.add(habitaciones.getPrecioBase());

                }
                Double preciomin = Collections.min(precioHabitaciones);
                h.setPrecio(preciomin.toString());
            }

            model.addAttribute("habfiltro", habitacion);
            model.addAttribute("puntuacion", puntuacion);
            model.addAttribute("hotel", hotels);
            return  "hotelesporfiltros";
        }


        /**
         * Este método muestra los detalles de un hotel específico y permite a los usuarios buscar habitaciones disponibles.
         *
         * @param id El ID del hotel del que se desean ver los detalles.
         * @param capacidad Un objeto {@link Habitacion} que contiene información sobre la capacidad de las habitaciones que se desean buscar.
         * @param fecha Un objeto {@link Reserva} que contiene información sobre las fechas de inicio y fin de la reserva.
         * @param model El objeto {@link Model} que se utiliza para agregar atributos que se utilizarán en la vista para mostrar los detalles del hotel y los resultados de la búsqueda.
         * @param page La página actual de los resultados de la búsqueda (se utiliza para la paginación).
         * @return El nombre de la vista que mostrará los detalles del hotel y los resultados de la búsqueda.
         */
    @GetMapping("/hoteles/habitacion/{id}")
    public String hotelid(@PathVariable(name = "id") Integer id,
                          @ModelAttribute("habitacion") Habitacion capacidad,
                          @ModelAttribute("reserva") Reserva fecha, Model model,
                          @RequestParam(name="page", defaultValue = "0") int page) {

        //Configuración de la paginación
        Pageable pageRequest = PageRequest.of(page, 5);

        model.addAttribute("titulo", "Buscar - Travel Planet");

        //Buscar habitaciones que cumplan con los filtros.
        Page<Habitacion> habitacions = habitacionService.buscarHabitacionesPages(id, capacidad.getCapacidad(), fecha.getFechaInicio(), fecha.getFechaFin(),pageRequest);
        PageRender<Habitacion> pageRender = new PageRender<>("/hoteles/habitacion/"+id,habitacions);


        Hotel hotel = hotelService.hotelID(id);
        model.addAttribute("page",pageRender);
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitacions", habitacions);
        return "hoteldetalle";
    }

    @GetMapping("/hoteles/habitacionfiltro/{id}")
    public String habitacionfiltro(@PathVariable(name = "id") Integer id,
                                   @ModelAttribute("habitacion") Habitacion capacidad,
                                   @ModelAttribute("reserva") Reserva fecha,
                                   @ModelAttribute("habfiltro") Habitacion habitacion,
                                   @ModelAttribute("puntuacion") Hotel puntuacion,
                                   Model model,
                                   @RequestParam(name="page", defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        model.addAttribute("titulo", "Buscar - Travel Planet");
        Page<Habitacion> habitacions = habitacionService.habitacionfiltro(id, fecha.getFechaInicio(), fecha.getFechaFin(), capacidad.getCapacidad(),habitacion.isWifi(), habitacion.isTerraza(), habitacion.isTv(), habitacion.isAireAcondicionado()
        , habitacion.isBanioPrivado(), habitacion.isCocina(), habitacion.isCajaFuerte(), habitacion.getPrecioBase(), puntuacion.getEstrellas(),pageRequest);
        PageRender<Habitacion> pageRender = new PageRender<>("/hoteles/habitacion/"+id,habitacions);
        Hotel hotel = hotelService.hotelID(id);
        model.addAttribute("page",pageRender);
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitacions", habitacions);
        return "hoteldetalle";
    }


    @GetMapping("/hoteles/listarHoteles")
 public String hoteles(@RequestParam(name="page", defaultValue = "0") int page, Model model){
//        List<Hotel> hoteles = hotelService.verTodosHoteles();
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Hotel> hotel = hotelService.findAll(pageRequest);
        PageRender<Hotel> pageRender = new PageRender<>("/hoteles/listarHoteles",hotel);
        model.addAttribute("hoteles", hotel);
        model.addAttribute("page",pageRender);
    return "hoteles";
    }

    @RequestMapping("/hoteles/BuscarPorCiudad/{ciudad}")
    public String hotelPorCiudad(Model model, @PathVariable String ciudad){
    List<Hotel> hotelesCiudad = hotelService.hotelPorCiudad(ciudad);
    model.addAttribute("hotelesCiudad", hotelesCiudad);
    return "HotelesPorCiudad";
    }

        @RequestMapping("/hoteles/BuscarPorId/{id}")
        public String hotelPorId(Model model, @PathVariable int id){
            Hotel hotelesId = hotelService.hotelID(id);
            model.addAttribute("hotelesId", hotelesId);
            return "HotelesId";
        }

        @GetMapping("/hoteles/nuevo")
        public String mostrarHotelNuevo(Model model){
        Hotel hotel = new Hotel();
        model.addAttribute("hotel",hotel);
        model.addAttribute("titulo","Nuevo Hotel");
            return "hotelNuevo";

        }

        @PostMapping("/hoteles/nuevo")
        public String hotelNuevo(@Valid @ModelAttribute("hotel") Hotel hotel,BindingResult result,Model model, Authentication auth, @RequestParam(value = "file")MultipartFile imagen, RedirectAttributes flash){

            if (result.hasErrors()) {
                model.addAttribute("titulo", "Ha habido algún error");
                return "/hoteles/nuevo";
            }

        hotel.setUsuario(usuarioService.buscarPorMail(auth.getName()));
        if(hotel.getEstrellas() == null){
            hotel.setEstrellas(3);
        }
            if(imagen.isEmpty()){
                hotel.setImagen("https://t3.ftcdn.net/jpg/01/06/85/86/360_F_106858608_EuOWeiATyMOD6b9cXNzcJDSZufLbojQs.jpg");
            }
        hotelService.hotelGuardar(imagen,flash,hotel);
        return "redirect:/tarifa/nuevo/"+hotel.getId();

        }

//        @GetMapping("/nuevo/{id}")
//        public String mostrarHotelNuevo(Model model, @PathVariable Integer id){
//            Hotel hotel = new Hotel();
//            hotel.setUsuario( usuarioRepository.findById(id).get());
//
//            model.addAttribute("hotel",hotel);
//            return "hotelNuevo";
//
//        }
//
//        @PostMapping("/nuevo")
//        public String hotelNuevo(@ModelAttribute("hotel") Hotel hotel){
//            hotelService.crearHotel(hotel.getNombre(),hotel.getPuntuacion(), hotel.getPrecio(),hotel.getComentario(),hotel.getImagen(),hotel.getLugar(),hotel.getTelefono(),hotel.getCif(),hotel.getNumero_habitaciones(),hotel.getCiudad(), hotel.getUsuario().getId());
//            return "redirect:/hoteles/verHotelesUsuarios";
//        }

        @GetMapping("/hoteles/editar/{id}")
        public String mostrarFormrHotelEditar(@PathVariable int id, Model model){
        model.addAttribute("hotel", hotelService.hotelID(id));
        return "hotelEditar";
        }

        @PostMapping("/hoteles/editar/{id}")
        public String mostrarHotelEditar(@PathVariable int id, @ModelAttribute("hotel") Hotel hotel,Model model, @RequestParam(value = "file")MultipartFile imagen, RedirectAttributes flash){
        Hotel hotelEditar = hotelService.hotelID(id);
        hotelEditar.setId(id);
        hotelEditar.setNombre(hotel.getNombre());
            hotelEditar.setCiudad(hotel.getCiudad());
            hotelEditar.setLugar(hotel.getLugar());
            hotelEditar.setNumero_habitaciones(hotel.getNumero_habitaciones());
            hotelEditar.setPrecio(hotel.getPrecio());
            hotelEditar.setImagen(hotel.getImagen());
            hotelEditar.setTelefono(hotel.getTelefono());
            hotelEditar.setCif(hotel.getCif());
            hotelEditar.setComentario(hotel.getComentario());
            if(hotel.getEstrellas() == null){
                hotel.setEstrellas(hotelEditar.getEstrellas());
            }
            hotelEditar.setEstrellas(hotel.getEstrellas());

            hotelService.cargarImagen(imagen, flash, hotel);

            if(hotel.getImagen() == null){
                hotelEditar.setImagen(hotelService.imagenHotel(hotel.getId()));
                hotelService.hotelEditar(imagen, flash, hotelEditar);
            }else {
                hotelService.hotelEditar(imagen, flash, hotelEditar);
            }
            return "redirect:/perfil/mis-hoteles";
        }



        @RequestMapping("/hoteles/eliminarPorId/{id}")
        public String eliminarPorId( @PathVariable int id){
           hotelService.hotelEliminar(id);

            return "redirect:/perfil/mis-hoteles";
        }

        @GetMapping("/hoteles/verHotelesUsuarios")
        public String listarHotelesUsuario(Model model, Authentication auth){
            auth = SecurityContextHolder.getContext().getAuthentication();
            List<Hotel> hoteles = hotelService.hotelesMail(auth.getName());
            model.addAttribute("hoteles",hoteles);

            return "hotelesUsuario";
        }



        @GetMapping("/reserva/crear/{id}")
        public String crearReserva(Model model, @PathVariable Integer id, Authentication auth,
                                   @ModelAttribute("reserva") Reserva fecha, HttpSession session) throws ParseException {

//            if (!model.containsAttribute("reserva")){
//                return "index";
//            }

            session.setAttribute("idHabitacion",id);
            auth= SecurityContextHolder.getContext().getAuthentication();
            Reserva reserva = new Reserva();
            Habitacion habitacion = habitacionService.findById(id);
            reserva.setHabitacion(habitacion);
            Usuario usuario = usuarioService.usuarioPorNombre(auth.getName());
            reserva.setUsuario(usuario);

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechauno = (Date)session.getAttribute("fi");
            Date fechafinal = (Date)session.getAttribute("ff");


            long diasBuscados = 0;
            try {
                long fechaInicio = fechauno.getTime();;
                long fechaFin = fechafinal.getTime();
                long timeDiff = Math.abs(fechaInicio - fechaFin);
                diasBuscados = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS) + 1;
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            TemporadaHotel temporadaHotel = new TemporadaHotel();

            String bajaIn1 = "2022-11-01";
            String bajaFin1 = "2023-03-31";
            Date bajaIn = formato.parse(bajaIn1);
            Date bajaFin = formato.parse(bajaFin1);

            String mediaIn1 = "2023-04-01";
            String mediaFin1 = "2023-06-30";
            Date mediaIn = formato.parse(mediaIn1);
            Date mediaFin = formato.parse(mediaFin1);

            String altaIn1 = "2023-07-01";
            String altaFin1 = "2023-09-30";
            Date altaIn = formato.parse(altaIn1);
            Date altaFin = formato.parse(altaFin1);

           if ((fechauno.after(bajaIn) || fechauno.equals(bajaIn)) && (fechafinal.equals(bajaFin) || fechafinal.before(bajaFin))){
               temporadaHotel.setTemporada(Temporada.Baja);
           }

            if ((fechauno.after(mediaIn) || fechauno.equals(mediaIn)) && (fechafinal.equals(mediaFin) || fechafinal.before(mediaFin))){
                temporadaHotel.setTemporada(Temporada.Media);
            }

            if ((fechauno.after(altaIn) || fechauno.equals(altaIn)) && (fechafinal.equals(altaFin) || fechafinal.before(altaFin))){
                temporadaHotel.setTemporada(Temporada.Alta);
            }


            PensionHotel pensionHotel = new PensionHotel();
            //pensionHotel.setTarifa(habitacion.getHotel().getTarifa());

            Tarifa miTarifa = habitacion.getHotel().getTarifa();
            session.setAttribute("tarifa",miTarifa);

            Double precioPension = (Double) session.getAttribute("precioPension");
            if (precioPension != null){
                model.addAttribute("precioPension",precioPension);
            }
            Double precioTemporada = pensionService.precioTemporada(temporadaHotel.getTemporada(),miTarifa);

            Double precioSumarTem =  precioTemporada * diasBuscados;
            Double precioBaseDias = habitacion.getPrecioBase() * diasBuscados;

            Double p1 = Double.valueOf(0);
            Double p2 = Double.valueOf(0);
            Double p3 = Double.valueOf(0);
            Double p4 = Double.valueOf(0);
            Double p5 = Double.valueOf(0);
            Double p6 = Double.valueOf(0);
            Double p7 = Double.valueOf(0);

            if ((miTarifa.getPrecioAire() != 0 || miTarifa.getPrecioAire() != null) && habitacion.isAireAcondicionado() == true ){ p1 = miTarifa.getPrecioAire() * diasBuscados;}
            if ((miTarifa.getPrecioBanio() != 0 || miTarifa.getPrecioBanio() != null ) && habitacion.isBanioPrivado() == true ){ p2 = miTarifa.getPrecioBanio() * diasBuscados;}
            if ((miTarifa.getPrecioCajaFuerte() != 0 || miTarifa.getPrecioCajaFuerte() != null) && habitacion.isCajaFuerte() == true ){ p3 = miTarifa.getPrecioCajaFuerte() * diasBuscados;}
            if ((miTarifa.getPrecioCocina() != 0 || miTarifa.getPrecioCocina() != null ) && habitacion.isCocina() == true ){ p4 = miTarifa.getPrecioCocina() * diasBuscados;}
            if ((miTarifa.getPrecioTerraza() != 0 || miTarifa.getPrecioTerraza() != null ) && habitacion.isTerraza() == true ){ p5 = miTarifa.getPrecioTerraza();}
            if ((miTarifa.getPrecioTV() != 0 || miTarifa.getPrecioTV() != null ) && habitacion.isTv() == true ){ p6 = miTarifa.getPrecioTV() * diasBuscados;}
            if ((miTarifa.getPrecioWifi() != 0 || miTarifa.getPrecioWifi() != null) && habitacion.isWifi() == true ){ p7 = miTarifa.getPrecioWifi() * diasBuscados;}

            Double precio_p = pensionService.precioPension(pensionHotel.getPension(),miTarifa);

            habitacion.setPrecioBase(precioBaseDias + precioSumarTem + p1 + p2 + p3 + p4 + p5 + p6 + p7 );

            Double precio = habitacion.getPrecioBase();

            model.addAttribute("precio", precio);
            model.addAttribute("titulo","Crear Reserva");
            model.addAttribute("reserva",reserva);
            model.addAttribute("habitacion",habitacion);
            model.addAttribute("usuario",usuario);
            model.addAttribute("dias",diasBuscados);
            model.addAttribute("pensionHotel",pensionHotel);
            return "crearReserva";
        }


        @PostMapping("/reserva/nuevo/{id}")
        public String guardarReserva( Model model,@PathVariable Integer id, Authentication authentication,
                                      @ModelAttribute("reserva") Reserva fecha, @ModelAttribute("precio") Double precio, HttpSession session) throws ParseException {

            authentication= SecurityContextHolder.getContext().getAuthentication();
            Reserva reserva = new Reserva();
            reserva.setPrecio_total(precio);
            Habitacion habitacion = habitacionService.findById(id);

            reserva.setHabitacion(habitacion);
            Usuario usuario = usuarioService.datosUsuario(authentication.getName());
            reserva.setUsuario(usuario);
            habitacion.getPrecioBase();
            reserva.setPagado(false);
            habitacionService.editarDisponibilidad(false, habitacion.getId());

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha_inicio = (Date)session.getAttribute("fi");
            Date fecha_fin = (Date)session.getAttribute("ff");
           reserva.setFechaInicio(fecha_inicio);
           reserva.setFechaFin(fecha_fin);

           reservaService.guardarReserva(reserva);

           model.addAttribute("reserva", reserva);

            return "redirect:/perfil/mis-reservas";
        }


       @PostMapping("/reserva/crear")
        public String procesarReserva(@Valid PensionHotel pensionHotel, BindingResult result,Model model, HttpSession session){

            EPension ePension = pensionHotel.getPension();
            Tarifa miTarifa = (Tarifa) session.getAttribute("tarifa");

            Double precioPension = pensionService.precioPension(ePension,miTarifa);
            Integer idHabitacion = (Integer) session.getAttribute("idHabitacion");

           session.setAttribute("precioPension",precioPension);

            return "redirect:/reserva/crear/"+idHabitacion;
        }

        //Tarea automatizada con Scheduled. Cuando la fecha fin de reserva de una habitación
        //sobrepase la fecha actual, la habitación debe pasar a estar libre.
        //Se ejecuta a las 10:15 AM cada día.
        @Scheduled(cron = "0 15 10 ? * *")
        public void cambiarEstadoHabitacion(){
            List<Reserva> totalReservas = reservaService.listar();
            Date date = java.sql.Date.valueOf(LocalDate.now());

            for (Reserva reserva:totalReservas) {
                if (reserva.getFechaFin().before(date) && !reserva.getHabitacion().getDisponibilidad()){
                    reserva.getHabitacion().setDisponibilidad(true);
                    habitacionService.editarDisponibilidad(true,reserva.getHabitacion().getId());
                    System.out.println(reserva);

                }
            }



        }


}
