package com.svo.svo.other;

import com.svo.svo.endpoint.TcarritoEndpoint;
import com.svo.svo.model.TcomprasVO;
import com.svo.svo.model.TusuariosVO;
import org.hibernate.loader.plan.build.internal.LoadGraphLoadPlanBuildingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GenerateCodigoCompra {
    private static final Logger LOG = LoggerFactory.getLogger(GenerateCodigoCompra.class);

    public String recuperarInformacion(TusuariosVO tusuarios, TcomprasVO  tcompras) throws ParseException {
        Date fechaV= tcompras.getFecha_venta();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fechaTexto = formatter.format(fechaV);

        String codigo= crearCodigo(tusuarios.getIdPersona().getNombre(),
                tusuarios.getIdPersona().getApellido_paterno(),
                tusuarios.getIdPersona().getApellido_materno(),
                fechaTexto);
        LOG.info("Codigo de compra generado: "+codigo);
        return codigo;
    }

    public String crearCodigo(String nombre, String apePa, String apeMa, String fechaCompra) throws ParseException {
        Random random = new Random();
    String codigo= nombre.substring(0,1)+apePa.substring(0,1)+apeMa.substring(0,1)+fechaCompra.substring(0,2)+
            fechaCompra.substring(3,5)+random.nextInt(99);

    return codigo;
    }
}
