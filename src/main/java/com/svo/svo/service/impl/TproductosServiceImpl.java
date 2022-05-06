package com.svo.svo.service.impl;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TproductosRepository;
import com.svo.svo.service.TproductosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TproductosServiceImpl implements TproductosService {
    private static final Logger LOG = LoggerFactory.getLogger(TproductosService.class);

    @Autowired
    private TproductosRepository tproductosRepository;

    @Override
    public void insert(TproductosDTO tproductosDTO) throws Exception {
        LOG.info("insert-> producto: {}",tproductosDTO);
        TproductosVO producto = null;
        try {
            producto = TproductosBuilder.fromDTO(tproductosDTO);
            producto.setEstatus("Disponible");
            producto.setId(0L);
            tproductosRepository.save(producto);
        } catch (Exception e){
            LOG.error(String.valueOf(e));
        }
    }

    @Override
    public void update(Long id, Map<String, String> data) throws Exception {
        LOG.info("update()->id: {} data{}", id, data);
        Optional<TproductosVO> vo = null;
        try {
            vo = tproductosRepository.findById(id);
            if (!vo.isPresent()) {
                throw new RuntimeException("No se encuentra el producto");
            }
            //codigo_prod
            if (data.containsKey("codigo_prod")) {
               vo.get().setCodigo_prod(data.get("codigo_prod"));
            }
            //nombre
            if (data.containsKey("nombre")) {
                vo.get().setNombre(data.get("nombre"));
            }
            //categoria
            if (data.containsKey("categoria")) {
                vo.get().setCategoria(data.get("categoria"));
            }
            //cantidad
            if (data.containsKey("cantidad")) {
                vo.get().setCantidad(Integer.parseInt(data.get("cantidad")));
            }
            //precio_compra
            if (data.containsKey("precio_compra")) {
                vo.get().setPrecio_compra(Float.parseFloat(data.get("precio_compra")));
            }
            //precio_venta
            if (data.containsKey("precio_venta")) {
                vo.get().setPrecio_venta(Float.parseFloat(data.get("precio_venta")));
            }
            //precio_descuento
            if (data.containsKey("precio_descuento")) {
                vo.get().setPrecio_descuento(Float.parseFloat(data.get("precio_descuento")));
            }
            //descripcion
            if (data.containsKey("descripcion")) {
                vo.get().setDescripcion(data.get("descripcion"));
            }
            //estatus
            if (data.containsKey("estatus")) {
                vo.get().setEstatus(data.get("estatus"));
            }
            tproductosRepository.save(vo.get());
        } catch (Exception e) {
            LOG.error("Error Al actualizar un producto",e);
        }
    }

    @Override
    public List<TproductosDTO> findAllProductos() throws Exception {
        List<TproductosDTO> listProductos = null;
        LOG.info("findProductos()");
        try {
            List<TproductosVO> tproductosVOList = tproductosRepository.findAllProductos();
            listProductos = new ArrayList<>();
            for (TproductosVO tproductosVO1 : tproductosVOList) {
                TproductosDTO tproductosDTO = TproductosBuilder.fromVO(tproductosVO1);
                listProductos.add(tproductosDTO);
            }
        } catch (Exception e) {
            Utils.raise(e, "Error en buscar productos");
        }
        return listProductos;
    }

    @Override
    public ResponseEntity<ResponseBody<TproductosVO>> findProductById(Long id) throws AppException {
        LOG.info("findProductById ()");
        TproductosVO tproductosVO= null;
        ResponseEntity<ResponseBody<TproductosVO>> res= null;
        try{
            tproductosVO = tproductosRepository.findProductoById(id);
           // LOG.info("Producto encontrado: "+tproductosVO);
            if (tproductosVO != null) {
                res = Utils.response200OK("Producto encontrado",tproductosVO);
            }else{
                res = Utils.response(HttpStatus.BAD_REQUEST,"Producto no encontrado",null);
            }
        }catch (Exception e){
            Utils.raise(e,e.getMessage());
        }
        return res;
    }
}