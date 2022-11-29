package com.example.booking;

import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = CreacionObjetosFaker.class)
//@ComponentScan(basePackages = "")
public class BookingApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }


    @Autowired
    HabitacionService habitacionService;

    //para el message.properties
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }



    @Override
    public void run(String... args) throws Exception {
        habitacionService.init();
    }
}
