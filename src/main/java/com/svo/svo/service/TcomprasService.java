package com.svo.svo.service;

import com.svo.svo.model.TcomprasDTO;
import com.svo.svo.model.TpedidosDTO;
import com.svo.svo.other.Utils.AppException;
import java.util.List;

public interface TcomprasService {
    TcomprasDTO buscarCompraPorId(Long idCompra) throws AppException;
    List<TpedidosDTO> buscarComprasPorUsuario (Long idUsuario, String estatusPedido) throws AppException;
    List<TpedidosDTO> buscarCompraPorFecha(Long idUsuario, String estatusPedido, String fecha) throws AppException;
}
