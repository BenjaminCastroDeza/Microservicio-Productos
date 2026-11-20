package com.microservicio_productos.levelup_MSproductos.service;

import com.microservicio_productos.levelup_MSproductos.dto.PedidoRequest;
import com.microservicio_productos.levelup_MSproductos.dto.PedidoResponseDTO;

import java.util.List;

public interface PedidoService {

    PedidoResponseDTO procesarPedido(PedidoRequest request, String userEmail);

    List<PedidoResponseDTO> listarTodos();

    List<PedidoResponseDTO> listarPorUsuario(Long usuarioId);

    PedidoResponseDTO obtenerPorId(Long id);
}
