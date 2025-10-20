package com.blumbit.compras_ventas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.compras_ventas.entity.AlmacenProducto;

public interface AlmacenProductoRepository extends ListCrudRepository<AlmacenProducto, Long>{

    Optional<AlmacenProducto> findByAlmacen_IdAndProducto_Id(Long almacenId, Long productoId); 

    List<AlmacenProducto> findByAlmacen_Id(Short id);
}
