package com.svo.svo.service.impl;

import com.svo.svo.model.TpuestoBuilder;
import com.svo.svo.model.TpuestoDTO;
import com.svo.svo.model.TpuestoVO;
import com.svo.svo.repository.TpuestoRepository;
import com.svo.svo.service.TpuestoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TpuestoServiceImpl implements TpuestoService {
    private static final Logger LOG = LoggerFactory.getLogger(TpuestoService.class);

    @Autowired
    private TpuestoRepository puestoRepository;

    @Override
    public List<TpuestoDTO> buscarPuesto() throws Exception {
        List<TpuestoDTO> listPuestos = null;
        LOG.info("buscarPuestosImpl");
        try {
            List<TpuestoVO> listPuestosVO = puestoRepository.buscarPuesto();
            listPuestos = new ArrayList<>();
            for (TpuestoVO tpuestoVO : listPuestosVO) {
                TpuestoDTO puestosDTO = TpuestoBuilder.fromVO(tpuestoVO);
                listPuestos.add(puestosDTO);
            }
        } catch (Exception e) {

        }
        return listPuestos;
    }
}
