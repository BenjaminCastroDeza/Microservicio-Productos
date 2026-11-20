package com.microservicio_productos.levelup_MSproductos.service;

import com.microservicio_productos.levelup_MSproductos.dto.ProductoDTO;
import com.microservicio_productos.levelup_MSproductos.exception.ResourceNotFoundException;
import com.microservicio_productos.levelup_MSproductos.model.Producto;
import com.microservicio_productos.levelup_MSproductos.repository.ProductoRepository;
import com.microservicio_productos.levelup_MSproductos.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .id("JM001")
                .nombre("Catan")
                .categoria("Juegos de Mesa")
                .precio(29990.0)
                .img("/imagenes/catan.jpg")
                .descripcion("Juego de mesa clásico")
                .stock(10)
                .build();

        productoDTO = ProductoDTO.builder()
                .id("JM001")
                .nombre("Catan")
                .categoria("Juegos de Mesa")
                .precio(29990.0)
                .img("/imagenes/catan.jpg")
                .descripcion("Juego de mesa clásico")
                .stock(10)
                .build();
    }

    @Test
    @DisplayName("Debe listar todos los productos")
    void testListarTodos() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoDTO> resultado = productoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Catan", resultado.get(0).getNombre());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe obtener un producto por su ID")
    void testObtenerPorId() {
        when(productoRepository.findById("JM001")).thenReturn(Optional.of(producto));

        ProductoDTO resultado = productoService.obtenerPorId("JM001");

        assertNotNull(resultado);
        assertEquals("JM001", resultado.getId());
        assertEquals("Catan", resultado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar excepción si producto no existe al buscar por ID")
    void testObtenerPorIdNoExiste() {
        when(productoRepository.findById("XYZ99")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productoService.obtenerPorId("XYZ99"));
    }

    @Test
    @DisplayName("Debe crear un producto exitosamente")
    void testCrearProducto() {
        when(productoRepository.existsById("JM001")).thenReturn(false);
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        ProductoDTO creado = productoService.crear(productoDTO);

        assertNotNull(creado);
        assertEquals("JM001", creado.getId());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Debe eliminar un producto existente")
    void testEliminarProducto() {
        when(productoRepository.existsById("JM001")).thenReturn(true);
        doNothing().when(productoRepository).deleteById("JM001");

        assertDoesNotThrow(() -> productoService.eliminar("JM001"));
        verify(productoRepository, times(1)).deleteById("JM001");
    }
}
