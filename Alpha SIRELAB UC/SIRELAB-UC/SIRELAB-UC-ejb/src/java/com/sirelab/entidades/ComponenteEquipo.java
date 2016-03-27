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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "componenteequipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComponenteEquipo.findAll", query = "SELECT c FROM ComponenteEquipo c"),
    @NamedQuery(name = "ComponenteEquipo.findByIdcomponenteequipo", query = "SELECT c FROM ComponenteEquipo c WHERE c.idcomponenteequipo = :idcomponenteequipo"),
    @NamedQuery(name = "ComponenteEquipo.findByCodigocomponete", query = "SELECT c FROM ComponenteEquipo c WHERE c.codigocomponete = :codigocomponete"),
    @NamedQuery(name = "ComponenteEquipo.findByNombrecomponente", query = "SELECT c FROM ComponenteEquipo c WHERE c.nombrecomponente = :nombrecomponente"),
    @NamedQuery(name = "ComponenteEquipo.findByDescripcioncomponente", query = "SELECT c FROM ComponenteEquipo c WHERE c.descripcioncomponente = :descripcioncomponente"),
    @NamedQuery(name = "ComponenteEquipo.findByMarcacomponente", query = "SELECT c FROM ComponenteEquipo c WHERE c.marcacomponente = :marcacomponente"),
    @NamedQuery(name = "ComponenteEquipo.findByModelocomponente", query = "SELECT c FROM ComponenteEquipo c WHERE c.modelocomponente = :modelocomponente"),
    @NamedQuery(name = "ComponenteEquipo.findBySerialcomponente", query = "SELECT c FROM ComponenteEquipo c WHERE c.serialcomponente = :serialcomponente"),
    @NamedQuery(name = "ComponenteEquipo.findByEstadocomponente", query = "SELECT c FROM ComponenteEquipo c WHERE c.estadocomponente = :estadocomponente"),
    @NamedQuery(name = "ComponenteEquipo.findByCostocomponente", query = "SELECT c FROM ComponenteEquipo c WHERE c.costocomponente = :costocomponente")})
public class ComponenteEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomponenteequipo")
    private BigInteger idcomponenteequipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigocomponete")
    private String codigocomponete;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombrecomponente")
    private String nombrecomponente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descripcioncomponente")
    private String descripcioncomponente;
    @Size(max = 45)
    @Column(name = "marcacomponente")
    private String marcacomponente;
    @Size(max = 45)
    @Column(name = "modelocomponente")
    private String modelocomponente;
    @Size(max = 45)
    @Column(name = "serialcomponente")
    private String serialcomponente;
    @Column(name = "estadocomponente")
    private Boolean estadocomponente;
    @Size(max = 10)
    @Column(name = "costocomponente")
    private String costocomponente;
    @JoinColumn(name = "tipocomponente", referencedColumnName = "idtipocomponente")
    @ManyToOne(optional = false)
    private TipoComponente tipocomponente;
    @JoinColumn(name = "equipoelemento", referencedColumnName = "idequipoelemento")
    @ManyToOne(optional = false)
    private EquipoElemento equipoelemento;
    @Transient
    private String strEstado;

    public ComponenteEquipo() {
    }

    public ComponenteEquipo(BigInteger idcomponenteequipo) {
        this.idcomponenteequipo = idcomponenteequipo;
    }

    public ComponenteEquipo(BigInteger idcomponenteequipo, String codigocomponete, String nombrecomponente, String descripcioncomponente) {
        this.idcomponenteequipo = idcomponenteequipo;
        this.codigocomponete = codigocomponete;
        this.nombrecomponente = nombrecomponente;
        this.descripcioncomponente = descripcioncomponente;
    }

    public BigInteger getIdcomponenteequipo() {
        return idcomponenteequipo;
    }

    public void setIdcomponenteequipo(BigInteger idcomponenteequipo) {
        this.idcomponenteequipo = idcomponenteequipo;
    }

    public String getCodigocomponete() {
        return codigocomponete.toUpperCase();
    }

    public void setCodigocomponete(String codigocomponete) {
        this.codigocomponete = codigocomponete.toUpperCase();
    }

    public String getNombrecomponente() {
        return nombrecomponente.toUpperCase();
    }

    public void setNombrecomponente(String nombrecomponente) {
        this.nombrecomponente = nombrecomponente.toUpperCase();
    }

    public String getDescripcioncomponente() {
        return descripcioncomponente.toUpperCase();
    }

    public void setDescripcioncomponente(String descripcioncomponente) {
        this.descripcioncomponente = descripcioncomponente.toUpperCase();
    }

    public String getMarcacomponente() {
        return marcacomponente.toUpperCase();
    }

    public void setMarcacomponente(String marcacomponente) {
        this.marcacomponente = marcacomponente.toUpperCase();
    }

    public String getModelocomponente() {
        return modelocomponente.toUpperCase();
    }

    public void setModelocomponente(String modelocomponente) {
        this.modelocomponente = modelocomponente.toUpperCase();
    }

    public String getSerialcomponente() {
        return serialcomponente.toUpperCase();
    }

    public void setSerialcomponente(String serialcomponente) {
        this.serialcomponente = serialcomponente.toUpperCase();
    }

    public Boolean getEstadocomponente() {
        return estadocomponente;
    }

    public void setEstadocomponente(Boolean estadocomponente) {
        this.estadocomponente = estadocomponente;
    }

    public String getStrEstado() {
        getEstadocomponente();
        if (null != estadocomponente) {
            if (true == estadocomponente) {
                strEstado = "ACTIVO";
            } else {
                strEstado = "INACTIVO";
            }
        } else {
            strEstado = "NO POSEE";
        }
        return strEstado;
    }
   
    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }
    
     public String getCostocomponente() {
        return costocomponente;
    }

    public void setCostocomponente(String costocomponente) {
        this.costocomponente = costocomponente;
    }


    public TipoComponente getTipocomponente() {
        return tipocomponente;
    }

    public void setTipocomponente(TipoComponente tipocomponente) {
        this.tipocomponente = tipocomponente;
    }

    public EquipoElemento getEquipoelemento() {
        return equipoelemento;
    }

    public void setEquipoelemento(EquipoElemento equipoelemento) {
        this.equipoelemento = equipoelemento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomponenteequipo != null ? idcomponenteequipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponenteEquipo)) {
            return false;
        }
        ComponenteEquipo other = (ComponenteEquipo) object;
        if ((this.idcomponenteequipo == null && other.idcomponenteequipo != null) || (this.idcomponenteequipo != null && !this.idcomponenteequipo.equals(other.idcomponenteequipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.ComponenteEquipo[ idcomponenteequipo=" + idcomponenteequipo + " ]";
    }
    
}
