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
    /**
     * Controlador para recuperar y devolver una lista de todos los usuarios.
     *
     * @return Una lista de objetos Usuario
     */
    @RequestMapping(method = RequestMethod.GET, value = "/usuarios")
    @ApiOperation(value = "Listar Usuarios")
    public List<Usuario> listarUsuarios(){
        // Recuperar todos los usuarios de la base de datos y devolverlos como una lista
        return usuarioRepository.findAll();
    }


    /**
     * Controlador para crear un nuevo usuario.
     *
     * @param usuario Un objeto Usuario que representa al usuario a crear
     * @return El usuario creado y guardado en la base de datos
     */
    @RequestMapping(method = RequestMethod.POST, value = "/nuevo/usuario")
    @ApiOperation(value = "Crear usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario nuevoUsuario(@RequestBody Usuario usuario){

        // Codificar la contraseña del usuario utilizando passwordEncoder
        String bcryptPassword = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(bcryptPassword);

        // Establecer el usuario como registrado y su descuento en null
        usuario.setRegistrado(true);
        usuario.setDescuento(null);

        // Crear un objeto UserAuth y guardarlo en la base de datos
        UserAuth auth = new UserAuth();
        auth.setUsername(usuario.getEmail());
        auth.setPassword(usuario.getContrasenia());
        auth.setEnabled(1);
        userAuthRepository.save(auth);

        // Crear un objeto Authorities y asignar un rol al usuario en función de si es un hotelero o no
        Authorities authorities = new Authorities();

        if(usuario.getEsHotelero()){
            authorities.setAuthority(ERoles.ROLE_ADMIN.toString());
        }else {
            authorities.setAuthority(ERoles.ROLE_USER.toString());
        }

        // Establecer el usuario en el objeto Authorities y guardarlo en la base de datos
        authorities.setUser(auth);
        authoritiesRepository.save(authorities);

        // Devolver el usuario actualizado
        return usuarioRepository.save(usuario);
    }


    /**
     * Controlador para editar un usuario existente.
     *
     * @param idUsuario El ID del usuario a editar
     * @param usuario Un objeto Usuario que contiene los nuevos valores para el usuario
     */
    @RequestMapping(method = RequestMethod.POST, value = "/editar/usuario")
    @ApiOperation(value = "Crear usuario")
    public void editarUsuario(@RequestParam Integer idUsuario, @RequestBody Usuario usuario){
        // Crear un nuevo objeto Usuario, pero no se realiza ninguna acción con él
        Usuario editarUsuario = new Usuario();
    }

    // Métodos de Habitación.
    /**
     * Controlador para recuperar y devolver una lista de todas las habitaciones de un hotel determinado.
     *
     * @param idHotel El ID del hotel
     * @return Una lista de objetos Habitacion
     */
    @RequestMapping(method = RequestMethod.GET, value = "/listarhab")
    @ApiOperation(value = "Listar Habitaciones")
    public List<Habitacion> listarHabitaciones(@RequestParam Integer idHotel){
        // Recuperar todas las habitaciones del hotel con el ID dado y devolverlas como una lista
        return habitacionService.listarHabitaciones(idHotel);
    }


    /**
     * Controlador para crear una nueva habitación en un hotel determinado.
     *
     * @param idHotel El ID del hotel
     * @param habitacion Un objeto Habitacion que representa la habitación a crear
     * @return Un objeto ResponseEntity que contiene un mensaje y la habitación creada
     */
    @RequestMapping(method = RequestMethod.POST, value = "/crearhab")
    @ApiOperation(value = "Crear Habitación")
    public ResponseEntity<?> crearHabSwagger(@RequestParam Integer idHotel, @RequestBody Habitacion habitacion){

        // Crear un mapa para almacenar el mensaje y la habitación creada
        Map<String,Object> response = new HashMap<>();

        try {
            // Comprobar si el hotel con el ID dado existe en la base de datos y establecer su ID en el objeto Habitacion
            habitacion.setHotel(hotelRepository.findById(idHotel).get());
        }catch (Exception e){
            // Si no se puede establecer el ID del hotel en la habitación, devolver un mensaje de error
            response.put("mensaje","No se pudo establecer el id de hotel a la habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        // Crear una variable para comprobar si ya existe una habitación con el mismo número en el hotel
        Integer comprobarNumHab = null;

        try{
            // Comprobar si ya existe una habitación con el mismo número en el hotel
            comprobarNumHab = habitacionService.comprobarNumHab(habitacion.getNumeroHabitacion(),idHotel);
        }catch (Exception e){
            // Si ocurre un error al comprobar el número de habitación, devolver un mensaje de error
            response.put("mensaje","Error al comprobar el número de habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comprobarNumHab !=null){
            response.put("mensaje","Ya existe una habitación con ese número");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }

        try{
            // Establecer el Precio Base de la Habitación
            habitacion.setPrecioBase(habitacionService.establecerPrecioHabitacion(habitacion.getPrecioBase(), habitacion));
        }catch (Exception e){
            response.put("mensaje","Error al establecer el precio de la habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            // Guardar la Habitación en la BBDD
            habitacionRepository.save(habitacion);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer el insert en la BBDD");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","La habitación ha sido creada con éxito");
        response.put("habitación",habitacion);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    /**
     *  Método para actualizar una habitación.
     *
     * @param idHabitacion
     * @param habitacion
     * @return Un objeto ResponseEntity que contiene un mensaje y la habitación actualizada.
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/updatehab")
    @ApiOperation(value = "Actualizar Habitación")
    public ResponseEntity<?> actualizarHabSwagger(@RequestParam Integer idHabitacion, @RequestBody Habitacion habitacion){

        // Crea un mapa de respuesta para almacenar el mensaje y la habitación
        Map<String,Object> response = new HashMap();

        // Inicializa la habitación que se va a actualizar
        Habitacion habActualizar = null;

        try{
            // Busca la habitación por su id
            habActualizar = habitacionService.findById(idHabitacion);
        }catch (Exception e){
            // Si hay un error al buscar la habitación, devuelve un mensaje de error
            response.put("mensaje","Error al encontrar el ID de habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Actualiza los atributos de la habitación
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
            // Guarda la habitación actualizada en la base de datos
            habitacionService.guardarHabitacion(habActualizar);
        }catch (DataAccessException e){
            // Si hay un error al guardar la habitación, devuelve un mensaje de error
            response.put("mensaje","Error al guardar la habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","Habitación actualizada con éxito");
        response.put("habitacion",habActualizar);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

    }

    /**
     * Método para borrar una habitación.
     *
     * @param idHabitacion Id de la habitación a borrar.
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/borrarhab")
    @ApiOperation(value = "Borrar Habitación")
    public ResponseEntity<?> borrarHabSwagger(@RequestParam Integer idHabitacion){

        // Crea un mapa de respuesta para almacenar el mensaje
        Map<String,Object> response = new HashMap<>();

        // Inicializa la habitación
        Habitacion habitacion = null;

        try{
            // Busca la habitación por su id
            habitacion = habitacionService.findById(idHabitacion);
        }catch (DataAccessException e){
            // Si hay un error al buscar la habitación, devuelve un mensaje de error
            response.put("mensaje","Error al buscar habitacion por el ID");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            // Borra la habitación de la base de datos
            habitacionRepository.delete(habitacion);
        }catch (Exception e){
            // Si hay un error al borrar la habitación, devuelve un mensaje de error
            response.put("mensaje","Error al intentar eliminar la habitación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Si se ha borrado correctamente, devuelve un mensaje indicando que se ha borrado con éxito
        response.put("mensaje","Habitación eliminada con éxito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }


    // Métodos de Reserva.

    /**
     * Método para listar las reservas de un usuario.
     *
     * @param auth Autenticación del usuario para ver las reservas del mismo.
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/verreservas")
    @ApiOperation(value = "Ver Reservas")
    @SecurityRequirement(name = "")
    public ResponseEntity<?> verReservasSwagger(Authentication auth){

        // Crea un mapa de respuesta para almacenar el mensaje y las reservas
        Map<String,Object> response = new HashMap<>();

        // Inicializa la lista de reservas
        List<Reserva> reservas = null;

        try{
            // Obtiene la autenticación del usuario actual
            auth = SecurityContextHolder.getContext().getAuthentication();
        }catch (Exception e){
            // Si hay un error al obtener la autenticación, devuelve un mensaje de error
            response.put("mensaje","Error al obtener la autenticación");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            // Obtiene las reservas del usuario actual
            reservas = reservaService.reservasPorNombre(auth.getName());
        }catch (Exception e){
            // Si hay un error al obtener las reservas, devuelve un mensaje de error
            response.put("mensaje","Error al intentar obtener las reservas");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Si se han obtenido las reservas correctamente, las devuelve en la respuesta
        response.put("reservas",reservas);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }


    /**
     * Método para la búsqueda de hoteles.
     *
     * @param ciudad
     * @param fechaInicio
     * @param fechaFin
     * @param capacidad
     * @return Lista de Hoteles recibidos al hacer la consulta.
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/busquedahoteles")
    public List<Hotel> busquedahoteles(@RequestParam String ciudad,@RequestParam String fechaInicio,@RequestParam String fechaFin,@RequestParam Integer capacidad) throws ParseException {
        // Crea un formato de fecha para parsear las fechas
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        // Parsea las fechas de inicio y fin
        Date fecha_inicio = formato.parse(fechaInicio);
        Date fecha_fin = formato.parse(fechaFin);

        // Busca los hoteles en la base de datos usando los parámetros de consulta
        List<Hotel> hoteles = hotelRepository.buscador(ciudad, fecha_inicio, fecha_fin, capacidad);

        // Devuelve la lista de hoteles encontrados
        return hoteles;
    }

    /**
     * Método para listar todos los Hoteles.
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/listarhoteles")
    public List<Hotel> listarhoteles()  {
        return hotelRepository.findAll();
    }

    /**
     * Método para guardar un Hotel.
     *
     * @param id_usuario
     * @param hotel
     * @return El Hotel guardado en BBDD.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/crearhoteles")
    public Hotel crearHotel(@RequestParam Integer id_usuario, @RequestBody Hotel hotel)  {
        // Obtiene el usuario por su id
        Usuario usuario = usuarioRepository.datosUsuarioID(id_usuario);

        // Asigna el usuario al hotel
        hotel.setUsuario(usuario);

        // Guarda el hotel en la base de datos y devuelve el hotel guardado
        return hotelRepository.save(hotel);
    }

    /**
     * Método para crear una nueva reserva.
     *
     * @param idUsuario
     * @param idHabitacion
     * @param reserva
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/crearReserva")
    public void crearReserva(@RequestParam Integer idUsuario, @RequestParam Integer idHabitacion, @RequestBody Reserva reserva) throws ParseException {
        // Obtiene el usuario y la habitación por su id
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Habitacion habitacion = habitacionRepository.findById(idHabitacion).orElse(null);

        // Asigna el usuario y la habitación a la reserva
        reserva.setUsuario(usuario);
        reserva.setHabitacion(habitacion);

        // Crea un formato de fecha para formatear las fechas de inicio y fin de la reserva
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        // Formatea las fechas de inicio y fin
        String fecha_inicio = formato.format(reserva.getFechaInicio());
        String fecha_fin = formato.format(reserva.getFechaFin());

        // Parsea las fechas formateadas
        Date fi = formato.parse(fecha_inicio);
        Date ff = formato.parse(fecha_fin);

        // Asigna las fechas parseadas a la reserva
        reserva.setFechaInicio(fi);
        reserva.setFechaFin(ff);

        // Guarda la reserva en la base de datos
        reservaService.guardarReserva(reserva);
    }

    /**
     * Método para crear una nueva Tarifa.
     *
     * @param idHotel
     * @param tarifa
     * @return La Tarifa creada.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/creartarifa")
    public Tarifa crearTarifa(@RequestParam Integer idHotel, @RequestBody Tarifa tarifa){
        // Obtiene el hotel por su id
        Hotel hotel = hotelRepository.obtenerHotelId(idHotel);

        // Asigna el hotel a la tarifa
        tarifa.setHotel(hotel);

        // Si no hay una tarifa para el hotel, la guarda en la base de datos
        if ( tarifaRepository.listarTarifa(idHotel) == null ){
            tarifaRepository.guardarTarifa(tarifa.getPrecioBanio(), tarifa.getPrecioCajaFuerte(), tarifa.getPrecioCocina(), tarifa.getPrecioTV(), tarifa.getPrecioTerraza(), tarifa.getPrecioWifi(), tarifa.getPrecioAire(), idHotel);
        }

        // Devuelve la tarifa
        return tarifa;
    }

    /**
     * Método para crear una nueva Pensión.
     *
     * @param idTarifa
     * @param pension El tipo de pensión.
     * @param pensiones
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/crearpensiones")
    public PensionHotel crearPensiones(@RequestParam Integer idTarifa, @RequestParam EPension pension, @RequestBody PensionHotel pensiones){
        // Obtiene la tarifa por su id
        Tarifa tarifa = tarifaRepository.findById(idTarifa).orElse(null);

        // Asigna la tarifa a la pensión
        pensiones.setTarifa(tarifa);

        // Si hay una tarifa con el id especificado y no hay una pensión para esa tarifa con el tipo especificado, guarda la pensión en la base de datos
        if ( tarifaRepository.tarifaSwagger(idTarifa) != null && tarifaRepository.pensionSwagger(idTarifa,pension.ordinal()) == null){
            tarifaRepository.guardarPension(pension.ordinal(), pensiones.getPrecio(),tarifa.getId());
        }

        // Devuelve la pensión
        return pensiones;
    }

    /**
     * Método crear Tarifa Temporada.
     *
     * @param idTarifa
     * @param temporada
     * @param temporadas
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/creartemporadas")
    public TemporadaHotel crearTemporadas(@RequestParam Integer idTarifa, @RequestParam Temporada temporada, @RequestBody TemporadaHotel temporadas){
        // Obtiene la tarifa por su id
        Tarifa tarifa = tarifaRepository.findById(idTarifa).orElse(null);

        // Asigna la tarifa a la temporada
        temporadas.setTarifa(tarifa);

        // Si hay una tarifa con el id especificado y no hay una temporada para esa tarifa con el tipo especificado,
        // guarda la temporada en la base de datos
        if (tarifaRepository.tarifaSwagger(idTarifa) != null && tarifaRepository.temporadaSwagger(idTarifa, temporada.ordinal()) == null){
            tarifaRepository.guardarTemporada(temporada.ordinal(),temporadas.getFechaInicio(),temporadas.getFechaFin(),temporadas.getPrecio(),idTarifa);
        }

        return  temporadas;
    }

    /**
     * Método para borrar Reserva.
     *
     * @param idUsuario
     * @param reserva
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/borrarreserva")
    public void borrarReserva(@RequestParam Integer idUsuario, @RequestBody Reserva reserva){
        // Obtiene todas las reservas para un usuario específico
        List<Reserva> reserva1 = reservaService.obtenerReservaUsuario(idUsuario);

        // Cancela todas las reservas del usuario
        for (Reserva r: reserva1){
            reservaService.cancelarReserva(r);
        }
    }


    /**
     * Método para borrar Temporada.
     *
     * @param idTarifa
     * @param temporada
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/borrartemporada")
    public void borrarTemporada(@RequestParam Integer idTarifa, @RequestParam Temporada temporada){

        tarifaRepository.borrarTemporadaSwagger(idTarifa, temporada.ordinal());

    }


    /**
     * Método para borrar Pensión.
     *
     * @param idTarifa
     * @param pension
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/borrarpension")
    public void borrarPension(@RequestParam Integer idTarifa, @RequestParam EPension pension){

            tarifaRepository.borrarPensionSwagger(idTarifa, pension.ordinal());
    }


    /**
     * Método para borrar Hotel.
     *
     * @param id_hotel
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/borrarhotel")
    public void borrarHotel(@RequestParam Integer id_hotel)  {
        // Obtiene la tarifa del hotel
        Tarifa tarifa = tarifaRepository.tarifaIdhotel(id_hotel);

        // Si hay una tarifa para el hotel, la elimina
        if(tarifa != null){
            tarifaRepository.delete(tarifa);
        }

        // Obtiene las habitaciones del hotel
        List<Habitacion> habitacion = habitacionRepository.listarHabitaciones(id_hotel);

        // Si hay habitaciones para el hotel, las elimina
        if(habitacion != null){
            for(Habitacion h: habitacion){
                habitacionRepository.delete(h);
            }
        }

        // Elimina el hotel
        hotelRepository.deleteById(id_hotel);
    }

    /**
     * Método para actualizar Tarifa.
     *
     * @param idHotel
     * @param tarifa
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/actualizarTarifaCaracteristicas")
    public Tarifa actualizarTarifaCaracteristicas(@RequestParam Integer idHotel, @RequestBody Tarifa tarifa){
        // Obtiene la tarifa a editar del hotel
        Tarifa tarifaEditar = tarifaRepository.listarTarifa(idHotel);

        // Establece los nuevos precios en la tarifa
        tarifaEditar.setPrecioWifi(tarifa.getPrecioWifi());
        tarifaEditar.setPrecioAire(tarifa.getPrecioAire());
        tarifaEditar.setPrecioTerraza(tarifa.getPrecioTerraza());
        tarifaEditar.setPrecioCocina(tarifa.getPrecioCocina());
        tarifaEditar.setPrecioTV(tarifa.getPrecioTV());
        tarifaEditar.setPrecioBanio(tarifa.getPrecioBanio());
        tarifaEditar.setPrecioCajaFuerte(tarifa.getPrecioCajaFuerte());

        // Guarda la tarifa actualizada y la devuelve
        return tarifaRepository.save(tarifaEditar);
    }

    /**
     * Método para actualizar la Temporada.
     *
     * @param idTarifa
     * @param temporada
     * @param temporadaHotel
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/actualizarTarifaTemporada")
    public void actualizarTarifaTemporada(@RequestParam Integer idTarifa, @RequestParam Temporada temporada, @RequestBody TemporadaHotel temporadaHotel) throws ParseException {
        // Se crea un formato de fecha para convertir las fechas de inicio y fin
        // de la temporada del hotel a cadenas en el formato "yyyy-MM-dd"
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_inicio = formato.format(temporadaHotel.getFechaInicio());
        String fecha_fin = formato.format(temporadaHotel.getFechaFin());

        // Se convierten las fechas en cadena al formato de fecha
        Date fi = formato.parse(fecha_inicio);
        Date ff = formato.parse(fecha_fin);

        // Se actualiza la temporada en la base de datos usando el repositorio
        // "tarifaRepository" y los valores proporcionados
        tarifaRepository.modificarTemporada(fi,ff, temporadaHotel.getPrecio(), idTarifa, temporada.ordinal());
    }

    /**
     * Método para actualizar la Pensión.
     *
     * @param idTarifa
     * @param pension
     * @param pensionHotel
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/actualizarTarifaPension")
    public void actualizarTarifaPension(Integer idTarifa, @RequestParam EPension pension, @RequestBody PensionHotel pensionHotel){
        tarifaRepository.modificarPension(pensionHotel.getPrecio(),idTarifa,pension.ordinal());

    }

    /**
     * Método para actualizar Hoteles.
     *
     * @param id_hotel
     * @param hotel
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/actualizarhotel")
    public Hotel actualizarHotel(@RequestParam Integer id_hotel, @RequestBody Hotel hotel){
        // Se obtiene el hotel a editar usando el repositorio "hotelRepository" y el ID del hotel
        Hotel hotelEditar = hotelRepository.obtenerHotelId(id_hotel);

        // Se guarda el usuario del hotel a editar
        Usuario usuario = hotelEditar.getUsuario();

        // Se establecen los nuevos valores del hotel a editar
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

        // Se guardan los cambios en la base de datos usando el repositorio "hotelRepository"
        return hotelRepository.save(hotel);
    }
    



}
