package com.svo.svo.service;

import com.svo.svo.model.TproductosDTO;
import com.svo.svo.model.TproductosVO;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TproductosService {
    void insert(TproductosDTO tproductosDTO) throws Exception;

    void update(Long id, TproductosDTO tproductosDTO) throws Exception;

    List<TproductosDTO> findAllProductos() throws Exception;
    List<TproductosDTO> findTipeProductos(String tipo) throws Exception;
    List<TproductosDTO> findRealProductos() throws Exception;

    TproductosVO findProductById(Long id) throws AppException;

    List<TproductosDTO> findStockBajo() throws AppException;

    void anadirFavoritos(Long idProducto, Long idUsuario) throws AppException;

    void borrarProductoFavorito(Long idProducto, Long idUsuario) throws AppException;

    void contactado(Long idProducto) throws AppException;


}
