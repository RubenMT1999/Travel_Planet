package com.example.booking;

import com.example.booking.auth.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

//enableGlobalMehtodSecurity para permitir las anotaciones Secured
//@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private DataSource dataSource;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/scripts/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/uploads/**");
    }

    //Configuracion de quien puede visitar cada url
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //definimos quien va a poder acceder a las distintas direccioens
        http.csrf().disable().authorizeRequests().antMatchers("/","/registrar/**","/styles/**","/images/**","/login","/hoteles/listar/**", "resultado/ver", "/hotel/**", "/generar",
                "/hoteles/habitacion/**","/reserva/crear/**","/authenticate","/swagger-resources/**","/swagger-ui/**","/v3/api-docs","/webjars/**","/api_swagger/**").permitAll()
                .antMatchers("/habitaciones/crear/**","/habitaciones/editar/**","/habitaciones/borrar/**","/habitaciones/listar/**","/reserva/ver","/api_swagger/verreservas", "/perfil/mis-hoteles").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                        .successHandler(successHandler)
                        .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");



    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{

        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");

    }

}
