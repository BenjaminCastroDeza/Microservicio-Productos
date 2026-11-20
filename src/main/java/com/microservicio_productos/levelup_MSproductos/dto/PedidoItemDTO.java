package com.microservicio_productos.levelup_MSproductos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoItemDTO {

    @NotBlank(message = "El id del producto es obligatorio")
    private String productoId;

    private String nombreProducto;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio debe ser positivo")
    private Double precioUnitario;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidad;
}
