package com.microservicio_productos.levelup_MSproductos.controller;

import com.microservicio_productos.levelup_MSproductos.dto.ApiResponse;
import com.microservicio_productos.levelup_MSproductos.dto.ProductoDTO;
import com.microservicio_productos.levelup_MSproductos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Endpoints para operaciones CRUD de productos e inventario gamer")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar catálogo de productos", description = "Obtiene todos los productos o filtra opcionalmente por categoría o búsqueda.")
    public ResponseEntity<List<ProductoDTO>> listar(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String search
    ) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(productoService.buscarPorTexto(search));
        }
        if (categoria != null && !categoria.isBlank()) {
            return ResponseEntity.ok(productoService.buscarPorCategoria(categoria));
        }
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Retorna el detalle de un producto específico mediante su código alfanumérico.")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear nuevo producto (Admin)", description = "Permite a un administrador registrar un nuevo producto en el catálogo.")
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevo = productoService.crear(productoDTO);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualizar producto (Admin)", description = "Permite a un administrador modificar los datos de un producto existente.")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable String id, @Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO actualizado = productoService.actualizar(id, productoDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar producto (Admin)", description = "Permite a un administrador dar de baja un producto por su ID.")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable String id) {
        productoService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Producto eliminado correctamente", null));
    }
}
