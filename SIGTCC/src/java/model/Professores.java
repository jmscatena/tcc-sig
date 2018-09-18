/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jscatena
 */
@Entity
@Table(name = "professores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Professores.findAll", query = "SELECT p FROM Professores p")
    , @NamedQuery(name = "Professores.findById", query = "SELECT p FROM Professores p WHERE p.id = :id")
    , @NamedQuery(name = "Professores.findByRegistro", query = "SELECT p FROM Professores p WHERE p.registro = :registro")
    , @NamedQuery(name = "Professores.findByNome", query = "SELECT p FROM Professores p WHERE p.nome = :nome")
    , @NamedQuery(name = "Professores.findByEmail", query = "SELECT p FROM Professores p WHERE p.email = :email")
    , @NamedQuery(name = "Professores.findByTelefone", query = "SELECT p FROM Professores p WHERE p.telefone = :telefone")
    , @NamedQuery(name = "Professores.findByArea", query = "SELECT p FROM Professores p WHERE p.area = :area")
    , @NamedQuery(name = "Professores.findByCoordenador", query = "SELECT p FROM Professores p WHERE p.coordenador = :coordenador")})
public class Professores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "registro")
    private int registro;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "area")
    private String area;
    @Column(name = "coordenador")
    private Boolean coordenador;
    @OneToMany(mappedBy = "idProfessores")
    private List<Tcc> tccList;
    @JoinColumn(name = "id_acesso", referencedColumnName = "id")
    @OneToOne
    private Acesso idAcesso;

    public Professores() {
    }

    public Professores(Integer id) {
        this.id = id;
    }

    public Professores(Integer id, int registro, String nome, String email, String telefone) {
        this.id = id;
        this.registro = registro;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRegistro() {
        return registro;
    }

    public void setRegistro(int registro) {
        this.registro = registro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Boolean getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Boolean coordenador) {
        this.coordenador = coordenador;
    }

    @XmlTransient
    public List<Tcc> getTccList() {
        return tccList;
    }

    public void setTccList(List<Tcc> tccList) {
        this.tccList = tccList;
    }

    public Acesso getIdAcesso() {
        return idAcesso;
    }

    public void setIdAcesso(Acesso idAcesso) {
        this.idAcesso = idAcesso;
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
        if (!(object instanceof Professores)) {
            return false;
        }
        Professores other = (Professores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Professores[ id=" + id + " ]";
    }
    
}
