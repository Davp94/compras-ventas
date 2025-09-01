package com.blumbit.compras_ventas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.blumbit.compras_ventas.entity.Sucursal;

@Repository
public interface SucursalRepository extends ListCrudRepository<Sucursal, Long> {

    //Native Query
    @Query(value = "select * from sucursales s where s.nombre like '?1'", nativeQuery = true)
    List<Sucursal> findAllSucursalByNombre(String nombre);

    //JPQL Query
    @Query(value = "select Sucursal from Sucursal s where s.nombre like '?1'")
    List<Sucursal> findAllSucursalByNombreJpql(String nombre);

    //Query Methods
    List<Sucursal> findByNombre(String nombre);

}
