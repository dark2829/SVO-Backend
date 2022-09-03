package com.svo.svo.endpoint;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TusuariosRepository;
import com.svo.svo.service.TproductosService;
import com.svo.svo.service.TusuariosService;
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

    @Autowired
    private TusuariosService tusuariosService;

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
            if(!productoDuplicate(Long.valueOf(0), tproductosDTO.getCodigo_prod())){
                tproductosService.insert(tproductosDTO);
                res = Utils.response200OK("Producto agregado");
            }else{
                res = Utils.response(HttpStatus.BAD_REQUEST, "El codigo de producto ya a sido registrado", null);
            }
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, "Error al guardar producto", null);
        }
        return res;
    }

    public boolean productoDuplicate(Long id,String codProducto){
        boolean encontrado = false;
        TproductosVO productoEncontrado = null;
        try{
            productoEncontrado = tproductosService.findProductoByCodigo(id,codProducto);
            if(productoEncontrado != null){
                encontrado = true;

            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        return encontrado;
    }
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Empleado')")
    @PostMapping("/update")
    public ResponseEntity<ResponseBody<Void>> update(@RequestParam("id") Long id, @RequestBody TproductosDTO tproductosDTO) throws AppException {
        LOG.info("update()->id: {} data: {}", id, tproductosDTO);
        ResponseEntity<ResponseBody<Void>> res = null;
        try {
            if(!productoDuplicate(id, tproductosDTO.getCodigo_prod())) {
                tproductosService.update(id, tproductosDTO);
                res = Utils.response200OK("Producto actualizado correctamente");
            }else{
                res = Utils.response(HttpStatus.BAD_REQUEST, "El codigo de producto ya a sido registrado", null);
            }
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

    //categoria: string
    @GetMapping("/findTipeProducts")
    public ResponseEntity<ResponseBody<List<TproductosDTO>>> findTipeProductos(@RequestParam("categoria") String tipe) throws Exception {
        List<TproductosDTO> listProductos = null;
        ResponseEntity<ResponseBody<List<TproductosDTO>>> res = null;
        LOG.info("findTipeProducts()");
        try {
            listProductos = tproductosService.findTipeProductos(tipe);
            res = Utils.response200OK("Lista de productos", listProductos);
        } catch (Exception e) {
            Utils.raise(e, "Error al buscar todos los productos");
        }
        return res;
    }

    @GetMapping("/findRealProducts")
    public ResponseEntity<ResponseBody<List<TproductosDTO>>> findRealProductos() throws Exception {
        List<TproductosDTO> listProductos = null;
        ResponseEntity<ResponseBody<List<TproductosDTO>>> res = null;
        LOG.info("findTipeProducts()");
        try {
            listProductos = tproductosService.findRealProductos();
            res = Utils.response200OK("Lista de productos", listProductos);
        } catch (Exception e) {
            Utils.raise(e, "Error al buscar todos los productos");
        }
        return res;
    }

    @GetMapping("/busquedaProductos")
    public ResponseEntity<ResponseBody<List<TproductosDTO>>> busquedaProductos(@RequestParam String nombre) throws Exception {
        List<TproductosDTO> listProductos = null;
        ResponseEntity<ResponseBody<List<TproductosDTO>>> res = null;
        LOG.info("busquedaProductos()");
        try {
            listProductos = tproductosService.busquedaProductos(nombre);
            res = Utils.response200OK("Lista de productos encontrados", listProductos);
        } catch (Exception e) {
            Utils.raise(e, "Error al buscar todos los productos");
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

    @GetMapping("/findStockBajo")
    public ResponseEntity<ResponseBody<List<TproductosDTO>>> findStockBajo() throws AppException {
        List<TproductosDTO> listProductos = null;
        ResponseEntity<ResponseBody<List<TproductosDTO>>> res = null;
        LOG.info("findStockBajo()");
        try {
            listProductos = tproductosService.findStockBajo();
            res = Utils.response200OK("Lista de productos por terminarse", listProductos);
        } catch (Exception e) {
            Utils.raise(e, "Error al buscar los productos");
        }
        return res;
    }

    //idProducto:number
    @GetMapping("/contactado")
    public ResponseEntity<ResponseBody<Void>> contactado(@RequestParam Long idProducto) throws AppException {
        List<TproductosDTO> listProductos = null;
        ResponseEntity<ResponseBody<Void>> res = null;
        LOG.info("findStockBajo()");
        try {
            tproductosService.contactado(idProducto);
            res = Utils.response200OK("Se ha contactado correctamente");
        } catch (Exception e) {
            Utils.raise(e, "Error contactar");
        }
        return res;
    }

    //idProducto:number
    //idUsuario:number
    @GetMapping("/anadirFavoritos")
    public ResponseEntity<ResponseBody<Void>> anadirFavoritos(@RequestParam Long idProducto, @RequestParam Long idUsuario) throws AppException {
        ResponseEntity<ResponseBody<Void>> res = null;
        LOG.info("anadirFavoritos()");
        try {
            tproductosService.anadirFavoritos(idProducto, idUsuario);
            res = Utils.response200OK("Producto añadido a favoritos");
        } catch (Exception e) {
            Utils.raise(e, "Error añadir a producto a favoritos");
        }
        return res;
    }

    //idProducto:number
    //idUsuario:number
    @GetMapping("/borrarProductoFavorito")
    public ResponseEntity<ResponseBody<Void>> borrarProductoFavorito(@RequestParam Long idProducto, @RequestParam Long idUsuario) throws AppException {
        ResponseEntity<ResponseBody<Void>> res = null;
        LOG.info("borrarProductoFavorito()");
        try {
            tproductosService.borrarProductoFavorito(idProducto, idUsuario);
            res = Utils.response200OK("Producto eliminado de tus favoritos");
        } catch (Exception e) {
            Utils.raise(e, "Error al eliminar producto de tus favoritos favoritos");
        }
        return res;
    }

    //idUsuario:number
    @GetMapping("/mostrarFavoritosPorUsuario")
    public ResponseEntity<ResponseBody<List<TproductosDTO>>> mostrarFavoritosPorUsuario(@RequestParam Long idUsuario) throws AppException {
        ResponseEntity<ResponseBody<List<TproductosDTO>>> res = null;
        List<TproductosDTO> tproductosDTOS = null;
        LOG.info("MostraFavoritosPorUsuario()");
        try {
            tproductosDTOS = tusuariosService.mostrarFavoritosPorUsuario(idUsuario);
            if (tproductosDTOS.isEmpty()) {
                res = Utils.response(HttpStatus.ACCEPTED, "No hay productos en favoritos", null);
            }
            res = Utils.response200OK("Productos favoritos", tproductosDTOS);
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
        return res;
    }


}
