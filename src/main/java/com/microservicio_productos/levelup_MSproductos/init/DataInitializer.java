package com.microservicio_productos.levelup_MSproductos.init;

import com.microservicio_productos.levelup_MSproductos.model.Producto;
import com.microservicio_productos.levelup_MSproductos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) {
        if (productoRepository.count() == 0) {
            List<Producto> productos = List.of(
                    Producto.builder()
                            .id("JM001")
                            .nombre("Catan")
                            .categoria("Juegos de Mesa")
                            .precio(29990.0)
                            .img("/imagenes/catan.jpg")
                            .descripcion("Clásico juego de estrategia y negociación")
                            .stock(15)
                            .build(),
                    Producto.builder()
                            .id("JM002")
                            .nombre("Carcassonne")
                            .categoria("Juegos de Mesa")
                            .precio(24990.0)
                            .img("/imagenes/carcassonne.jpg")
                            .descripcion("Juego de colocación de losetas medievales")
                            .stock(12)
                            .build(),
                    Producto.builder()
                            .id("AC001")
                            .nombre("Controlador Inalámbrico Xbox Series X")
                            .categoria("Accesorios")
                            .precio(59990.0)
                            .img("/imagenes/mando.jpg")
                            .descripcion("Mando inalámbrico de precisión ergonómico")
                            .stock(20)
                            .build(),
                    Producto.builder()
                            .id("AC002")
                            .nombre("Auriculares Gamer HyperX Cloud II")
                            .categoria("Accesorios")
                            .precio(79990.0)
                            .img("/imagenes/hyperxcloud2.jpg")
                            .descripcion("Sonido envolvente 7.1 y micrófono con cancelación de ruido")
                            .stock(8)
                            .build(),
                    Producto.builder()
                            .id("CO001")
                            .nombre("PlayStation 5")
                            .categoria("Consolas")
                            .precio(549990.0)
                            .img("/imagenes/ps5.jpg")
                            .descripcion("Consola de nueva generación con gráficos 4K y ray tracing")
                            .stock(5)
                            .build(),
                    Producto.builder()
                            .id("CG001")
                            .nombre("PC Gamer ASUS ROG Strix")
                            .categoria("Computadores")
                            .precio(1299990.0)
                            .img("/imagenes/asuspc.jpg")
                            .descripcion("Equipo de alto rendimiento para gaming competitivo")
                            .stock(4)
                            .build(),
                    Producto.builder()
                            .id("SG001")
                            .nombre("Silla Gamer Secretlab Titan")
                            .categoria("Sillas Gamers")
                            .precio(349990.0)
                            .img("/imagenes/silla.jpg")
                            .descripcion("Silla ergonómica premiada con soporte lumbar integrado")
                            .stock(7)
                            .build(),
                    Producto.builder()
                            .id("MS001")
                            .nombre("Mouse Gamer Logitech G502 HERO")
                            .categoria("Mouse")
                            .precio(49990.0)
                            .img("/imagenes/mouse.jpg")
                            .descripcion("Sensor HERO 25K de alta precisión y pesas ajustables")
                            .stock(25)
                            .build(),
                    Producto.builder()
                            .id("MP001")
                            .nombre("Mousepad Razer Goliathus")
                            .categoria("Mousepads")
                            .precio(29990.0)
                            .img("/imagenes/mousepad.jpg")
                            .descripcion("Superficie de tela microtexturizada con borde cosido")
                            .stock(30)
                            .build()
            );

            productoRepository.saveAll(productos);
            log.info("✓ [MS-Productos] Catálogo inicial cargado con {} productos.", productos.size());
        }
    }
}
