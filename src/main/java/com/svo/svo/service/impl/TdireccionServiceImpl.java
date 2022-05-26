package com.svo.svo.service.impl;

import com.svo.svo.model.TdireccionVO;
import com.svo.svo.model.TpersonaVO;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.AppException400BadRequest;
import com.svo.svo.other.Utils.AppException404NotFound;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TdireccionRepository;
import com.svo.svo.repository.TpersonaRepository;
import com.svo.svo.service.TdireccionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TdireccionServiceImpl implements TdireccionService {
    private static final Logger LOG = LoggerFactory.getLogger(TdireccionServiceImpl.class);

    @Autowired
    private TdireccionRepository tdireccionRepository;

    @Autowired
    private TpersonaRepository tpersonaRepository;

    @Override
    public void deleteDireccion(Long idDireccion, Long idPersona) throws AppException {
        LOG.info("deleteDireccion -> idDireccion: {}", idDireccion);
        List<TdireccionVO> nuevasDirecciones = new ArrayList<>();
        try {
            TpersonaVO persona = tpersonaRepository.getById(idPersona);
            TdireccionVO tdireccion = tdireccionRepository.findDireccionById(idDireccion);
            List<TdireccionVO> listDirecciones = persona.getDireccion();

            for (TdireccionVO direcciones : listDirecciones) {
                if (direcciones != tdireccion) {
                    nuevasDirecciones.add(direcciones);
                }
            }
            persona.setDireccion(nuevasDirecciones);
            tpersonaRepository.save(persona);
            tpersonaRepository.flush();
            tdireccionRepository.delete(tdireccion);
        } catch (Exception e) {
            Utils.raise(e, "Error Al eliminar una direccion");
        }
    }
}
