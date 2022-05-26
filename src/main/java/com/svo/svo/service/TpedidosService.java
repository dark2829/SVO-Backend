package com.svo.svo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.svo.svo.model.TpedidosDTO;
import com.svo.svo.model.TproductosDTO;
import com.svo.svo.model.TsolicitudCancelacionDTO;
import com.svo.svo.other.Utils.AppException;
import org.json.JSONObject;

import java.util.List;

public interface TpedidosService {
    List<TpedidosDTO> buscarTodosPedidos() throws Exception;
    TpedidosDTO crearSolicitudCancelacion(Long idCompra, String motivoCancelacion) throws AppException;
    TpedidosDTO buscarPedidoPorId(Long idPedido) throws AppException;
    TpedidosDTO responderSolicitudCancelacion(Long idPedido, JSONObject respuestaSolicitud) throws AppException;
    void actualizarEstatusPedido(Long idPedido, String estatus) throws  AppException;
}

