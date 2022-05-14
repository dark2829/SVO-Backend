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
    public TcomprasVO añadirCarrito(@RequestParam Long id, @RequestParam Integer cantidad) throws AppException {
        TcarritoVO detalleOrden = new TcarritoVO();
        TproductosVO producto = new TproductosVO();
        float total = 0;
        double sumaTotal = 0;
        int index = 0;
        TcarritoVO carritoAux = null;

        Optional<TproductosVO> optionalTproducto = Optional.ofNullable(tproductosService.findProductById(id));
//        LOG.info("Producto a añadir: " + optionalTproducto);
//        LOG.info("Cantidad: {}", cantidad);
        producto = optionalTproducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio_unitario(producto.getPrecio_venta());
        detalleOrden.setPrecio_descuento(producto.getPrecio_descuento());
        if (producto.getPrecio_descuento() != 0) {
            total = producto.getPrecio_descuento()*cantidad;
        }else{
            total = producto.getPrecio_venta()* cantidad;
        }
        detalleOrden.setPrecio_total(total);
        detalleOrden.setIdProducto(producto);

        //Validar que un producto no se agregue dos veces
        Long idProducto= producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p-> Objects.equals(p.getIdProducto().getId(), idProducto));
        //si no esta agregado se agrega
        if(!ingresado){
            detalles.add(detalleOrden);
        }else{
            //si no, Busca producto y le suma la nueva cantidad
            for(TcarritoVO detalleProducto:detalles){
                if(Objects.equals(detalleProducto.getIdProducto().getId(), idProducto)){
                    LOG.info("entro"+index);
                    carritoAux= detalleProducto;
                    carritoAux.setCantidad(detalleProducto.getCantidad()+cantidad);
                    break;
                }
                index++;
                LOG.info(String.valueOf(index));
            }
            detalles.set(index,carritoAux);
        }


        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getPrecio_total()).sum();

        orden.setPago_total((float) sumaTotal);
        orden.setCarrito(detalles);

        return orden;

    }

    //quitar producto del carrito
    // id:number
    @GetMapping("/delete/cart")
    public TcomprasVO deleteProductCart(@RequestParam Long id){
        LOG.info("Eliminar producto de carrito: ",id);
        //lista nueva de productos
        List<TcarritoVO> ordenNueva = new ArrayList<TcarritoVO>();
        for(TcarritoVO detalleOrden: detalles) {
            if (!Objects.equals(detalleOrden.getIdProducto().getId(), id)) {
                ordenNueva.add(detalleOrden);
            }
        }
        //mandar nueva lista
        detalles=ordenNueva;
        double sumaTotal =0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getPrecio_total()).sum();

        orden.setPago_total((float) sumaTotal);
        orden.setCarrito(detalles);
        LOG.info("ELIMINADO CORRECTAMENTE");
        return orden;
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
        TcomprasVO newCompra= new TcomprasVO();
        TusuariosVO  user = tusuariosRepository.findUserById(id);
        newCompra.setCodigo_compra(codigoCompraNew.recuperarInformacion(user,tcomprasVO));
        newCompra.setPago_total(tcomprasVO.getPago_total());
        newCompra.setTipo_envio(tcomprasVO.getTipo_envio());
        newCompra.setFecha_venta(tcomprasVO.getFecha_venta());
        newCompra.setFacturado(tcomprasVO.getFacturado());
        newCompra.setDireccion(tcomprasVO.getDireccion());
        newCompra.setIdUsuario(user);
        //guardar productos a tcarrito
        for(TcarritoVO productos: detalles){
            tcarritoRepository.save(productos);
            productos=productos;
            newCompra.getCarrito().add(productos);
        }
        //LOG.error("detalles"+String.valueOf(detalles));
       // newCompra.setCarrito(detalles);
        LOG.info(String.valueOf(newCompra));

       tcomprasRepository.save(newCompra);
       tcomprasRepository.flush();


        return Utils.response(HttpStatus.ACCEPTED,"Se registro compras",newCompra);
    }

}
