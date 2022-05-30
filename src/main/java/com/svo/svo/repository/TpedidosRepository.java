package com.svo.svo.repository;

import com.svo.svo.model.TpedidosVO;
import com.svo.svo.model.TproductosVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TpedidosRepository extends JpaRepository<TpedidosVO,Long> {
    List<TpedidosVO> buscarTodosPedidos();
    TpedidosVO buscarPedidoPorIdCompra(Long idCompra);
    TpedidosVO buscarPedidoPorId(Long idPedido);
    List<TpedidosVO> buscarPedidosPorIdUsuario(Long idUsuario, String estatusPedido);
    List<TpedidosVO> buscarPedidoPorCodigoCompra(String codigoCompra);

}
