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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public boolean insertCurso(){
        if(!curso.getDescricao().isEmpty())
        {
            dao.create(curso);
            return true;
        }else return false;
    }
    
    public boolean updateCurso(){
        boolean success=false;
        try {
                dao.edit(curso);
                success = true;

        } catch (Exception ex) {
            success = false;    
            Logger.getLogger(BeanProfessor.class.getName()).log(Level.SEVERE, null, ex);
          }
        return success;
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
    
    public Curso findCurso(int id){
        if(id>0)
            return dao.findCurso(id);
        else return null;
    }
}
