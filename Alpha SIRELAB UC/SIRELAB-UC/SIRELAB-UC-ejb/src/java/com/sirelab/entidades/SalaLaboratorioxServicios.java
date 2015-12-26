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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "salalaboratorioxservicios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SalaLaboratorioxServicios.findAll", query = "SELECT s FROM SalaLaboratorioxServicios s"),
    @NamedQuery(name = "SalaLaboratorioxServicios.findByIdsalalaboratorioxservicios", query = "SELECT s FROM SalaLaboratorioxServicios s WHERE s.idsalalaboratorioxservicios = :idsalalaboratorioxservicios"),
    @NamedQuery(name = "SalaLaboratorioxServicios.findByEstado", query = "SELECT s FROM SalaLaboratorioxServicios s WHERE s.estado = :estado")})
public class SalaLaboratorioxServicios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsalalaboratorioxservicios")
    private BigInteger idsalalaboratorioxservicios;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "serviciosala", referencedColumnName = "idserviciossala")
    @ManyToOne(optional = false)
    private ServiciosSala serviciosala;
    @JoinColumn(name = "salalaboratorio", referencedColumnName = "idsalalaboratorio")
    @ManyToOne(optional = false)
    private SalaLaboratorio salalaboratorio;
    @Transient
    private String strEstado;

    public SalaLaboratorioxServicios() {
    }

    public SalaLaboratorioxServicios(BigInteger idsalalaboratorioxservicios) {
        this.idsalalaboratorioxservicios = idsalalaboratorioxservicios;
    }

    public BigInteger getIdsalalaboratorioxservicios() {
        return idsalalaboratorioxservicios;
    }

    public void setIdsalalaboratorioxservicios(BigInteger idsalalaboratorioxservicios) {
        this.idsalalaboratorioxservicios = idsalalaboratorioxservicios;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public ServiciosSala getServiciosala() {
        return serviciosala;
    }

    public void setServiciosala(ServiciosSala serviciosala) {
        this.serviciosala = serviciosala;
    }

    public SalaLaboratorio getSalalaboratorio() {
        return salalaboratorio;
    }

    public void setSalalaboratorio(SalaLaboratorio salalaboratorio) {
        this.salalaboratorio = salalaboratorio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsalalaboratorioxservicios != null ? idsalalaboratorioxservicios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalaLaboratorioxServicios)) {
            return false;
        }
        SalaLaboratorioxServicios other = (SalaLaboratorioxServicios) object;
        if ((this.idsalalaboratorioxservicios == null && other.idsalalaboratorioxservicios != null) || (this.idsalalaboratorioxservicios != null && !this.idsalalaboratorioxservicios.equals(other.idsalalaboratorioxservicios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.SalaLaboratorioxServicios[ idsalalaboratorioxservicios=" + idsalalaboratorioxservicios + " ]";
    }

    public String getStrEstado() {
        getEstado();
        if (null != estado) {
            if (estado == true) {
                strEstado = "ACTIVO";
            } else {
                strEstado = "INACTIVO";
            }
        } else {
            strEstado = "INACTIVO";
        }
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }

}
