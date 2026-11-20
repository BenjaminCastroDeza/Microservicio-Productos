package com.microservicio_productos.levelup_MSproductos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponseDTO {

    private Long id;
    private Long usuarioId;
    private String nombreCliente;
    private String correoCliente;
    private LocalDateTime fecha;
    private Double subtotal;
    private Double descuento;
    private Double total;
    private String cupon;
    private List<PedidoItemDTO> items;
}
