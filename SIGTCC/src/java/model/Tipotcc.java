/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jscatena
 */
@Entity
@Table(name = "tipotcc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipotcc.findAll", query = "SELECT t FROM Tipotcc t")
    , @NamedQuery(name = "Tipotcc.findById", query = "SELECT t FROM Tipotcc t WHERE t.id = :id")
    , @NamedQuery(name = "Tipotcc.findByDescricao", query = "SELECT t FROM Tipotcc t WHERE t.descricao = :descricao")})
public class Tipotcc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @OneToOne(mappedBy = "idTipotcc")
    private Tcc tcc;

    public Tipotcc() {
    }

    public Tipotcc(Integer id) {
        this.id = id;
    }

    public Tipotcc(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Tcc getTcc() {
        return tcc;
    }

    public void setTcc(Tcc tcc) {
        this.tcc = tcc;
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
        if (!(object instanceof Tipotcc)) {
            return false;
        }
        Tipotcc other = (Tipotcc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tipotcc[ id=" + id + " ]";
    }
    
}
