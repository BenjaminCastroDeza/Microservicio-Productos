package com.microservicio_productos.levelup_MSproductos.service.impl;

import com.microservicio_productos.levelup_MSproductos.dto.ProductoDTO;
import com.microservicio_productos.levelup_MSproductos.exception.BadRequestException;
import com.microservicio_productos.levelup_MSproductos.exception.ResourceNotFoundException;
import com.microservicio_productos.levelup_MSproductos.model.Producto;
import com.microservicio_productos.levelup_MSproductos.repository.ProductoRepository;
import com.microservicio_productos.levelup_MSproductos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO obtenerPorId(String id) {
        Producto producto = productoRepository.findById(id.trim().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "ID", id));
        return convertirADTO(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoriaIgnoreCase(categoria).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarPorTexto(String query) {
        return productoRepository.findByNombreContainingIgnoreCaseOrCategoriaContainingIgnoreCase(query, query).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoDTO crear(ProductoDTO dto) {
        String idNormalizado = dto.getId().trim().toUpperCase();
        if (productoRepository.existsById(idNormalizado)) {
            throw new BadRequestException("Ya existe un producto con el código: " + idNormalizado);
        }

        Producto producto = Producto.builder()
                .id(idNormalizado)
                .nombre(dto.getNombre().trim())
                .categoria(dto.getCategoria().trim())
                .precio(dto.getPrecio())
                .img(dto.getImg().trim())
                .descripcion(dto.getDescripcion())
                .stock(dto.getStock() != null ? dto.getStock() : 10)
                .build();

        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }

    @Override
    @Transactional
    public ProductoDTO actualizar(String id, ProductoDTO dto) {
        String idNormalizado = id.trim().toUpperCase();
        Producto producto = productoRepository.findById(idNormalizado)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "ID", id));

        producto.setNombre(dto.getNombre().trim());
        producto.setCategoria(dto.getCategoria().trim());
        producto.setPrecio(dto.getPrecio());
        producto.setImg(dto.getImg().trim());
        if (dto.getDescripcion() != null) {
            producto.setDescripcion(dto.getDescripcion());
        }
        if (dto.getStock() != null) {
            producto.setStock(dto.getStock());
        }

        Producto actualizado = productoRepository.save(producto);
        return convertirADTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(String id) {
        String idNormalizado = id.trim().toUpperCase();
        if (!productoRepository.existsById(idNormalizado)) {
            throw new ResourceNotFoundException("Producto", "ID", id);
        }
        productoRepository.deleteById(idNormalizado);
    }

    private ProductoDTO convertirADTO(Producto p) {
        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .categoria(p.getCategoria())
                .precio(p.getPrecio())
                .img(p.getImg())
                .descripcion(p.getDescripcion())
                .stock(p.getStock())
                .build();
    }
}
