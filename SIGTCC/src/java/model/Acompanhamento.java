/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jscatena
 */
@Entity
@Table(name = "acompanhamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acompanhamento.findAll", query = "SELECT a FROM Acompanhamento a")
    , @NamedQuery(name = "Acompanhamento.findById", query = "SELECT a FROM Acompanhamento a WHERE a.id = :id")
    , @NamedQuery(name = "Acompanhamento.findByData", query = "SELECT a FROM Acompanhamento a WHERE a.data = :data")
    , @NamedQuery(name = "Acompanhamento.findByObservacoes", query = "SELECT a FROM Acompanhamento a WHERE a.observacoes = :observacoes")})
public class Acompanhamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @Column(name = "observacoes")
    private String observacoes;
    @JoinColumn(name = "id_tcc", referencedColumnName = "id")
    @ManyToOne
    private Tcc idTcc;

    public Acompanhamento() {
    }

    public Acompanhamento(Integer id) {
        this.id = id;
    }

    public Acompanhamento(Integer id, Date data, String observacoes) {
        this.id = id;
        this.data = data;
        this.observacoes = observacoes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Tcc getIdTcc() {
        return idTcc;
    }

    public void setIdTcc(Tcc idTcc) {
        this.idTcc = idTcc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acompanhamento)) {
            return false;
        }
        Acompanhamento other = (Acompanhamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Acompanhamento[ id=" + id + " ]";
    }
    
}
