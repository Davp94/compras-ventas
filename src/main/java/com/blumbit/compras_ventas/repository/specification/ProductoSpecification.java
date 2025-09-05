package com.blumbit.compras_ventas.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.blumbit.compras_ventas.dto.response.producto.ProductoFilterCriteria;
import com.blumbit.compras_ventas.entity.AlmacenProducto;
import com.blumbit.compras_ventas.entity.Categoria;
import com.blumbit.compras_ventas.entity.Producto;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class ProductoSpecification {

    public static Specification<Producto> createSpecification(ProductoFilterCriteria criteria){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(criteria.getAlmacenId() != null && criteria.getAlmacenId() != 0){
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<AlmacenProducto> almacenProductoRoot = subquery.from(AlmacenProducto.class);
                subquery.select(almacenProductoRoot.get("producto").get("id"))
                .where(criteriaBuilder.equal(almacenProductoRoot.get("almacen").get("id"), criteria.getAlmacenId()));
                predicates.add(criteriaBuilder.in(root.get("id")).value(subquery));
            }
 
            if(criteria.getCodigoBarra() != null && !criteria.getCodigoBarra().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("codigoBarra")), 
                "%"+criteria.getCodigoBarra().toLowerCase()+"%"));
            }

            if(criteria.getNombre() != null && !criteria.getNombre().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), 
                "%"+criteria.getNombre().toLowerCase()+"%"));
            }

            if(criteria.getMarca() != null && !criteria.getMarca().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("marca")), 
                "%"+criteria.getMarca().toLowerCase()+"%"));
            }

            if(criteria.getDescripcion() != null && !criteria.getDescripcion().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("descripcion")), 
                "%"+criteria.getDescripcion().toLowerCase()+"%"));
            }

            if(criteria.getNombreCategoria() != null && !criteria.getNombreCategoria().trim().isEmpty()){
                Join<Producto, Categoria> categoriaJoin = root.join("Categoria");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(categoriaJoin.get("nombre")),  
                "%"+criteria.getNombreCategoria().toLowerCase()+"%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
