package com.microservicio_productos.levelup_MSproductos.service;

import com.microservicio_productos.levelup_MSproductos.dto.ProductoDTO;

import java.util.List;

public interface ProductoService {

    List<ProductoDTO> listarTodos();

    ProductoDTO obtenerPorId(String id);

    List<ProductoDTO> buscarPorCategoria(String categoria);

    List<ProductoDTO> buscarPorTexto(String query);

    ProductoDTO crear(ProductoDTO productoDTO);

    ProductoDTO actualizar(String id, ProductoDTO productoDTO);

    void eliminar(String id);
}
