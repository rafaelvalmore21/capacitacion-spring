package com.organizacion.software.controller;

import com.organizacion.software.model.Producto;
import com.organizacion.software.repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class productoController {
    private final ProductoRepository productoRepository;
    private static final String REDIRECT_ITEMS = "redirect:/productos";

    public productoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public String listProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos/list";
    }

    @GetMapping("/new")
    public String newProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/form";
    }

    @GetMapping("/edit/{id}")
    public String editProducto(@PathVariable Long id, Model model) {
        model.addAttribute(
                "producto",
                productoRepository.findById(id)
                        .orElseThrow(
                                () -> new IllegalArgumentException("Producto no encontrado con id: " + id)
                        ));
        return "productos/form";
    }

    @PostMapping("/update/{id}")
    public String updateProducto(
            @PathVariable Long id,
            Producto producto) {
        productoRepository.findById(id)
                .ifPresentOrElse(
                        (productoToUpdate) -> {
                            productoToUpdate.setNombre(producto.getNombre());
                            productoToUpdate.setDescripcion(producto.getDescripcion());
                            productoToUpdate.setPrecio(producto.getPrecio());
                            productoToUpdate.setCantidad(producto.getCantidad());
                            productoRepository.save(productoToUpdate);
                        },
                        () -> {
                            throw new IllegalArgumentException("Producto no encontrado con id: " + id);
                        }
                );
        return REDIRECT_ITEMS;
    }

    @PostMapping
    public String saveProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto);
        return REDIRECT_ITEMS;
    }

    @GetMapping("/delete/{id}")
    public String deleteProducto(@PathVariable Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
        return REDIRECT_ITEMS;
    }

}
