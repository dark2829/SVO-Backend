package com.svo.svo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tfavoritos")
@NamedQueries({
        //@NamedQuery(name = "TproveedoresVO.findAllProveedores", query = "select p from TproveedoresVO p"),
})
public class TfavoritosVO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="tproductos_id",referencedColumnName = "id")
    private TpedidosVO idProducto;
    @ManyToOne
    @JoinColumn(name="tusuarios_id",referencedColumnName = "id")
    private TusuariosVO idUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TpedidosVO getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(TpedidosVO idProducto) {
        this.idProducto = idProducto;
    }

    public TusuariosVO getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TusuariosVO idUsuario) {
        this.idUsuario = idUsuario;
    }
}
