package com.svo.svo.endpoint;

import com.lowagie.text.DocumentException;
import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.GenerarFactura;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TpedidosService;
import com.svo.svo.service.TusuariosService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class TpedidosEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(TpedidosEndpoint.class);

    @Autowired
    private TpedidosService tpedidosService;


    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Empleado')")
    @GetMapping("/buscarTodosPedidos")
    public ResponseEntity<ResponseBody<List<TpedidosDTO>>> buscarTodosPedidos() throws AppException {
        List<TpedidosDTO> listPedidos = null;
        ResponseEntity<ResponseBody<List<TpedidosDTO>>> res = null;
        LOG.info("buscarTodosPedidos()");
        try {
            listPedidos = tpedidosService.buscarTodosPedidos();
            res = Utils.response200OK("Lista de pedidos", listPedidos);
        } catch (Exception e) {
            Utils.raise(e, "Error al buscar todos los pedidso");
        }
        return res;
    }

    //envio: string [envio: tienda]
    @GetMapping("/searchTipeSend")
    public ResponseEntity<ResponseBody<List<TpedidosDTO>>> searchTipeSend(@RequestParam String envio) throws AppException {
        List<TpedidosDTO> listPedidos = null;
        ResponseEntity<ResponseBody<List<TpedidosDTO>>> res = null;
        LOG.info("buscarTodosPedidos()");
        try {
            listPedidos = tpedidosService.searchTipeSend(envio);
            res = Utils.response200OK("Lista de pedidos", listPedidos);
        } catch (Exception e) {
            Utils.raise(e, "Error al buscar todos los pedidso");
        }
        return res;
    }
    /*
    url: idCompra:number
    body: "motivoCancelacion":string
     */
    @PostMapping("/solicitarCancelacion")
    public ResponseEntity<ResponseBody<Void>> solicitarCancelacion(@RequestParam Long idCompra, @RequestBody String motivoCancelacion) {
        LOG.info("<<<<<solicitarCancelacion() -> " + motivoCancelacion);
        JSONObject solicitud = new JSONObject(motivoCancelacion);
        ResponseEntity<ResponseBody<Void>> res = null;
        try {
            TpedidosDTO pedido = tpedidosService.crearSolicitudCancelacion(idCompra, solicitud.getString("motivoCancelacion"));
            LOG.info(String.valueOf(pedido));
            res = Utils.response200OK("Solicitud enviada");
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, "Error al crear la solicitud", null);
        }
        return res;
    }

    @GetMapping("/actualizarEstatusPedido")
    public ResponseEntity<ResponseBody<Void>> actualizarEstatusPedido(@RequestParam Long idPedido, @RequestParam String nuevoEstatus) {
        LOG.info("<<<<<actualizarEstatusPedido() -> " + idPedido);
        ResponseEntity<ResponseBody<Void>> res = null;
        try {
            tpedidosService.actualizarEstatusPedido(idPedido, nuevoEstatus);
            res = Utils.response200OK("Pedido actualizado");
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, "Error al crear la solicitud", null);
        }
        return res;
    }

    //url idPedido:number
    @GetMapping("/buscarPedidoPorId")
    public ResponseEntity<ResponseBody<TpedidosDTO>> buscarPedidoPorId(@RequestParam("idPedido") Long idPedido) throws AppException {
        LOG.info("buscarPedidoPorId()--> id: " + idPedido);
        TpedidosDTO tpedidosDTO = null;
        ResponseEntity<ResponseBody<TpedidosDTO>> res = null;
        try {
            tpedidosDTO = tpedidosService.buscarPedidoPorId(idPedido);
            if (tpedidosDTO != null) {
                res = Utils.response(HttpStatus.ACCEPTED, "PEDIDO ENCONTRADO", tpedidosDTO);
            } else {
                res = Utils.response(HttpStatus.BAD_REQUEST, "Pedido no encontrada", null);
            }
        } catch (AppException e) {
            Utils.raise(e, "Error al buscar pedido");
        }
        return res;
    }

    /*
    url idPedido: number
    body
        "motivo_res":string
        "estatus": string
    */
    @PostMapping("/responderSolicitarCancelacion")
    public ResponseEntity<ResponseBody<Void>> responderSolicitarCancelacion(@RequestParam Long idPedido, @RequestBody String respuesta) {
        LOG.info("<<<<<responderSolicitarCancelacion() -> " + respuesta);
        JSONObject respuestaSolicitud = new JSONObject(respuesta);
        ResponseEntity<ResponseBody<Void>> res = null;
        try {
            TpedidosDTO pedido = tpedidosService.responderSolicitudCancelacion(idPedido, respuestaSolicitud);
            LOG.info(String.valueOf(pedido));
            res = Utils.response200OK("Respuesta enviada");
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, "Error al responder la solicitud", null);
        }
        return res;
    }
    //Exporta en Pdf,
    //idPedido:number
    @GetMapping("/DescargarFactura/exportPdf")
    public void ExportPDF(@RequestParam Long idPedido, HttpServletResponse response) throws DocumentException, IOException, AppException, java.io.IOException{
        response.setContentType("aplication/pdf");
        Long idPedido1= idPedido;
        Date objDate = new Date();
        String strDateFormat = "yyyy-MM-dd_HH:mm:ss";
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        String headerKey ="Content-Disposition";
        String headerValue = "attachment; filename=factura_"+objSDF.format(objDate)+".pdf";
        response.setHeader(headerKey, headerValue);
        TpedidosDTO pedido = tpedidosService.buscarPedidoPorId(idPedido);
        TpedidosVO tpedidosVO = TpedidosBuilder.fromDTO(pedido);
        GenerarFactura exporter = new GenerarFactura(tpedidosVO);

        exporter.Export(response);

    }

    @GetMapping("/buscarPedidoPorCodigoCompra")
    public ResponseEntity<ResponseBody<List<TpedidosDTO>>> buscarPedidoPorCodigoCompra(@RequestParam("codigo_Compra") String codigoCompra) throws AppException {
        LOG.info("buscarPedidoPorCodigoCompra(): " + codigoCompra);
        List<TpedidosDTO> listPedidosDTO = null;
        ResponseEntity<ResponseBody<List<TpedidosDTO>>> res = null;
        try {
            listPedidosDTO = tpedidosService.buscarPedidoPorCodigoCompra(codigoCompra);
            if (!listPedidosDTO.isEmpty()) {
                res = Utils.response(HttpStatus.ACCEPTED, "PEDIDO ENCONTRADO", listPedidosDTO);
            } else {
                res = Utils.response(HttpStatus.BAD_REQUEST, "Pedido no encontrada", null);
            }
        } catch (AppException e) {
            Utils.raise(e, "Error al buscar pedido");
        }
        return res;
    }
}
