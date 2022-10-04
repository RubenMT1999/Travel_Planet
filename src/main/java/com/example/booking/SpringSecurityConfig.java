package com.example.booking;

import com.example.booking.auth.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

//enableGlobalMehtodSecurity para permitir las anotaciones Secured
@EnableGlobalMethodSecurity(securedEnabled = true)
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

    //Configuracion de quien puede visitar cada url
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //definimos quien va a poder acceder a las distintas direccioens
        http.authorizeRequests().antMatchers("/","/styles/**","/images/**","/login").permitAll()
                .antMatchers("/registrar/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .usernameParameter("nombre")
                    .passwordParameter("contrasenia")
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
                .usersByUsernameQuery("select nombre, contrasenia, enabled from usuario where nombre=?")
                .authoritiesByUsernameQuery("select * from authorities a inne where ")

    }

}
