/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.AlunosDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Alunos;

/**
 *
 * @author jscatena
 */
@ManagedBean
@ViewScoped
public class BeanAluno {

    private AlunosDAO dao;
    private Alunos aluno;
    public BeanAluno() {
        dao = new AlunosDAO(javax.persistence.Persistence.createEntityManagerFactory("SIGTCCPU"));
        this.aluno = new Alunos();

    }

    public boolean insertAluno(){
        if(!aluno.getNome().isEmpty())
        {
            dao.create(aluno);
            return true;
        }else return false;
        
    }

    public boolean updateAluno(){
        boolean success=false;
        try {
                dao.edit(aluno);
                success = true;

        } catch (Exception ex) {
            success = false;    
            Logger.getLogger(BeanProfessor.class.getName()).log(Level.SEVERE, null, ex);
          }
        return success;
    }
    
    
    public Alunos getAluno() {
        return aluno;
    }

    public void setAluno(Alunos aluno) {
        this.aluno = aluno;
    }
    


    public List<Alunos> getAlunos(){
        return dao.findAlunosEntities();
    }
    
    public Alunos findAluno(int id)
    {
        if(id>0)
            return dao.findAlunos(id);
        else return null;
    }
    

    
}
