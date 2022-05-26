package com.svo.svo.endpoint;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.svo.svo.model.*;
import com.svo.svo.other.GenerateCodigoCompra;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.*;
import com.svo.svo.service.TproductosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.nio.file.LinkOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private TproductosRepository tproductosRepository;

    @Autowired
    private TcarritoRepository tcarritoRepository;

    @Autowired
    private TpagoRepository tpagoRepository;

    @Autowired
    private TcomprasRepository tcomprasRepository;

    @Autowired
    private TpedidosRepository tpedidosRepository;


    List<TcomprasVO> listOrdenes = new ArrayList<>();
    int idx;

    public TcomprasVO findCompras(Long idUsuario) throws AppException {
        TcomprasVO ordenUser = null;
        int indiceCompra = 0;
        try {
            TusuariosVO usuario = tusuariosRepository.findUserById(idUsuario);
            //si listaOrdenes es diferente de vacio
            if (!listOrdenes.isEmpty()) {
                for (TcomprasVO compra : listOrdenes) {
                    //buscar una compra del usuario
                    if (Objects.equals(compra.getIdUsuario().getId(), idUsuario)) {
                        //si la encuentra, guarda indice de compra
                        ordenUser = compra;
                        idx = indiceCompra;
                        break;
                    }
                    indiceCompra++;
                }
            }
            //si no encontro ninguna compra, crea una
            //y asigna el usuario
            if (ordenUser == null || listOrdenes.isEmpty()) {
                ordenUser = new TcomprasVO();
                ordenUser.setIdUsuario(usuario);

            }

        } catch (Exception e) {
            LOG.error("Error al crear y buscar compras", e);
        }
        return ordenUser;
    }

    /*idProducto:Number
    cantidad:number
    idUsuario: number*/
    @PostMapping("/carrito")
    public ResponseEntity<ResponseBody<TcomprasVO>> añadirCarrito(@RequestParam Long idProducto, @RequestParam Integer cantidad, @RequestParam Long idUsuario) throws AppException {
        TcarritoVO productoCarrito = new TcarritoVO();
        TproductosVO producto = null;

        TusuariosVO usuario = null;
        float total = 0;
        double sumaTotal = 0;
        int index = 0;
        TcarritoVO carritoAux = null;
        ResponseEntity<ResponseBody<TcomprasVO>> res = null;

        try {
            //si ya hay una compra con ese usuario la regresa
            //si no, regresa una compra con campos null
            TcomprasVO orden = findCompras(idUsuario);

            //crea lista de carrito y le asigna lo que tenga la compra
            List<TcarritoVO> productosCarrito = orden.getCarrito();
            Integer productosCarritoAux = productosCarrito.size();

            Optional<TproductosVO> optionalTproducto = Optional.ofNullable(tproductosService.findProductById(idProducto));
            //buscar producto a agregar
            producto = optionalTproducto.get();

            //verifica que cantidad no revase stock, si revasa cancela proceso y mando msj
            productoCarrito.setPrecio_unitario(producto.getPrecio_venta());
            productoCarrito.setPrecio_descuento(producto.getPrecio_descuento());
            //si el producto tiene descuento la cantidad de multiplicara por él
            if (producto.getPrecio_descuento() != 0) {
                total = producto.getPrecio_descuento() * cantidad;
            } else {//si no , sera pro el precio de venta
                total = producto.getPrecio_venta() * cantidad;
            }
            productoCarrito.setPrecio_total(total);
            productoCarrito.setIdProducto(producto);

            //Validar que un producto no se agregue dos veces
            Long idProduc = producto.getId();
            boolean ingresado = productosCarrito.stream().anyMatch(p -> Objects.equals(p.getIdProducto().getId(), idProducto));
            ;
            //si no esta agregado se agrega
            if (!ingresado) {
                if (producto.getCantidad() == 0) {
                    throw new RuntimeException(producto.getNombre() + "Agotado");
                } else if (cantidad > producto.getCantidad()) {
                    throw new RuntimeException("Cantidad disponible: " + producto.getCantidad());
                }
                //guarda informacion de producto en carrito
                productoCarrito.setCantidad(cantidad);
                productosCarrito.add(productoCarrito);
            } else {
                //si no, Busca producto y le suma la nueva cantidad
                for (TcarritoVO productoCar : productosCarrito) {
                    //si encuentra el producto
                    if (Objects.equals(productoCar.getIdProducto().getId(), idProduc)) {
                        //actualiza informacion del producto
//                        productoCar.setIdProducto(producto);
//                        if(productoCar.getIdProducto().getCantidad()==0){
//                            LOG.error("indice de carrito"+index);
//                            productosCarrito.remove(index);
//                            throw new RuntimeException("Lo sentimos el producto se agotó");
//                        }
                        //carritoAux guarda el producto encontrado en carrito
                        carritoAux = productoCar;
                        //verifica que sumandole la cantidad no revase el stock
                        //si si, cancela proceso y manda msj
                        if (productoCar.getCantidad() + cantidad > productoCar.getIdProducto().getCantidad()) {
                            throw new RuntimeException("La cantidad revasa el stock");
                        } else {
                            //si no, suma cantidad
                            carritoAux.setCantidad(productoCar.getCantidad() + cantidad);
                            //si el producto tiene descuento la cantidad de multiplicara por él
                            if (producto.getPrecio_descuento() != 0) {
                                total = producto.getPrecio_descuento() * carritoAux.getCantidad();
                            } else {//si no , sera pro el precio de venta
                                total = producto.getPrecio_venta() * carritoAux.getCantidad();
                            }
                            carritoAux.setPrecio_total(total);
                            break;
                        }
                    }
                    index++;
                }
                //actualiza producto encontrado en el carrito y lo actualiza con carritoAux
                //que almacena el producto actualizado
                productosCarrito.set(index, carritoAux);
            }


            sumaTotal = productosCarrito.stream().mapToDouble(dt -> dt.getPrecio_total()).sum();


            if (listOrdenes.isEmpty() || productosCarritoAux == 0) {
                orden.setPago_total((float) sumaTotal);
                orden.setCarrito(productosCarrito);
                listOrdenes.add(orden);
            } else {
                orden.setPago_total((float) sumaTotal);
                orden.setCarrito(productosCarrito);
                listOrdenes.set(idx, orden);
                idx = 0;
            }

            res = Utils.response200OK("Carrito actualizado", orden);
        } catch (Exception e) {
            // Utils.raise(e,"error en carrito");
            res = Utils.response(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
        return res;

    }

    //idUsuario:number
    @GetMapping("/actualizarProductosCarrito")
    public ResponseEntity<ResponseBody<TcomprasVO>> actualizarProductosCarrito(@RequestParam Long idUsuario) throws AppException {
        int indiceCar = 0;
        String msj = null;
        ResponseEntity<ResponseBody<TcomprasVO>> res = null;
        TcomprasVO compraUsuario = new TcomprasVO();
        List<TcarritoVO> carritoActualizado = new ArrayList<>();
        TproductosVO producto = null;

        try {
            for (TcomprasVO compra : listOrdenes) {
                if (Objects.equals(compra.getIdUsuario().getId(), idUsuario)) {
                    compraUsuario = compra;
                }
            }
            for (TcarritoVO productoCarrito : compraUsuario.getCarrito()) {
                producto = tproductosService.findProductById(productoCarrito.getIdProducto().getId());
                //actualizamos informacion de producto
                productoCarrito.setIdProducto(producto);
                //si el producto tiene 0 en cantidad
                if (productoCarrito.getIdProducto().getCantidad() == 0) {
                    //lo elimina de la lista de carrito en curso
                    msj = "Lo sentimos el producto " + productoCarrito.getIdProducto().getNombre() + " se agotó";
                    //si la cantidad de carrito es mayor a la cantidad en carrito
                    //la actualiza a la cantidad de producto
                } else if (productoCarrito.getCantidad() > productoCarrito.getIdProducto().getCantidad()) {
                    productoCarrito.setCantidad(productoCarrito.getIdProducto().getCantidad());
                    msj = "La cantidad disponible de " + productoCarrito.getIdProducto().getNombre() + " son " + productoCarrito.getIdProducto().getCantidad() + "unidades";
                    carritoActualizado.add(productoCarrito);
                } else {
                    carritoActualizado.add(productoCarrito);
                }
                indiceCar++;
            }
            compraUsuario.setCarrito(carritoActualizado);

            LOG.error("lista de ordenes" + listOrdenes);
            res = Utils.response(HttpStatus.ACCEPTED, msj, compraUsuario);
        } catch (Exception e) {
            Utils.raise(e, "Error al actualizar carrito");
        }
        return res;


    }


    //quitar producto del carrito
    // idProducto:number
    //idUsuario:number
    @GetMapping("/delete/cart")
    public ResponseEntity<ResponseBody<TcomprasVO>> deleteProductCart(@RequestParam Long idProducto, @RequestParam Long idUsuario) throws AppException {
        LOG.info("Eliminar producto de carrito: ", idProducto);
        ResponseEntity<ResponseBody<TcomprasVO>> res = null;
        TcomprasVO compra = findCompras(idUsuario);
        List<TcarritoVO> productosCarrito = null;

        //lista nueva de productos
        List<TcarritoVO> ordenNueva = new ArrayList<TcarritoVO>();
        for (TcarritoVO detalleOrden : compra.getCarrito()) {
            if (!Objects.equals(detalleOrden.getIdProducto().getId(), idProducto)) {
                ordenNueva.add(detalleOrden);
            }
        }
        //mandar nueva lista
        productosCarrito = ordenNueva;
        double sumaTotal = 0;
        sumaTotal = productosCarrito.stream().mapToDouble(dt -> dt.getPrecio_total()).sum();
        compra.setPago_total((float) sumaTotal);
        compra.setCarrito(productosCarrito);
        res = Utils.response200OK("Producto eliminado de carrito", compra);
        return res;
    }

    /*
    url{
    "idUsuario":number
    }

    bodi{
    "tipo_envio":string,
    "direccion":sring(cuando sea envio a domicilio)
    "fecha_venta":"12-05-2000" fecha,
    "facturado": number (0 o 1)
    "tipo_pago":string
    "tarjetaUtilizada: string"
    }
    */
    @PostMapping("/guardarCompra")
    public ResponseEntity<ResponseBody<TcomprasVO>> guardarCompra(@RequestParam Long idUsuario, @RequestBody Map<String,String> data) throws ParseException, AppException {
        GenerateCodigoCompra codigoCompraNew = new GenerateCodigoCompra();
        TcomprasVO newCompra = findCompras(idUsuario);
        ResponseEntity<ResponseBody<TcomprasVO>> res = null;
        String fechaVenta = data.get("fecha_venta");
        Date date =new SimpleDateFormat("dd-MM-yyyy").parse(fechaVenta);
        TpagosVO pago = new TpagosVO();
        TpedidosVO pedido = new TpedidosVO();

        try {
            TusuariosVO user = tusuariosRepository.findUserById(idUsuario);
            //Verifica que no tenga productos en carrito
            if (newCompra.getCarrito().isEmpty()) {
                throw new RuntimeException("Debe tener al menos un producto en carrito");
            } else {
                //si tiene, Recupera datos para la compra
                //Llama a la funcion codigoCompra para generar el codigo
                String codigo = codigoCompraNew.codigo(user, date);

                while (tcomprasRepository.findCompra(codigo) != null) {
                    codigo = codigoCompraNew.codigo(user, date);
                }
                newCompra.setCodigo_compra(codigo);
                newCompra.setTipo_envio(data.get("tipo_envio"));
                newCompra.setFecha_venta(date);
                newCompra.setFacturado(Integer.parseInt(data.get("facturado")));
                newCompra.setDireccion(data.get("direccion"));
                //guardar productos a tcarrito
                for (TcarritoVO productos : newCompra.getCarrito()) {
                    //va a hasta producto y le resta la cantidad comprada
                    productos.getIdProducto().setCantidad(productos.getIdProducto().getCantidad() - productos.getCantidad());
                    //guarda en productos
                    tproductosRepository.save(productos.getIdProducto());
                    tcarritoRepository.save(productos);
                }
            }


            //guarda informacion de pago
            pago.setTipo_pago(data.get("tipo_pago"));
            if(Objects.equals(pago.getTipo_pago(), "Efectivo")){
                pago.setEstatus("Pendiente");
            }else{
                pago.setEstatus("Pagado");
                pago.setTarjetautilizada(data.get("tarjetaUtilizada"));
            }
            //guarda compra
            tcomprasRepository.save(newCompra);
            tcomprasRepository.flush();
            pago.setIdCompra(newCompra);
            //guarda pago
            tpagoRepository.save(pago);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 2);
            date = c.getTime();
            pedido.setFecha_entrega(date);
            pedido.setEstatus("Nuevo");
            pedido.setIdCompra(newCompra);
            tpedidosRepository.save(pedido);
            LOG.info("PEDIDO"+pedido);
            //elimina de lista ordenes
            listOrdenes.remove(idx);
            idx = 0;
            res = Utils.response(HttpStatus.ACCEPTED, "Se registro compra correctamente", newCompra);
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
        return res;
    }

    /*
    idProducto:number,
    cantidad: number
    idUsuario: number
     */
    @PostMapping("/actualizarCantidadProducto")
    public ResponseEntity<ResponseBody<TcomprasVO>> actualizarCantidadProducto(@RequestParam Long idProducto, @RequestParam int cantidad, @RequestParam Long idUsuario) throws AppException {
        int index = 0;
        float total = 0;
        TcarritoVO carritoAux = null;
        double sumaTotal = 0;
        TproductosVO producto = null;
        TcomprasVO compra = findCompras(idUsuario);
        List<TcarritoVO> productosCarrito = compra.getCarrito();
        ResponseEntity<ResponseBody<TcomprasVO>> res = null;

        try {
            Optional<TproductosVO> optionalTproducto = Optional.ofNullable(tproductosService.findProductById(idProducto));
            //buscar producto a agregar
            producto = optionalTproducto.get();
            //Recorre producto del carrito hasta encontrar el producto a actualizar cantidad
            for (TcarritoVO detalleProducto : compra.getCarrito()) {
                if (Objects.equals(detalleProducto.getIdProducto().getId(), idProducto)) {
                    carritoAux = detalleProducto;
                    //si lo encuentra verifique que la nueva cantidad no revase el stock
                    if (cantidad > detalleProducto.getIdProducto().getCantidad()) {
                        throw new RuntimeException("No contamos con el stock suficiente");
                    } else {
                        //si no revasa cantidad, actualiza cantidad
                        carritoAux.setCantidad(cantidad);
                        if (carritoAux.getPrecio_descuento() != 0) {
                            total = producto.getPrecio_descuento() * carritoAux.getCantidad();
                        } else {//si no , sera pro el precio de venta
                            total = producto.getPrecio_venta() * carritoAux.getCantidad();
                        }
                        carritoAux.setPrecio_total(total);
                        break;
                    }
                }
                index++;
            }
            productosCarrito.set(index, carritoAux);
            sumaTotal = productosCarrito.stream().mapToDouble(dt -> dt.getPrecio_total()).sum();
            compra.setPago_total((float) sumaTotal);
            compra.setCarrito(productosCarrito);
            res = Utils.response200OK("Actualización correcta", compra);
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, e.getMessage(), compra);
        }
        return res;

    }
}
