package com.svo.svo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//nombre de la tabla
@Table(name="tpuesto")

@NamedQueries({
        @NamedQuery(name = "TpuestoVO.buscarPuesto", query = "select p from TpuestoVO p"),
})
 public class TpuestoVO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre_puesto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre_puesto() {
        return nombre_puesto;
    }

    public void setNombre_puesto(String nombre_puesto) {
        this.nombre_puesto = nombre_puesto;
    }


}

