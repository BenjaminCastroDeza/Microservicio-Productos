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
public class ProductoDTO {

    @NotBlank(message = "El código / ID del producto es obligatorio")
    private String id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser un número mayor a 0")
    private Double precio;

    @NotBlank(message = "La ruta o URL de la imagen es obligatoria")
    private String img;

    private String descripcion;

    @Builder.Default
    private Integer stock = 10;
}
