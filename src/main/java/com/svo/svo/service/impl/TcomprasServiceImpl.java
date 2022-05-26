package com.svo.svo.service.impl;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TcomprasRepository;
import com.svo.svo.repository.TpedidosRepository;
import com.svo.svo.repository.TusuariosRepository;
import com.svo.svo.service.TcomprasService;
import com.svo.svo.service.TproductosService;
import org.apache.tomcat.util.digester.ArrayStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcomprasServiceImpl implements TcomprasService {
    private static final Logger LOG = LoggerFactory.getLogger(TcomprasServiceImpl.class);

    @Autowired
    private TcomprasRepository tcomprasRepository;

    @Autowired
    private TpedidosRepository tpedidosRepository;

    @Override
    public TcomprasDTO buscarCompraPorId(Long idCompra) throws AppException {
        LOG.info("buscarCompraPorId ()");
        TcomprasVO tcomprasVO = null;
        TcomprasDTO  tcomprasDTO = null;
        try {
            tcomprasVO = tcomprasRepository.findCompraPorId(idCompra);
            tcomprasDTO = TcomprasBuilder.fromVO(tcomprasVO);
        } catch (Exception e) {
            Utils.raise(e, e.getMessage());
        }
        return tcomprasDTO;
    }

    @Override
    public List<TpedidosDTO> buscarComprasPorUsuario(Long idUsuario, String estatusPedido) throws AppException {
        LOG.info("buscarComprasPorUsuario ()");
        List<TpedidosVO> tpedidosVOS = null;
        List<TpedidosDTO>  tpedidosList = new ArrayStack<>();
        try {
            tpedidosVOS = tpedidosRepository.buscarPedidosPorIdUsuario(idUsuario, estatusPedido);
            for(TpedidosVO tpedidoVO: tpedidosVOS){
                TpedidosDTO tpedidosDTO = TpedidosBuilder.fromVO(tpedidoVO);
                tpedidosList.add(tpedidosDTO);
            }
        } catch (Exception e) {
            Utils.raise(e, e.getMessage());
        }
        return tpedidosList;
    }
}
