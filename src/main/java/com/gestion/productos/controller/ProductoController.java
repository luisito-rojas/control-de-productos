package com.gestion.productos.controller;


import com.gestion.productos.entity.Producto;
import com.gestion.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductoController {


    @Autowired
    private ProductoService productoService;

    @RequestMapping("/")
    public String verPaginaDeInicio(Model modelo, @Param("palabraClave") String palabraClave){

        List<Producto> listaProductos = productoService.listAll(palabraClave);
        modelo.addAttribute("listaProductos", listaProductos);
        modelo.addAttribute("palabraClave", palabraClave);
        return "index";
    }


    @RequestMapping("/nuevo")
    public String mostrarFormularioDeRegistrarProducto(Model modelo){
        Producto producto = new Producto();
        modelo.addAttribute("producto",producto);
        return "nuevo_producto";
    }


    //Cuando llenemos el formulario con los datos de un producto lo agregara a la lista y nos direccionara a la clase de inicio
    @RequestMapping(value="/guardar", method = RequestMethod.POST)
    public String guardarProducto(@ModelAttribute("producto")Producto producto){
        productoService.save(producto);
        return "redirect:/";
    }



    //este metodo nos permitira editar cualquier producto
    @RequestMapping(value="/editar/{id}")
    public ModelAndView mostrarFormularioDeEditarProducto(@PathVariable(name="id")Long id){
        ModelAndView modelo = new ModelAndView("editar_producto");

        Producto producto = productoService.get(id);
        modelo.addObject("producto",producto);
        return modelo;
    }


    @RequestMapping(value="/eliminar/{id}")
    public String eliminarProducto(@PathVariable(name="id")Long id){
        productoService.delete(id);
        return "redirect:/";
    }



}
