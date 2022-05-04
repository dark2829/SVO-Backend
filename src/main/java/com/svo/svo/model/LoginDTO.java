package com.svo.svo.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginDTO{
    private String identificador;
    private String contrasena;
    private TpersonaVO idPerson;
    private TusuariosVO idUser;
    private String tokenAccess;
    private Collection<? extends GrantedAuthority> rol;



    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public TpersonaVO getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(TpersonaVO idPerson) {
        this.idPerson = idPerson;
    }

    public TusuariosVO getIdUser() {
        return idUser;
    }

    public void setIdUser(TusuariosVO idUser) {
        this.idUser = idUser;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public Collection<? extends GrantedAuthority> getRol() {
        return rol;
    }

    public void setRol(Collection<? extends GrantedAuthority> rol) {
        this.rol = rol;
    }
}
