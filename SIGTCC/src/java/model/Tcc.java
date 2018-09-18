/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jscatena
 */
@Entity
@Table(name = "tcc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tcc.findAll", query = "SELECT t FROM Tcc t")
    , @NamedQuery(name = "Tcc.findById", query = "SELECT t FROM Tcc t WHERE t.id = :id")
    , @NamedQuery(name = "Tcc.findByTema", query = "SELECT t FROM Tcc t WHERE t.tema = :tema")
    , @NamedQuery(name = "Tcc.findByProjeto", query = "SELECT t FROM Tcc t WHERE t.projeto = :projeto")
    , @NamedQuery(name = "Tcc.findByDtInicio", query = "SELECT t FROM Tcc t WHERE t.dtInicio = :dtInicio")
    , @NamedQuery(name = "Tcc.findByDtTermino", query = "SELECT t FROM Tcc t WHERE t.dtTermino = :dtTermino")
    , @NamedQuery(name = "Tcc.findByDtDefesa", query = "SELECT t FROM Tcc t WHERE t.dtDefesa = :dtDefesa")})
public class Tcc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tema")
    private String tema;
    @Basic(optional = false)
    @Column(name = "projeto")
    private String projeto;
    @Basic(optional = false)
    @Column(name = "dt_inicio")
    @Temporal(TemporalType.DATE)
    private Date dtInicio;
    @Column(name = "dt_termino")
    @Temporal(TemporalType.DATE)
    private Date dtTermino;
    @Column(name = "dt_defesa")
    @Temporal(TemporalType.DATE)
    private Date dtDefesa;
    @OneToMany(mappedBy = "idTcc")
    private List<Acompanhamento> acompanhamentoList;
    @JoinColumn(name = "id_alunos", referencedColumnName = "id")
    @OneToOne
    private Alunos idAlunos;
    @JoinColumn(name = "id_professores", referencedColumnName = "id")
    @ManyToOne
    private Professores idProfessores;
    @JoinColumn(name = "id_tipotcc", referencedColumnName = "id")
    @OneToOne
    private Tipotcc idTipotcc;

    public Tcc() {
    }

    public Tcc(Integer id) {
        this.id = id;
    }

    public Tcc(Integer id, String tema, String projeto, Date dtInicio) {
        this.id = id;
        this.tema = tema;
        this.projeto = projeto;
        this.dtInicio = dtInicio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtTermino() {
        return dtTermino;
    }

    public void setDtTermino(Date dtTermino) {
        this.dtTermino = dtTermino;
    }

    public Date getDtDefesa() {
        return dtDefesa;
    }

    public void setDtDefesa(Date dtDefesa) {
        this.dtDefesa = dtDefesa;
    }

    @XmlTransient
    public List<Acompanhamento> getAcompanhamentoList() {
        return acompanhamentoList;
    }

    public void setAcompanhamentoList(List<Acompanhamento> acompanhamentoList) {
        this.acompanhamentoList = acompanhamentoList;
    }

    public Alunos getIdAlunos() {
        return idAlunos;
    }

    public void setIdAlunos(Alunos idAlunos) {
        this.idAlunos = idAlunos;
    }

    public Professores getIdProfessores() {
        return idProfessores;
    }

    public void setIdProfessores(Professores idProfessores) {
        this.idProfessores = idProfessores;
    }

    public Tipotcc getIdTipotcc() {
        return idTipotcc;
    }

    public void setIdTipotcc(Tipotcc idTipotcc) {
        this.idTipotcc = idTipotcc;
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
        if (!(object instanceof Tcc)) {
            return false;
        }
        Tcc other = (Tcc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tcc[ id=" + id + " ]";
    }
    
}
