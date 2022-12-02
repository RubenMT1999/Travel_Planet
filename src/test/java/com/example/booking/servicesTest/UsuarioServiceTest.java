package com.example.booking.servicesTest;

import com.example.booking.CreacionObjetosFaker;
import com.example.booking.models.Usuario;
import com.example.booking.repository.UsuarioRepository;
import com.example.booking.services.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)

public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    public static List<Usuario> usuario;


    @BeforeAll
    public static void cargarDatos(){usuario = new CreacionObjetosFaker().fakerUsuario(4);}


    @Test
    @DisplayName("Test 1 -> UsuarioService -> Crear Usuario" )
    void crearUsuario(){


        usuarioService.save(usuario.get(2));
        verify(usuarioRepository, times(1)).save(usuario.get(2));
    }

    @Test
    @DisplayName("Test 2 -> UsuarioService -> Borrar Usuario")
    void borrarUsuario(){


        doAnswer(i->{
        Usuario us1 = i.getArgument(0);
            verify(usuarioRepository,times(1)).delete(us1);
            return null;

        }).when(usuarioRepository).delete(any(Usuario.class));
        usuarioService.borrarUsuario(usuario.get(1));
    }

    @Test
    @DisplayName("Test 3 -> UsuarioService -> Buscar por Id")
    void buscarUsuarioID(){

        when(usuarioRepository.findById(2)).thenReturn(Optional.ofNullable(usuario.get(0)));
        Usuario us1 = usuarioService.usuarioPorId(2);
        assertEquals(us1,usuario.get(0));
    }

    @Test
    @DisplayName("Test 4 -> UsuarioService -> Editar usuario")
    void EditarUsuaro() throws ParseException {
        doAnswer(i->{
            String arg0 = i.getArgument(0);
            String arg1 = i.getArgument(1);
            String arg2 = i.getArgument(2);
            Date arg3 = i.getArgument(3);
            String arg4 = i.getArgument(4);
            String arg5 = i.getArgument(5);
            Integer arg6 = i.getArgument(6);



            verify(usuarioRepository,times(1)).editarUsuario(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
            return null;
        }).when(usuarioRepository).editarUsuario(any(String.class),any(String.class),any(String.class),any(Date.class),any(String.class),any(String.class),any(Integer.class));

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_inicio = formato.parse("2011-11-11");



        usuarioService.editarUsuario("Alejandro","Cornejo","1234",fecha_inicio,"89787845F","española","69745821",1);
    }


}
