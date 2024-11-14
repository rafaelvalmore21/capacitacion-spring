package com.organizacion.software.controller;

import com.organizacion.software.model.Pedido;
import com.organizacion.software.repository.PedidoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidos")
public class pedidoController {
    private final PedidoRepository pedidoRepository;
    private static final String REDIRECT_PEDIDOS = "redirect:/pedidos";

    public pedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping
    public String getPedidos(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "pedidos/list";
    }
    @GetMapping("/new")
    public String newPedido(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "pedidos/form";
    }
    @GetMapping("/edit/{id}")
    public String editPedido(Model model, Pedido pedido) {
        model.addAttribute("pedido", pedido);
        return "pedidos/form";
    }

    @GetMapping("/delete/{id}")
    public String deletePedido(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new IllegalArgumentException("Pedido no encontrado con id: " + id);
        }
        pedidoRepository.deleteById(id);
        return REDIRECT_PEDIDOS;
    }

    @PostMapping
    public String savePedido(Pedido pedido) {
        pedidoRepository.save(pedido);
        return REDIRECT_PEDIDOS;
    }
}
