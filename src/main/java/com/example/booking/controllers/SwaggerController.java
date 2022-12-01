package com.example.booking.controllers;

import com.example.booking.models.*;
import com.example.booking.repository.*;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.ReservaService;
import com.example.booking.services.TarifaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api_swagger")
@ShowApi
public class SwaggerController {
    @Autowired
    private IPensionRepository pensionRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private HabitacionRepository habitacionRepository;
    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private TarifaService tarifaService;


    // Métodos de Usuario.
    @RequestMapping(method = RequestMethod.GET, value = "/usuarios")
    @ApiOperation(value = "Listar Usuarios")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/nuevo/usuario")
    @ApiOperation(value = "Crear usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario nuevoUsuario(@RequestBody Usuario usuario){

        String bcryptPassword = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(bcryptPassword);

        usuario.setRegistrado(true);
        usuario.setDescuento(null);

        UserAuth auth = new UserAuth();
        auth.setUsername(usuario.getEmail());
        auth.setPassword(usuario.getContrasenia());
        auth.setEnabled(1);
        userAuthRepository.save(auth);

        Authorities authorities = new Authorities();

        if(usuario.getEsHotelero()){
            authorities.setAuthority(ERoles.ROLE_ADMIN.toString());
        }else {
            authorities.setAuthority(ERoles.ROLE_USER.toString());
        }

        authorities.setUser(auth);
        authoritiesRepository.save(authorities);
        return usuarioRepository.save(usuario);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editar/usuario")
    @ApiOperation(value = "Crear usuario")
    public void editarUsuario(@RequestParam Integer idUsuario,@RequestBody Usuario usuario){
        Usuario editarUsuario = new Usuario();
    }

    // Métodos de Habitación.
    @RequestMapping(method = RequestMethod.GET, value = "/listarhab")
    @ApiOperation(value = "Listar Habitaciones")
    public List<Habitacion> listarHabitaciones(@RequestParam Integer idHotel){
        return habitacionService.listarHabitaciones(idHotel);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/crearhab")
    @ApiOperation(value = "Crear Habitación")
    public ResponseEntity<?> crearHabSwagger(@RequestParam Integer idHotel, @RequestBody Habitacion habitacion){

        Map<String,Object> response = new HashMap<>();

        try {
            habitacion.setHotel(hotelRepository.findById(idHotel).get());
        }catch (Exception e){
            response.put("mensaje","No se pudo establecer el id de hotel a la habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        Integer comprobarNumHab = null;

        try{
            comprobarNumHab = habitacionService.comprobarNumHab(habitacion.getNumeroHabitacion(),idHotel);
        }catch (Exception e){
            response.put("mensaje","Error al comprobar el número de habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comprobarNumHab !=null){
            response.put("mensaje","Ya existe una habitación con ese número");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }

        try{
            habitacion.setPrecioBase(habitacionService.establecerPrecioHabitacion(habitacion.getPrecioBase(), habitacion));
        }catch (Exception e){
            response.put("mensaje","Error al establecer el precio de la habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            habitacionRepository.save(habitacion);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer el insert en la BBDD");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","La habitación ha sido creada con éxito");
        response.put("habitación",habitacion);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/updatehab")
    @ApiOperation(value = "Actualizar Habitación")
    public ResponseEntity<?> actualizarHabSwagger(@RequestParam Integer idHabitacion, @RequestBody Habitacion habitacion){

        Map<String,Object> response = new HashMap();
        Habitacion habActualizar = null;
        try{
           habActualizar = habitacionService.findById(idHabitacion);
        }catch (Exception e){
            response.put("mensaje","Error al encontrar el ID de habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        habActualizar.setHotel(habitacion.getHotel());
        habActualizar.setTv(habitacion.isTv());
        habActualizar.setTerraza(habitacion.isTerraza());
        habActualizar.setWifi(habitacion.isWifi());
        habActualizar.setCocina(habitacion.isCocina());
        habActualizar.setAireAcondicionado(habitacion.isAireAcondicionado());
        habActualizar.setImagen(habitacion.getImagen());
        habActualizar.setDescuento(habitacion.getDescuento());
        habActualizar.setBanioPrivado(habitacion.isBanioPrivado());
        habActualizar.setCajaFuerte(habitacion.isCajaFuerte());
        habActualizar.setCapacidad(habitacion.getCapacidad());
        habActualizar.setDescripcion(habitacion.getDescripcion());
        habActualizar.setPrecioBase(habitacion.getPrecioBase());
        habActualizar.setDisponibilidad(habitacion.getDisponibilidad());
        habActualizar.setNumeroHabitacion(habitacion.getNumeroHabitacion());
        habActualizar.setExtensionTelefonica(habitacion.getExtensionTelefonica());

        try {
            habitacionService.guardarHabitacion(habActualizar);
        }catch (DataAccessException e){
            response.put("mensaje","Error al guardar la habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","Habitación actualizada con éxito");
        response.put("habitacion",habActualizar);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/borrarhab")
    @ApiOperation(value = "Borrar Habitación")
    public ResponseEntity<?> borrarHabSwagger(@RequestParam Integer idHabitacion){

        Map<String,Object> response = new HashMap<>();
        Habitacion habitacion = null;

        try{
            habitacion = habitacionService.findById(idHabitacion);
        }catch (DataAccessException e){
            response.put("mensaje","Error al buscar habitacion por el ID");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            habitacionRepository.delete(habitacion);
        }catch (Exception e){
            response.put("mensaje","Error al intentar eliminar la habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","Habitación eliminada con éxito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }


    // Métodos de Reserva.
    @RequestMapping(method = RequestMethod.GET, value = "/verreservas")
    @ApiOperation(value = "Ver Reservas")
    @SecurityRequirement(name = "")
    public ResponseEntity<?> verReservasSwagger(Authentication auth){

        Map<String,Object> response = new HashMap<>();
        List<Reserva> reservas = null;

        try{
            auth = SecurityContextHolder.getContext().getAuthentication();
        }catch (Exception e){
            response.put("mensaje","Error al obtener la autenticación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            reservas = reservaService.reservasPorNombre(auth.getName());
        }catch (Exception e){
            response.put("mensaje","Error al intentar obtener las reservas");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("reservas",reservas);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
    //Swagger Busqueda
    @RequestMapping(method = RequestMethod.GET, value = "/busquedahoteles")
    public List<Hotel> busquedahoteles(@RequestParam String ciudad,@RequestParam String fechaInicio,@RequestParam String fechaFin,@RequestParam Integer capacidad) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_inicio = formato.parse(fechaInicio);
        Date fecha_fin = formato.parse(fechaFin);
        List<Hotel> hoteles = hotelRepository.buscador(ciudad, fecha_inicio, fecha_fin, capacidad);
        return hoteles;
    }

    //Swagger todos los hoteles
    @RequestMapping(method = RequestMethod.GET, value = "/listarhoteles")
    public List<Hotel> listarhoteles()  {
        return hotelRepository.findAll();
    }

    //Swagger crear hoteles.
    @RequestMapping(method = RequestMethod.POST, value = "/crearhoteles")
    public Hotel crearHotel(@RequestParam Integer id_usuario, @RequestBody Hotel hotel)  {
        Usuario usuario = usuarioRepository.datosUsuarioID(id_usuario);
        hotel.setUsuario(usuario);
        return hotelRepository.save(hotel);
    }

    //Swagger crear reserva
    @RequestMapping(method = RequestMethod.POST, value = "/crearReserva")
    public void crearReserva(@RequestParam Integer idUsuario, @RequestParam Integer idHabitacion, @RequestBody Reserva reserva) throws ParseException {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        reserva.setUsuario(usuario);
        Habitacion habitacion = habitacionRepository.findById(idHabitacion).orElse(null);
        reserva.setHabitacion(habitacion);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_inicio = formato.format(reserva.getFechaInicio());
        String fecha_fin = formato.format(reserva.getFechaFin());

        Date fi = formato.parse(fecha_inicio);
        Date ff = formato.parse(fecha_fin);

        reserva.setFechaInicio(fi);
        reserva.setFechaFin(ff);




        reservaService.guardarReserva(reserva);


    }

    //Swagger crear tarifa caracteristicas
    @RequestMapping(method = RequestMethod.POST, value = "/creartarifa")
    public Tarifa crearTarifa(@RequestParam Integer idHotel, @RequestBody Tarifa tarifa){
        Hotel hotel = hotelRepository.obtenerHotelId(idHotel);


        tarifa.setHotel(hotel);




        if ( tarifaRepository.listarTarifa(idHotel) == null ){
            tarifaRepository.guardarTarifa(tarifa.getPrecioBanio(), tarifa.getPrecioCajaFuerte(), tarifa.getPrecioCocina(), tarifa.getPrecioTV(), tarifa.getPrecioTerraza(), tarifa.getPrecioWifi(), tarifa.getPrecioAire(), idHotel);

        }



        return tarifa;
    }

    //Swagger crear tarifa pensiones
    @RequestMapping(method = RequestMethod.POST, value = "/crearpensiones")
    public PensionHotel crearPensiones(@RequestParam Integer idTarifa, @RequestParam EPension pension, @RequestBody PensionHotel pensiones){
        Tarifa tarifa = tarifaRepository.findById(idTarifa).orElse(null);

        pensiones.setTarifa(tarifa);

        if ( tarifaRepository.tarifaSwagger(idTarifa) != null && tarifaRepository.pensionSwagger(idTarifa,pension.ordinal()) == null){
        tarifaRepository.guardarPension(pension.ordinal(), pensiones.getPrecio(),tarifa.getId());
        }

        return pensiones;
    }

    //Swagger crear tarifa temporadas
    @RequestMapping(method = RequestMethod.POST, value = "/creartemporadas")
    public TemporadaHotel crearTemporadas(@RequestParam Integer idTarifa, @RequestParam Temporada temporada, @RequestBody TemporadaHotel temporadas){
        Tarifa tarifa = tarifaRepository.findById(idTarifa).orElse(null);

        temporadas.setTarifa(tarifa);

        if (tarifaRepository.tarifaSwagger(idTarifa) != null && tarifaRepository.temporadaSwagger(idTarifa, temporada.ordinal()) == null){
            tarifaRepository.guardarTemporada(temporada.ordinal(),temporadas.getFechaInicio(),temporadas.getFechaFin(),temporadas.getPrecio(),idTarifa);
        }


    return  temporadas;
    }
    //Swagger borrar reserva
    @RequestMapping(method = RequestMethod.DELETE, value = "/borrarreserva")
    public void borrarReserva(@RequestParam Integer idUsuario, @RequestBody Reserva reserva){
        List<Reserva> reserva1 = reservaService.obtenerReservaUsuario(idUsuario);
        for (Reserva r: reserva1){
            reservaService.cancelarReserva(r);
        }

    }


    //Swagger borrar temporada
    @RequestMapping(method = RequestMethod.DELETE, value = "/borrartemporada")
    public void borrarTemporada(@RequestParam Integer idTarifa, @RequestParam Temporada temporada){



        tarifaRepository.borrarTemporadaSwagger(idTarifa, temporada.ordinal());


    }



    //Swagger borrar pension
    @RequestMapping(method = RequestMethod.DELETE, value = "/borrarpension")
    public void borrarPension(@RequestParam Integer idTarifa, @RequestParam EPension pension){



            tarifaRepository.borrarPensionSwagger(idTarifa, pension.ordinal());


    }


    //Swagger borrar hoteles.
    @RequestMapping(method = RequestMethod.DELETE, value = "/borrarhotel")
    public void borrarHotel(@RequestParam Integer id_hotel)  {
        Tarifa tarifa = tarifaRepository.tarifaIdhotel(id_hotel);
        if(tarifa != null){
            tarifaRepository.delete(tarifa);
        }
        List<Habitacion> habitacion = habitacionRepository.listarHabitaciones(id_hotel);
        if(habitacion != null){
            for(Habitacion h: habitacion){
                habitacionRepository.delete(h);
            }
        }
        hotelRepository.deleteById(id_hotel);
    }

    //Swagger actualizar caracteristicas Tarifa
    @RequestMapping(method = RequestMethod.PUT, value = "/actualizarTarifaCaracteristicas")
    public Tarifa actualizarTarifaCaracteristicas(@RequestParam Integer idHotel, @RequestBody Tarifa tarifa){
        Tarifa tarifaEditar = tarifaRepository.listarTarifa(idHotel);
        tarifaEditar.setPrecioWifi(tarifa.getPrecioWifi());
        tarifaEditar.setPrecioAire(tarifa.getPrecioAire());
        tarifaEditar.setPrecioTerraza(tarifa.getPrecioTerraza());
        tarifaEditar.setPrecioCocina(tarifa.getPrecioCocina());
        tarifaEditar.setPrecioTV(tarifa.getPrecioTV());
        tarifaEditar.setPrecioBanio(tarifa.getPrecioBanio());
        tarifaEditar.setPrecioCajaFuerte(tarifa.getPrecioCajaFuerte());
        return tarifaRepository.save(tarifaEditar);
    }

    //Swagger actualizar temporada Tarifa
    @RequestMapping(method = RequestMethod.PUT, value = "/actualizarTarifaTemporada")
    public void actualizarTarifaTemporada(@RequestParam Integer idTarifa, @RequestParam Temporada temporada, @RequestBody TemporadaHotel temporadaHotel) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_inicio = formato.format(temporadaHotel.getFechaInicio());
        String fecha_fin = formato.format(temporadaHotel.getFechaFin());

        Date fi = formato.parse(fecha_inicio);
        Date ff = formato.parse(fecha_fin);

        tarifaRepository.modificarTemporada(fi,ff, temporadaHotel.getPrecio(), idTarifa, temporada.ordinal());

    }

    //Swagger actualizar pension Tarifa
    @RequestMapping(method = RequestMethod.PUT, value = "/actualizarTarifaPension")
    public void actualizarTarifaPension(Integer idTarifa, @RequestParam EPension pension, @RequestBody PensionHotel pensionHotel){
        tarifaRepository.modificarPension(pensionHotel.getPrecio(),idTarifa,pension.ordinal());

    }

    //Swagger actualizar hoteles.
    @RequestMapping(method = RequestMethod.PUT, value = "/actualizarhotel")
    public Hotel actualizarHotel(@RequestParam Integer id_hotel, @RequestBody Hotel hotel){
        Hotel hotelEditar = hotelRepository.obtenerHotelId(id_hotel);
        Usuario usuario = hotelEditar.getUsuario();
        hotelEditar.setId(id_hotel);
        hotelEditar.setNombre(hotel.getNombre());
        hotelEditar.setCiudad(hotel.getCiudad());
        hotelEditar.setLugar(hotel.getLugar());
        hotelEditar.setNumero_habitaciones(hotel.getNumero_habitaciones());
        hotelEditar.setPrecio(hotel.getPrecio());
        hotelEditar.setImagen(hotel.getImagen());
        hotelEditar.setTelefono(hotel.getTelefono());
        hotelEditar.setCif(hotel.getCif());
        hotelEditar.setComentario(hotel.getComentario());
        hotelEditar.setEstrellas(hotel.getEstrellas());
        hotel.setUsuario(usuario);
        hotel.setId(id_hotel);
        return hotelRepository.save(hotel);
    }
    



}
