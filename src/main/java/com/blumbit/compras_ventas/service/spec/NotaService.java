package com.blumbit.compras_ventas.service.spec;

import java.util.List;

import com.blumbit.compras_ventas.dto.request.NotaRequest;
import com.blumbit.compras_ventas.dto.response.ClienteResponse;
import com.blumbit.compras_ventas.dto.response.NotaResponse;

public interface NotaService {

    List<NotaResponse> findAllNotas();

    NotaResponse createNota(NotaRequest notaRequest);

    List<ClienteResponse> findAllClientes();

}
