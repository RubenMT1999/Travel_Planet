package com.example.booking.controllers;

import com.example.booking.models.*;
import com.example.booking.repository.*;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.ReservaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api_swagger")
@ShowApi
public class SwaggerController {


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
    private HotelRepository hotelRepository;

    @Autowired
    private ReservaService reservaService;


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

        usuario.setMetodoDePago(EMetodoDePago.Bizum);
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
           habActualizar = habitacionService.encontrarPorId(idHabitacion);
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
            habitacion = habitacionService.encontrarPorId(idHabitacion);
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


}
