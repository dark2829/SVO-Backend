package com.svo.svo.endpoint;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.svo.svo.model.*;
import com.svo.svo.other.GenerateCodigoCompra;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TcarritoRepository;
import com.svo.svo.repository.TcomprasRepository;
import com.svo.svo.repository.TusuariosRepository;
import com.svo.svo.service.TproductosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/cart")
public class TcarritoEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(TcarritoEndpoint.class);

    @Autowired
    private TproductosService tproductosService;

    @Autowired
    private TusuariosRepository tusuariosRepository;

    @Autowired
    private TcarritoRepository tcarritoRepository;

    @Autowired
    private TcomprasRepository tcomprasRepository;

    List<TcarritoVO> detalles = new ArrayList<TcarritoVO>();

    TcomprasVO orden = new TcomprasVO();

    /*id:Number
    cantidad:number*/
    @PostMapping("/carrito")
    public ResponseEntity<ResponseBody<TcomprasVO>> añadirCarrito(@RequestParam Long id, @RequestParam Integer cantidad) throws AppException {
        TcarritoVO detalleOrden = new TcarritoVO();
        TproductosVO producto = new TproductosVO();
        float total = 0;
        double sumaTotal = 0;
        int index = 0;
        TcarritoVO carritoAux = null;
        ResponseEntity<ResponseBody<TcomprasVO>> res = null;

        try {
            Optional<TproductosVO> optionalTproducto = Optional.ofNullable(tproductosService.findProductById(id));
            //buscar producto a agregar
            producto = optionalTproducto.get();
            //verifica que cantidad no revase stock, si revasa cancela proceso y mando msj
            if (cantidad > producto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente");
            }
            detalleOrden.setCantidad(cantidad);
            detalleOrden.setPrecio_unitario(producto.getPrecio_venta());
            detalleOrden.setPrecio_descuento(producto.getPrecio_descuento());
            //si el producto tiene descuento la cantidad de multiplicara por el
            if (producto.getPrecio_descuento() != 0) {
                total = producto.getPrecio_descuento() * cantidad;
            } else {//si no , sera pro el precio de venta
                total = producto.getPrecio_venta() * cantidad;
            }
            detalleOrden.setPrecio_total(total);
            detalleOrden.setIdProducto(producto);

            //Validar que un producto no se agregue dos veces
            Long idProducto = producto.getId();
            boolean ingresado = detalles.stream().anyMatch(p -> Objects.equals(p.getIdProducto().getId(), idProducto));
            //si no esta agregado se agrega
            if (!ingresado) {
                detalles.add(detalleOrden);
            } else {
                //si no, Busca producto y le suma la nueva cantidad
                for (TcarritoVO detalleProducto : detalles) {
                    //si encuentra el producto
                    if (Objects.equals(detalleProducto.getIdProducto().getId(), idProducto)) {
                        //verifica que sumandole la cantidad no revase el stock
                        //si si, cancela proceso y manda msj
                        if (detalleProducto.getCantidad() + cantidad > detalleProducto.getIdProducto().getCantidad()) {
                            throw new RuntimeException("La cantidad revasa el stock");
                        } else {
                            //si no, suma cantidad
                            carritoAux.setCantidad(detalleProducto.getCantidad() + cantidad);
                            break;
                        }
                    }
                    index++;
                }
                //actualiza producto conla nueva cantidad
                detalles.set(index, carritoAux);
            }
            sumaTotal = detalles.stream().mapToDouble(dt -> dt.getPrecio_total()).sum();
            orden.setPago_total((float) sumaTotal);
            orden.setCarrito(detalles);

            res = Utils.response200OK("Carrito actualizado", orden);
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, e.getMessage(), orden);
        }
        return res;

    }

    //quitar producto del carrito
    // id:number
    @GetMapping("/delete/cart")
    public ResponseEntity<ResponseBody<TcomprasVO>> deleteProductCart(@RequestParam Long id) {
        LOG.info("Eliminar producto de carrito: ", id);
        ResponseEntity<ResponseBody<TcomprasVO>> res = null;
        //lista nueva de productos
        List<TcarritoVO> ordenNueva = new ArrayList<TcarritoVO>();
        for (TcarritoVO detalleOrden : detalles) {
            if (!Objects.equals(detalleOrden.getIdProducto().getId(), id)) {
                ordenNueva.add(detalleOrden);
            }
        }
        //mandar nueva lista
        detalles = ordenNueva;
        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getPrecio_total()).sum();
        orden.setPago_total((float) sumaTotal);
        orden.setCarrito(detalles);
        res = Utils.response200OK("Producto eliminado de carrito", orden);
        return res;
    }

    /*
    "pago_total":number,
    "tipo_envio":string,
    "fecha_venta":"12-05-2000" fecha,
    "facturado": number (0 o 1)
    */
    @PostMapping("/guardarCompra")
    public ResponseEntity<ResponseBody<TcomprasVO>> guardarCompra(@RequestParam Long id, @RequestBody TcomprasVO tcomprasVO) throws ParseException {
        GenerateCodigoCompra codigoCompraNew = new GenerateCodigoCompra();
        TcomprasVO newCompra = new TcomprasVO();

        //Recupera datos para la compra
        TusuariosVO user = tusuariosRepository.findUserById(id);
        //Llama a la funcion codigoCompra para generar el codigo
        newCompra.setCodigo_compra(codigoCompraNew.recuperarInformacion(user, tcomprasVO));
        newCompra.setPago_total(tcomprasVO.getPago_total());
        newCompra.setTipo_envio(tcomprasVO.getTipo_envio());
        newCompra.setFecha_venta(tcomprasVO.getFecha_venta());
        newCompra.setFacturado(tcomprasVO.getFacturado());
        newCompra.setDireccion(tcomprasVO.getDireccion());
        newCompra.setIdUsuario(user);
        //guardar productos a tcarrito
        for (TcarritoVO productos : detalles) {
            tcarritoRepository.save(productos);
            productos = productos;
            //los agrega a la compra
            newCompra.getCarrito().add(productos);
        }
        ;
        tcomprasRepository.save(newCompra);
        tcomprasRepository.flush();
        return Utils.response(HttpStatus.ACCEPTED, "Se registro compras", newCompra);
    }

    /*
    idProducto:number,
    cantidad: number
     */
    @PostMapping("/actualizarCantidadProducto")
    public ResponseEntity<ResponseBody<TcomprasVO>> actualizarCantidadProducto(@RequestParam Long idProducto, @RequestParam int cantidad) {
        int index = 0;
        TcarritoVO carritoAux = null;
        double sumaTotal = 0;
        ResponseEntity<ResponseBody<TcomprasVO>> res = null;
        try {
            //Recorre producto del carrito hasta encontrar el producto a actualizar cantidad
            for (TcarritoVO detalleProducto : detalles) {
                if (Objects.equals(detalleProducto.getIdProducto().getId(), idProducto)) {
                    carritoAux = detalleProducto;
                    //si lo encuentra verifiqu}a que la nueva cantidad no revase el stock
                    if (cantidad > detalleProducto.getIdProducto().getCantidad()) {
                        throw new RuntimeException("No contamos con el stock suficiente");
                    } else {
                        //si no revasa cantidad, actualiza cantidad
                        carritoAux.setCantidad(cantidad);
                        break;
                    }
                }
                index++;
            }
            detalles.set(index, carritoAux);
            sumaTotal = detalles.stream().mapToDouble(dt -> dt.getPrecio_total()).sum();
            orden.setPago_total((float) sumaTotal);
            orden.setCarrito(detalles);
            res = Utils.response200OK("Actualización correcta", orden);
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, e.getMessage(), orden);
        }
        return res;

    }
}
