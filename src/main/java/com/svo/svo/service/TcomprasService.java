package com.svo.svo.service;

import com.svo.svo.model.TcomprasDTO;
import com.svo.svo.other.Utils.AppException;

public interface TcomprasService {
    TcomprasDTO buscarCompraPorId(Long idCompra) throws AppException;
}
