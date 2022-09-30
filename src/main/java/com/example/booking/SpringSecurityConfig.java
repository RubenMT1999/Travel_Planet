package com.example.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{

        PasswordEncoder encoder = passwordEncoder();
        //por cada vez que registramos un usuario se genera un evento que encripta la contraseña
        User.UserBuilder users = User.builder().passwordEncoder(password ->{
            return encoder.encode(password);
        });

        //el password se va a encriptar con el passwordEncoder.
        builder.inMemoryAuthentication().withUser(users.username("admin").password("12345").roles("ADMIN","USER"))
                .withUser(users.username("usuario").password("12345").roles("USER"));

    }

}
