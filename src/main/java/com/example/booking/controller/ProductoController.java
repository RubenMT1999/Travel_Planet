package com.example.booking.controller;

import com.example.booking.models.Producto;
import com.example.booking.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductoController {


    @Autowired
    private ProductoService productoService;

    @RequestMapping("/productos")
    public List<Producto> obtenerProducto(){
    return productoService.obtenerProducto();
    }



}
