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

import java.util.*;

@Service
public class TproductosServiceImpl implements TproductosService {
    private static final Logger LOG = LoggerFactory.getLogger(TproductosService.class);

    @Autowired
    private TproductosRepository tproductosRepository;

    @Override
    public void insert(TproductosDTO tproductosDTO) throws Exception {
        //LOG.info("insert-> producto: {}",tproductosDTO);
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
    public void update(Long id, TproductosDTO tproductosDTO) throws Exception {
        //LOG.info("update()->id: {} data{}", id, data);
        Optional<TproductosVO> vo = null;
        byte[] bite ;
        try {
            vo = tproductosRepository.findById(id);
            if (!vo.isPresent()) {
                throw new RuntimeException("No se encuentra el producto");
            }
            /*imagen
            codigo_prod
            nombre
            categoria
            cantidad
            precio_compra
            precio_venta
            precio_descuento
            descripcion
            estatus
            */
            vo.get().setImagen(tproductosDTO.getImagen());
            vo.get().setCodigo_prod(tproductosDTO.getCodigo_prod());
            vo.get().setNombre(tproductosDTO.getNombre());
            vo.get().setCategoria(tproductosDTO.getCategoria());
            vo.get().setCantidad(tproductosDTO.getCantidad());
            vo.get().setPrecio_compra(tproductosDTO.getPrecio_compra());
            vo.get().setPrecio_venta(tproductosDTO.getPrecio_venta());
            vo.get().setPrecio_descuento(tproductosDTO.getPrecio_descuento());
            vo.get().setDescripcion(tproductosDTO.getDescripcion());
            vo.get().setEstatus(tproductosDTO.getEstatus());
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
    public TproductosVO findProductById(Long id) throws AppException {
        LOG.info("findProductById ()");
        TproductosVO tproductosVO= null;
        try{
            tproductosVO = tproductosRepository.findProductoById(id);
        }catch (Exception e){
            Utils.raise(e,e.getMessage());
        }
        return tproductosVO;
    }
}