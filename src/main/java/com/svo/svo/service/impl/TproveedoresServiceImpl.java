package com.svo.svo.service.impl;

import com.svo.svo.model.*;
import com.svo.svo.repository.TproveedoresRepository;
import com.svo.svo.service.TproveedoresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TproveedoresServiceImpl implements TproveedoresService {
    private static final Logger LOG = LoggerFactory.getLogger(TproveedoresService.class);

    @Autowired
    private TproveedoresRepository tproveedoresRepository;

    @Override
    public List<TproveedoresDTO> findAllProveedores() throws Exception {
        List<TproveedoresDTO> listProveedores = null;
        LOG.info("findProveedores");
        try {
            List<TproveedoresVO> tproveedoresVOList = tproveedoresRepository.findAllProveedores();
            listProveedores = new ArrayList<>();
            for (TproveedoresVO tproveedoresVO : tproveedoresVOList){
                TproveedoresDTO tproveedoresDTO = TproveedoresBuilder.fromVO(tproveedoresVO);
                listProveedores.add(tproveedoresDTO);
            }
        } catch (Exception e){

        }
        return listProveedores;
    }


    @Override
    public void insert(TproveedoresDTO tproveedoresDTO) {
        LOG.info("insert-> proveedor: {}",tproveedoresDTO);
        TproveedoresVO proveedorVO = null;
        try {
            proveedorVO = TproveedoresBuilder.fromDTO(tproveedoresDTO);
            proveedorVO.setId(0L);
            tproveedoresRepository.save(proveedorVO);
        } catch (Exception e){
            LOG.error(String.valueOf(e));
        }

    }

    @Override
    public void update(Long id, Map<String, String> data) throws Exception {
        LOG.info("update()->id: {} data{}",id,data);
        Optional<TproveedoresVO> vo=null;
        try{
            vo = tproveedoresRepository.findById(id);
            if (!vo.isPresent()){
                throw new RuntimeException("No se encuentra el periodo");
            }
            //nombre
            if (data.containsKey("nombre")){
                vo.get().setNombre(data.get("nombre"));
            }
            //telefono
            if (data.containsKey("telefono")){
                vo.get().setTelefono(data.get("telefono"));
            }
            //correo
            if (data.containsKey("correo")){
                vo.get().setCorreo(data.get("correo"));
            }
            //direccion
            if (data.containsKey("direccion")){
                vo.get().setDireccion(data.get("direccion"));
            }
            //provee
            if (data.containsKey("provee")){
                vo.get().setProvee(data.get("provee"));
            }
            tproveedoresRepository.save(vo.get());
        }catch (Exception e){
            LOG.error("Error Al actualizar un proveedor");
        }
    }
}
