package com.example.booking;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.nio.file.Paths;


@Configuration
public class MvcConfig implements WebMvcConfigurer {



    //View Controller para página de error 403. Este error ocurre cuando el usuario
    //no tiene permisos e intenta acceder a la url.
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/error_403").setViewName("error_403");
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        //registrar ruta externa para cargar imagen
        //addResourceHandler para declararla como estática y poder verla en la vista
        //con toUri le agrega el file:/
        String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourcePath);
    }
}
