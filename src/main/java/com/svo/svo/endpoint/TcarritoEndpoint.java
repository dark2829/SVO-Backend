package com.svo.svo.endpoint;

import com.svo.svo.model.TcarritoVO;
import com.svo.svo.model.TcomprasVO;
import com.svo.svo.model.TproductosVO;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.service.TproductosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class TcarritoEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(TcarritoEndpoint.class);

    @Autowired
    private TproductosService tproductosService;

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

        Optional<TproductosVO> optionalTproducto = Optional.ofNullable(tproductosService.findProductById(id));
        LOG.info("Producto a añadir: " + optionalTproducto);
        LOG.info("Cantidad: {}", cantidad);
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

        detalles.add(detalleOrden);

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getPrecio_total()).sum();

        orden.setPago_total((float) sumaTotal);
        orden.setCarrito(detalles);
        LOG.info(String.valueOf(sumaTotal));

        return orden;

    }
}
