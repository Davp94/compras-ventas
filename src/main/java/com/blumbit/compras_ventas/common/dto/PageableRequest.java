package com.blumbit.compras_ventas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableRequest<T> {
    private int pageSize;
    private int pageNumber;
    private String sortField;
    private String sortOrder;
    private T criterials;
    private String filterValue;
}
