package com.microservicio_productos.levelup_MSproductos.service.impl;

import com.microservicio_productos.levelup_MSproductos.dto.PedidoItemDTO;
import com.microservicio_productos.levelup_MSproductos.dto.PedidoRequest;
import com.microservicio_productos.levelup_MSproductos.dto.PedidoResponseDTO;
import com.microservicio_productos.levelup_MSproductos.exception.BadRequestException;
import com.microservicio_productos.levelup_MSproductos.exception.ResourceNotFoundException;
import com.microservicio_productos.levelup_MSproductos.model.DetallePedido;
import com.microservicio_productos.levelup_MSproductos.model.Pedido;
import com.microservicio_productos.levelup_MSproductos.model.Producto;
import com.microservicio_productos.levelup_MSproductos.repository.PedidoRepository;
import com.microservicio_productos.levelup_MSproductos.repository.ProductoRepository;
import com.microservicio_productos.levelup_MSproductos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public PedidoResponseDTO procesarPedido(PedidoRequest request, String userEmail) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("El pedido no contiene productos.");
        }

        String clienteEmail = (userEmail != null && !userEmail.isBlank() && !"anonymousUser".equals(userEmail))
                ? userEmail
                : (request.getCorreoCliente() != null ? request.getCorreoCliente() : "invitado@levelup.cl");

        String clienteNombre = (request.getNombreCliente() != null && !request.getNombreCliente().isBlank())
                ? request.getNombreCliente()
                : "Cliente LevelUp";

        Pedido pedido = Pedido.builder()
                .usuarioId(request.getUsuarioId())
                .nombreCliente(clienteNombre)
                .correoCliente(clienteEmail)
                .fecha(LocalDateTime.now())
                .cupon(request.getCupon())
                .items(new ArrayList<>())
                .build();

        double subtotal = 0.0;

        for (PedidoItemDTO itemDto : request.getItems()) {
            Producto producto = productoRepository.findById(itemDto.getProductoId().toUpperCase())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", "ID", itemDto.getProductoId()));

            double itemTotal = producto.getPrecio() * itemDto.getCantidad();
            subtotal += itemTotal;

            DetallePedido detalle = DetallePedido.builder()
                    .pedido(pedido)
                    .productoId(producto.getId())
                    .nombreProducto(producto.getNombre())
                    .precioUnitario(producto.getPrecio())
                    .cantidad(itemDto.getCantidad())
                    .subtotal(itemTotal)
                    .build();

            pedido.getItems().add(detalle);
        }

        // Aplicación del cupón DUOC20 (20% descuento)
        double descuento = 0.0;
        if (request.getCupon() != null && "DUOC20".equalsIgnoreCase(request.getCupon().trim())) {
            descuento = subtotal * 0.20;
        }

        double total = Math.max(0.0, subtotal - descuento);
        pedido.setDescuento(descuento);
        pedido.setTotal(total);

        Pedido guardado = pedidoRepository.save(pedido);

        return convertirADTO(guardado, subtotal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAllByOrderByFechaDesc().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByFechaDesc(usuarioId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO obtenerPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", "ID", id));
        return convertirADTO(pedido);
    }

    private PedidoResponseDTO convertirADTO(Pedido pedido) {
        double subtotal = pedido.getItems().stream().mapToDouble(DetallePedido::getSubtotal).sum();
        return convertirADTO(pedido, subtotal);
    }

    private PedidoResponseDTO convertirADTO(Pedido pedido, double subtotal) {
        List<PedidoItemDTO> itemsDTO = pedido.getItems().stream()
                .map(item -> PedidoItemDTO.builder()
                        .productoId(item.getProductoId())
                        .nombreProducto(item.getNombreProducto())
                        .precioUnitario(item.getPrecioUnitario())
                        .cantidad(item.getCantidad())
                        .build())
                .collect(Collectors.toList());

        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .usuarioId(pedido.getUsuarioId())
                .nombreCliente(pedido.getNombreCliente())
                .correoCliente(pedido.getCorreoCliente())
                .fecha(pedido.getFecha())
                .subtotal(subtotal)
                .descuento(pedido.getDescuento())
                .total(pedido.getTotal())
                .cupon(pedido.getCupon())
                .items(itemsDTO)
                .build();
    }
}
