package com.svo.svo.model;

public class TempleadosBuilder {
    public static TempleadosDTO fromVO(TempleadosVO templeadosVO){
        TempleadosVO origen =templeadosVO;
        TempleadosDTO destin = new TempleadosDTO();

        destin.setId(origen.getId());
        destin.setNo_empleado(origen.getNo_empleado());
        destin.setCurp(origen.getCurp());
        destin.setSalario(origen.getSalario());
        destin.setEstatus(origen.getEstatus());
        destin.setIdPersona(origen.getIdPersona());
        destin.setIdPuesto(origen.getIdPuesto());
        return destin;
    }

    public static TempleadosVO fromDTO(TempleadosDTO templeadosDTO){
        TempleadosDTO origen = templeadosDTO;
        TempleadosVO destin = new TempleadosVO();

        destin.setId(origen.getId());
        destin.setNo_empleado(origen.getNo_empleado());
        destin.setCurp(origen.getCurp());
        destin.setSalario(origen.getSalario());
        destin.setEstatus(origen.getEstatus());
        destin.setIdPersona(origen.getIdPersona());
        destin.setIdPuesto(origen.getIdPuesto());
        return destin;
    }
}
