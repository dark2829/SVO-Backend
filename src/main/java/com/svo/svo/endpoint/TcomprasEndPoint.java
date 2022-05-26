package com.svo.svo.endpoint;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TcomprasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class TcomprasEndPoint {
    private static final Logger LOG = LoggerFactory.getLogger(TcomprasVO.class);

    @Autowired
    private TcomprasService tcomprasService;

     /*url:idCompra:cumber
      */
    @GetMapping("/buscarCompraPorId")
    public ResponseEntity<ResponseBody<TcomprasDTO>> buscarCompraPorId(@RequestParam("idCompra") Long idCompra) throws AppException {
        LOG.info("buscarCompraPorId()--> id: " + idCompra);
        TcomprasDTO tcomprasDTO = null;
        ResponseEntity<ResponseBody<TcomprasDTO>> res = null;
        try {
            tcomprasDTO = tcomprasService.buscarCompraPorId(idCompra);
            if (tcomprasDTO != null) {
                res = Utils.response(HttpStatus.ACCEPTED, "COMPRA ENCONTRADA", tcomprasDTO);
            } else {
                res = Utils.response(HttpStatus.BAD_REQUEST, "Compra no encontrada", null);
            }
        } catch (AppException e) {
            Utils.raise(e, "Error al buscar compra");
        }
        return res;
    }
/*
idUsuario: number
estatus:string
 */
    @GetMapping("/buscarComprasPorUsuario")
    public ResponseEntity<ResponseBody<List<TpedidosDTO>>> buscarComprasPorUsuario(@RequestParam("idUsuario") Long idUsuario, @RequestParam("estatus") String estatusPedido) throws AppException {
        LOG.info("buscarComprasPorUsuario()--> idUsuario: " + idUsuario);
        List<TpedidosDTO> tpedidos = null;
        ResponseEntity<ResponseBody<List<TpedidosDTO>>> res = null;
        try {
            tpedidos = tcomprasService.buscarComprasPorUsuario(idUsuario, estatusPedido);
            if (!tpedidos.isEmpty()) {
                res = Utils.response(HttpStatus.ACCEPTED, "Compras encontradas", tpedidos);
            } else {
                res = Utils.response(HttpStatus.ACCEPTED, "No se encontro ninguna compra", null);
            }
        } catch (AppException e) {
            Utils.raise(e, "Error al buscar compras");
        }
        return res;
    }
}
