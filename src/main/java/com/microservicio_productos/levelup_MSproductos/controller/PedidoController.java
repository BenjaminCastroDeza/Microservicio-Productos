package com.microservicio_productos.levelup_MSproductos.controller;

import com.microservicio_productos.levelup_MSproductos.dto.PedidoRequest;
import com.microservicio_productos.levelup_MSproductos.dto.PedidoResponseDTO;
import com.microservicio_productos.levelup_MSproductos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos y Carrito", description = "Endpoints para procesar compras, aplicar cupones de descuento y consultar órdenes")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Procesar compra del carrito", description = "Recibe los artículos del carrito y cupón (ej: DUOC20), calcula totales y registra la orden.")
    public ResponseEntity<PedidoResponseDTO> crearPedido(
            @Valid @RequestBody PedidoRequest request,
            Authentication authentication
    ) {
        String email = (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName()))
                ? authentication.getName()
                : null;

        PedidoResponseDTO pedido = pedidoService.procesarPedido(request, email);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar pedidos", description = "Retorna el historial de compras registradas en el sistema.")
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obtener pedido por ID", description = "Retorna los detalles y desglose de un pedido específico.")
    public ResponseEntity<PedidoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }
}
