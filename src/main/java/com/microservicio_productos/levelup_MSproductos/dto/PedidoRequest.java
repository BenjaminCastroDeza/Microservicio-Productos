package com.microservicio_productos.levelup_MSproductos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoRequest {

    private String cupon;
    private String nombreCliente;
    private String correoCliente;
    private Long usuarioId;

    @NotEmpty(message = "El pedido debe contener al menos un producto")
    @Valid
    private List<PedidoItemDTO> items;
}
