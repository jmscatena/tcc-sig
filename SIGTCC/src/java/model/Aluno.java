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
import javax.persistence.JoinColumn;
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
@Table(name = "alunos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alunos.findAll", query = "SELECT a FROM Alunos a")
    , @NamedQuery(name = "Alunos.findById", query = "SELECT a FROM Alunos a WHERE a.id = :id")
    , @NamedQuery(name = "Alunos.findByRa", query = "SELECT a FROM Alunos a WHERE a.ra = :ra")
    , @NamedQuery(name = "Alunos.findByNome", query = "SELECT a FROM Alunos a WHERE a.nome = :nome")
    , @NamedQuery(name = "Alunos.findByEmail", query = "SELECT a FROM Alunos a WHERE a.email = :email")
    , @NamedQuery(name = "Alunos.findByTelefone", query = "SELECT a FROM Alunos a WHERE a.telefone = :telefone")})
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ra")
    private String ra;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "telefone")
    private String telefone;
    @OneToOne(mappedBy = "idAlunos")
    private Tcc tcc;
    @JoinColumn(name = "id_acesso", referencedColumnName = "id")
    @OneToOne
    private Acesso idAcesso;
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    @OneToOne
    private Curso idCurso;

    public Aluno() {
    }

    public Aluno(Integer id) {
        this.id = id;
    }

    public Aluno(Integer id, String ra, String nome, String email, String telefone) {
        this.id = id;
        this.ra = ra;
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

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
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

    public Tcc getTcc() {
        return tcc;
    }

    public void setTcc(Tcc tcc) {
        this.tcc = tcc;
    }

    public Acesso getIdAcesso() {
        return idAcesso;
    }

    public void setIdAcesso(Acesso idAcesso) {
        this.idAcesso = idAcesso;
    }

    public Curso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Curso idCurso) {
        this.idCurso = idCurso;
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
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Alunos[ id=" + id + " ]";
    }
    
}
