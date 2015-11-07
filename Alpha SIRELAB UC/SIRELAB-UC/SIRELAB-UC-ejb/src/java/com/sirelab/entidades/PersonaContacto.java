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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "personacontacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonaContacto.findAll", query = "SELECT p FROM PersonaContacto p"),
    @NamedQuery(name = "PersonaContacto.findByIdpersonacontacto", query = "SELECT p FROM PersonaContacto p WHERE p.idpersonacontacto = :idpersonacontacto")})
public class PersonaContacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpersonacontacto")
    private BigInteger idpersonacontacto;
    @JoinColumn(name = "persona", referencedColumnName = "idpersona")
    @ManyToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "convenioporentidad", referencedColumnName = "idconvenioporentidad")
    @ManyToOne(optional = false)
    private ConvenioPorEntidad convenioporentidad;

    public PersonaContacto() {
    }

    public PersonaContacto(BigInteger idpersonacontacto) {
        this.idpersonacontacto = idpersonacontacto;
    }

    public BigInteger getIdpersonacontacto() {
        return idpersonacontacto;
    }

    public void setIdpersonacontacto(BigInteger idpersonacontacto) {
        this.idpersonacontacto = idpersonacontacto;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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
