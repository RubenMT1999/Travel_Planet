package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Tarifa;
import com.example.booking.models.Usuario;
import com.example.booking.repository.HotelRepository;
import com.example.booking.repository.TarifaRepository;
import com.example.booking.repository.UsuarioRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.RequestParameter;

import javax.servlet.annotation.HttpConstraint;
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



    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    //Swagger Busqueda
    @RequestMapping(method = RequestMethod.GET, value = "/busquedahoteles")
    public List<Hotel> hoteles(@RequestParam String ciudad,@RequestParam String fechaInicio,@RequestParam String fechaFin,@RequestParam Integer capacidad) throws ParseException {
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
    @RequestMapping(method = RequestMethod.POST, value = "/borrarhotel")
    public void borrarHotel(@RequestParam Integer id_hotel)  {
        hotelRepository.deleteById(id_hotel);
    }

    //Swagger actualizar hoteles.








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
