package com.svo.svo.service.impl;

import com.svo.svo.model.TcomprasBuilder;
import com.svo.svo.model.TcomprasDTO;
import com.svo.svo.model.TcomprasVO;
import com.svo.svo.model.TproductosVO;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TcomprasRepository;
import com.svo.svo.repository.TusuariosRepository;
import com.svo.svo.service.TcomprasService;
import com.svo.svo.service.TproductosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TcomprasServiceImpl implements TcomprasService {
    private static final Logger LOG = LoggerFactory.getLogger(TcomprasServiceImpl.class);

    @Autowired
    private TcomprasRepository tcomprasRepository;

    @Override
    public TcomprasDTO buscarCompraPorId(Long idCompra) throws AppException {
        LOG.info("buscarCompraPorId ()");
        TcomprasVO tcomprasVO = null;
        TcomprasDTO  tcomprasDTO = null;
        try {
            tcomprasVO = tcomprasRepository.findCompraPorId(idCompra);
            tcomprasDTO = TcomprasBuilder.fromVO(tcomprasVO);
        } catch (Exception e) {
            Utils.raise(e, e.getMessage());
        }
        return tcomprasDTO;
    }
}
