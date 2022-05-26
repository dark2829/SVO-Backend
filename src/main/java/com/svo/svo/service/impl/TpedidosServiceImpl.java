package com.svo.svo.service.impl;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TpedidosRepository;
import com.svo.svo.repository.TsolicitudCancelacionRepository;
import com.svo.svo.repository.TusuariosRepository;
import com.svo.svo.service.TpedidosService;
import com.svo.svo.service.TproductosService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TpedidosServiceImpl implements TpedidosService {
    private static final Logger LOG = LoggerFactory.getLogger(TpedidosService.class);

    @Autowired
    private TpedidosRepository tpedidosRepository;

    @Autowired
    private TsolicitudCancelacionRepository tsolicitudCancelacionRepository;

    @Override
    public List<TpedidosDTO> buscarTodosPedidos() throws Exception {
        List<TpedidosDTO> tpedidosDTOList = null;
        LOG.info("buscarTodosPedidos()");
        try {
            List<TpedidosVO> tpedidosVOS = tpedidosRepository.buscarTodosPedidos();
            tpedidosDTOList = new ArrayList<>();
            for (TpedidosVO tpedidosVO1 : tpedidosVOS) {
                TpedidosDTO tpedidosDTO = TpedidosBuilder.fromVO(tpedidosVO1);
                tpedidosDTOList.add(tpedidosDTO);
            }
        } catch (Exception e) {
            Utils.raise(e, "Error en buscar todos los pedidos");
        }
        return tpedidosDTOList;
    }

    @Override
    public TpedidosDTO crearSolicitudCancelacion(Long idCompra,String motivoCancelacion) throws AppException {
        TsolicitudCancelacionVO nuevaSolicitud = new TsolicitudCancelacionVO();
        TpedidosDTO pedidoDTO = null;
        try{
            TpedidosVO pedido = tpedidosRepository.buscarPedidoPorIdCompra(idCompra);
            LOG.info("ESTE ES EL PEDIDO"+pedido);
            if(pedido==null){
                throw new RuntimeException("No existe el pedido");
            }
            nuevaSolicitud.setMotivo_cancel(motivoCancelacion);
            tsolicitudCancelacionRepository.save(nuevaSolicitud);
            pedido.setSolicitudCancelacion(nuevaSolicitud);
            tpedidosRepository.save(pedido);
            pedidoDTO = TpedidosBuilder.fromVO(pedido);
        }catch (Exception e){
            Utils.raise(e,"Error al registrar solicitud");
        }

        return pedidoDTO;
    }

    @Override
    public TpedidosDTO buscarPedidoPorId(Long idPedido) throws AppException {
        LOG.info("buscarPedidoPorId ()");
        TpedidosVO tpedidosVO = null;
        TpedidosDTO  tpedidosDTO = null;
        try {
            tpedidosVO = tpedidosRepository.buscarPedidoPorId(idPedido);
            tpedidosDTO = TpedidosBuilder.fromVO(tpedidosVO);
        } catch (Exception e) {
            Utils.raise(e, e.getMessage());
        }
        return tpedidosDTO;
    }

    @Override
    public TpedidosDTO responderSolicitudCancelacion(Long idPedido, JSONObject respuestaSolicitud) throws AppException {
        TpedidosDTO tpedidosDTO = null;
        try{
            TpedidosVO pedido = tpedidosRepository.buscarPedidoPorId(idPedido);
            if(pedido == null){
                throw new RuntimeException("El pedido no existe");
            }
            TsolicitudCancelacionVO  solicitud = pedido.getSolicitudCancelacion();
            solicitud.setMotivo_resp(respuestaSolicitud.getString("motivo_res"));
            solicitud.setEstatus(respuestaSolicitud.getString("estatus"));
            tsolicitudCancelacionRepository.save(solicitud);
            pedido.setSolicitudCancelacion(solicitud);
            tpedidosRepository.save(pedido);
            tpedidosDTO = TpedidosBuilder.fromVO(pedido);
        }catch (Exception e){
            Utils.raise(e,"Error al buscar un pedido");
        }

        return tpedidosDTO;


    }
}
