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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @PostMapping("/insertProduct")
    public void insertProduct(@RequestBody TproductosDTO tproductosDTO) {
        LOG.info("<<<<<insertProducto() -> ", tproductosDTO);
        try {
            tproductosService.insert(tproductosDTO);
        } catch (Exception e) {
        }
    }

    //id
    @PostMapping("/update")
    public void update(@RequestParam("id") int id, @RequestBody Map<String,String> data){
        LOG.info("update()->id: {} data: {}",id,data);
        try {
            tproductosService.update((long) id, data);
        } catch (Exception e){
            LOG.error(String.valueOf(e));
        }
    }
    //id
    @GetMapping("/findProductById")//Buscar un producto
    public ResponseEntity<ResponseBody<TproductosDTO>>  findAllProveedores(@RequestParam("id") int id) throws AppException {
        LOG.info("findProductById()--> id: "+ id);
        TproductosDTO tproductosDTO = null;
        ResponseEntity<ResponseBody<TproductosVO>> tproductosVO = null;
        ResponseEntity<ResponseBody<TproductosDTO>> res=null;
        try{
            tproductosVO = tproductosService.findProductById(Long.valueOf(id));
            if(tproductosVO.getBody().getData() != null){
                tproductosDTO= TproductosBuilder.fromVO(tproductosVO.getBody().getData());
                res= Utils.response(HttpStatus.ACCEPTED,"Producto existente",tproductosDTO)  ;
            }else{
                res = Utils.response(HttpStatus.BAD_REQUEST,tproductosVO.getBody().getMessage(),null);
            }
        }catch (AppException e){
            Utils.raise(e,"Error al buscar producto");
        }
        return res;
    }

    @GetMapping("/findAllProductos")
    public ResponseEntity<ResponseBody<List<TproductosDTO>>> findAllProveedores() throws AppException {
        List<TproductosDTO> listProductos = null;
        ResponseEntity<ResponseBody<List<TproductosDTO>>> res =null;
        LOG.info("findAllProductos()");
        try{
            listProductos= tproductosService.findAllProductos();
            res=Utils.response200OK("Lista de productos",listProductos);
        }catch (Exception e){
           Utils.raise(e,"Error al buscar todos los productos");
        }
        return res;
    }
}