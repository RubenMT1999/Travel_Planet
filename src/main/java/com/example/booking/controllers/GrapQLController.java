package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Tarifa;
import com.example.booking.models.Usuario;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.HotelRepository;
import com.example.booking.repository.TarifaRepository;
import com.example.booking.repository.UsuarioRepository;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.RequestParameter;

import javax.servlet.annotation.HttpConstraint;
import javax.swing.text.StyledEditorKit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GrapQLController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private HotelService hotelService;



    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
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









//    @PostMapping("")
//    @MutationMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Usuario nuevoUsuario(@RequestBody @Argument(name = "input")GrapqlModels.UsuarioInput input){
//
//        Usuario usuario = new Usuario();
//
//        String bcryptPassword = passwordEncoder.encode(input.getPassword());
//        usuario.setContrasenia(bcryptPassword);
//        usuario.setNombre(input.getNombre());
//        usuario.setApellidos(input.getApellidos());
//        usuario.setFechaNacimiento(input.getFechaNacimiento());
//        usuario.setNacionalidad(input.getNacionalidad());
//        usuario.setDni(input.getDni());
//        usuario.setEmail(input.getEmail());
//        usuario.setEsHotelero(input.getEsHotelero());
//        usuario.setTelefono(input.getTelefono());
//
//        UserAuth auth = new UserAuth();
//        auth.setUsername(usuario.getEmail());
//        auth.setPassword(usuario.getContrasenia());
//        auth.setEnabled(1);
//        userAuthRepository.save(auth);
//
//        Authorities authorities = new Authorities();
//
//        if(usuario.getEsHotelero() == true){
//            authorities.setAuthority(ERoles.ROLE_ADMIN.toString());
//        }else {
//            authorities.setAuthority(ERoles.ROLE_USER.toString());
//        }
//
//        authorities.setUser(auth);
//        authoritiesRepository.save(authorities);
//        return usuarioRepository.save(usuario);
//    }

}
