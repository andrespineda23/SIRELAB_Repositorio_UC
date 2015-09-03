/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "personacontacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonaContacto.findAll", query = "SELECT p FROM PersonaContacto p"),
    @NamedQuery(name = "PersonaContacto.findByIdpersonacontacto", query = "SELECT p FROM PersonaContacto p WHERE p.idpersonacontacto = :idpersonacontacto"),
    @NamedQuery(name = "PersonaContacto.findByIdentificacion", query = "SELECT p FROM PersonaContacto p WHERE p.identificacion = :identificacion"),
    @NamedQuery(name = "PersonaContacto.findByNombre", query = "SELECT p FROM PersonaContacto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PersonaContacto.findByApellido", query = "SELECT p FROM PersonaContacto p WHERE p.apellido = :apellido"),
    @NamedQuery(name = "PersonaContacto.findByTelefonofijo", query = "SELECT p FROM PersonaContacto p WHERE p.telefonofijo = :telefonofijo"),
    @NamedQuery(name = "PersonaContacto.findByTelefonocelular", query = "SELECT p FROM PersonaContacto p WHERE p.telefonocelular = :telefonocelular"),
    @NamedQuery(name = "PersonaContacto.findByCorreo", query = "SELECT p FROM PersonaContacto p WHERE p.correo = :correo"),
    @NamedQuery(name = "PersonaContacto.findByNombreusuario", query = "SELECT p FROM PersonaContacto p WHERE p.nombreusuario = :nombreusuario")})
public class PersonaContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpersonacontacto")
    private BigInteger idpersonacontacto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "identificacion")
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "apellido")
    private String apellido;
    @Size(max = 7)
    @Column(name = "telefonofijo")
    private String telefonofijo;
    @Size(max = 10)
    @Column(name = "telefonocelular")
    private String telefonocelular;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombreusuario")
    private String nombreusuario;
    @JoinColumn(name = "convenioporentidad", referencedColumnName = "idconvenioporentidad")
    @ManyToOne(optional = false)
    private ConvenioPorEntidad convenioporentidad;

    public PersonaContacto() {
    }

    public PersonaContacto(BigInteger idpersonacontacto) {
        this.idpersonacontacto = idpersonacontacto;
    }

    public PersonaContacto(BigInteger idpersonacontacto, String identificacion, String nombre, String apellido, String correo, String nombreusuario) {
        this.idpersonacontacto = idpersonacontacto;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.nombreusuario = nombreusuario;
    }

    public BigInteger getIdpersonacontacto() {
        return idpersonacontacto;
    }

    public void setIdpersonacontacto(BigInteger idpersonacontacto) {
        this.idpersonacontacto = idpersonacontacto;
    }

    public String getIdentificacion() {
        if (null != identificacion) {
            return identificacion.toUpperCase();
        }
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion.toUpperCase();
    }

    public String getNombre() {
        if (null != nombre) {
            return nombre.toUpperCase();
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getApellido() {
        if (null != apellido) {
            return apellido.toUpperCase();
        }
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido.toUpperCase();
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }

    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public ConvenioPorEntidad getConvenioporentidad() {
        return convenioporentidad;
    }

    public void setConvenioporentidad(ConvenioPorEntidad convenioporentidad) {
        this.convenioporentidad = convenioporentidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpersonacontacto != null ? idpersonacontacto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaContacto)) {
            return false;
        }
        PersonaContacto other = (PersonaContacto) object;
        if ((this.idpersonacontacto == null && other.idpersonacontacto != null) || (this.idpersonacontacto != null && !this.idpersonacontacto.equals(other.idpersonacontacto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.PersonaContacto[ idpersonacontacto=" + idpersonacontacto + " ]";
    }

}
