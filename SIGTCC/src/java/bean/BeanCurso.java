/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.AlunosDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import dao.CursoDAO;
import java.util.List;
import model.Curso;

/**
 *
 * @author jscatena
 */
@ManagedBean
@ViewScoped
public class BeanCurso {
    private CursoDAO dao;
    private Curso curso;
    public BeanCurso() {
        dao = new CursoDAO(javax.persistence.Persistence.createEntityManagerFactory("SIGTCCPU"));
        curso = new Curso();
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    
    public List<Curso> getCursos()
    {
        return dao.findCursoEntities();
    }
}
