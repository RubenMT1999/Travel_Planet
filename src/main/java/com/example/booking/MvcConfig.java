package com.example.booking;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {


    //View Controller para página de error 403. Este error ocurre cuando el usuario
    //no tiene permisos e intenta acceder a la url.
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/error_403").setViewName("error_403");
    }

}
