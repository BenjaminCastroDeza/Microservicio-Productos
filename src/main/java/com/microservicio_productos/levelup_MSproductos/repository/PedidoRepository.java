package com.microservicio_productos.levelup_MSproductos.repository;

import com.microservicio_productos.levelup_MSproductos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    List<Pedido> findAllByOrderByFechaDesc();
}
