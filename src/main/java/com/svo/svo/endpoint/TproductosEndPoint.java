package com.svo.svo.endpoint;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TproductosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class TproductosEndPoint {
    private static final Logger LOG = LoggerFactory.getLogger(TproductosEndPoint.class);

    @Autowired
    private TproductosService tproductosService;

    /*
    "codigo_prod" : "00058",
    "nombre": "Cerillos 50pzs",
    "categoria" : "Hogar",
    "cantidad" : 20,
    "precio_compra" :10,
    "precio_venta" :12,
    "precio_descuento": 15,
    "descripcion" :"Cerillos LUMINA"
     */
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Empleado')")
    @PostMapping("/insertProduct")
    public ResponseEntity<ResponseBody<Void>> insertProduct(@RequestBody TproductosDTO tproductosDTO) {
        LOG.info("<<<<<insertProducto() -> ");
        ResponseEntity<ResponseBody<Void>> res = null;
        try {
            tproductosService.insert(tproductosDTO);
            res = Utils.response200OK("Producto agregado");
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, "Error al guardar producto", null);
        }
        return res;
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Empleado')")
    @PostMapping("/update")
    public ResponseEntity<ResponseBody<Void>> update(@RequestParam("id") int id, @RequestBody TproductosDTO tproductosDTO) throws AppException {
        LOG.info("update()->id: {} data: {}", id, tproductosDTO);
        ResponseEntity<ResponseBody<Void>> res = null;
        try {
            tproductosService.update((long) id, tproductosDTO);
            res = Utils.response200OK("Producto actualizado correctamente");
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, "Algo fallo al actualizar producto", null);
        }
        return res;
    }

    //id
    @GetMapping("/findProductById")//Buscar un producto
    public ResponseEntity<ResponseBody<TproductosDTO>> findProductById(@RequestParam("id") Long id) throws AppException {
        LOG.info("findProductById()--> id: " + id);
        TproductosDTO tproductosDTO = null;
        TproductosVO tproductosVO = null;
        ResponseEntity<ResponseBody<TproductosDTO>> res = null;
        try {
            tproductosVO = tproductosService.findProductById(id);
            if (tproductosVO != null) {
                tproductosDTO = TproductosBuilder.fromVO(tproductosVO);
                res = Utils.response(HttpStatus.ACCEPTED, "Producto existente", tproductosDTO);
            } else {
                res = Utils.response(HttpStatus.BAD_REQUEST, "Producto no encontrado", null);
            }
        } catch (AppException e) {
            Utils.raise(e, "Error al buscar producto");
        }
        return res;
    }

    @GetMapping("/findAllProductos")
    public ResponseEntity<ResponseBody<List<TproductosDTO>>> findAllProveedores() throws AppException {
        List<TproductosDTO> listProductos = null;
        ResponseEntity<ResponseBody<List<TproductosDTO>>> res = null;
        LOG.info("findAllProductos()");
        try {
            listProductos = tproductosService.findAllProductos();
            res = Utils.response200OK("Lista de productos", listProductos);
        } catch (Exception e) {
            Utils.raise(e, "Error al buscar todos los productos");
        }
        return res;
    }




}
