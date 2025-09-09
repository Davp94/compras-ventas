package com.blumbit.compras_ventas.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.compras_ventas.entity.Almacen;
import java.util.List;


public interface AlmacenRepository extends ListCrudRepository<Almacen, Long>{

    List<Almacen> findBySucursal_Id(Long id);
}
