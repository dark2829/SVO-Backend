package com.svo.svo.service.impl;

import com.svo.svo.model.TdireccionVO;
import com.svo.svo.model.TpersonaVO;
import com.svo.svo.model.TtarjetasVO;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TpersonaRepository;
import com.svo.svo.repository.TtarjetaRepository;
import com.svo.svo.service.TtarjetasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TtarjetasServiceImpl implements TtarjetasService {
    private static final Logger LOG = LoggerFactory.getLogger(TtarjetasServiceImpl.class);

    @Autowired
    private TtarjetaRepository ttarjetaRepository;

    @Autowired
    private TpersonaRepository tpersonaRepository;

    @Override
    public void deleteTarjeta(Long idTarjeta, Long idPersona) throws AppException {
        LOG.info("deleteTarjeta -> idTarjeta: {}", idTarjeta);
        List<TtarjetasVO> nuevasTarjetas = new ArrayList<>();
        try {
            TpersonaVO persona = tpersonaRepository.getById(idPersona);
            TtarjetasVO ttarjetas = ttarjetaRepository.findTarjetaById(idTarjeta);
            List<TtarjetasVO> listarjetas = persona.getTarjeta();

            for (TtarjetasVO tarjetas : listarjetas) {
                if (tarjetas != ttarjetas) {
                    nuevasTarjetas.add(tarjetas);
                }
            }
            persona.setTarjeta(nuevasTarjetas);
            tpersonaRepository.save(persona);
            tpersonaRepository.flush();
            ttarjetaRepository.delete(ttarjetas);
        } catch (Exception e) {
            Utils.raise(e, "Error Al eliminar una tarjeta");
        }
    }
}
