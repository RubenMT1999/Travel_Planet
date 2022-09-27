package com.example.booking.services;


import com.example.booking.models.Producto;
import com.example.booking.repository.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    public List<Producto> obtenerProducto(){
        return productoRepositorio.findAll();
    }

}
